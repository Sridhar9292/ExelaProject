package com.exelatech.ecx.backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.exelatech.ecx.backend.BLL.ProftpdMap;
import com.exelatech.ecx.backend.domain.MoveItc;
import com.exelatech.ecx.backend.domain.Proftpd;
import com.exelatech.ecx.backend.domain.RpdsInput;
import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.domain.SplunkResult;
import com.exelatech.ecx.backend.domain.T2App;
import com.exelatech.ecx.backend.service.ICompletable;
import com.exelatech.ecx.backend.service.IProdFTPD;
import com.exelatech.ecx.backend.service.ISplunkProcessing;
import com.exelatech.ecx.backend.util.DateUtl;
import com.exelatech.ecx.backend.util.JsonUtl;
import com.exelatech.ecx.backend.util.RegexUtl;

public class ProftpController implements ISplunkProcessing, ICompletable {
    static Logger log = Logger.getLogger(TransferController.class.getName());

    private SplunkResult<Proftpd> data;
    Setting _setting;
    private MappingController<ArrayList<Proftpd>> mapCtrl;

    public SplunkResult<Proftpd> getData() {
        return data;
    }

    public void setData(SplunkResult<Proftpd> data) {
        this.data = data;
    }

    /**
     * Setup
     */
    @Override
    public void Setting(Setting setting) {
        this._setting = setting;
        mapCtrl = new MappingController<ArrayList<Proftpd>>(new ProftpdMap());

    }

    /**
     * Request data from splunk
     */
    @Override
    public void RequestData() throws Exception {

        SplunkController proFTPD = new SplunkController(this._setting);
        proFTPD.getJobId();
        String pp = proFTPD.getResults();
        data = JsonUtl.SplProftpdStrToObj(pp);
        log.info(data.getResults().size() + " records were retrived for PROFTPD");
        SetTimeEpoch();
        
        

    }

    /**
     * fill elapsed time Convert Seconds to milliseconds (Time field) Complete SLA
     * with N/A if mapping doesn't find the application code.
     */
    @Override
    public void DataTransformation() {
        for (int index = 0; index < getData().getResults().size(); index++) {
            Proftpd item = getData().getResults().get(index);

            if (item.getSLA() == null || item.getSLA().isEmpty())
                item.setSLA("N/A");

            if (item.getDeliveredToTargetPlatform() == null || item.getDeliveredToTargetPlatform().isEmpty()) {
                item.setElapsedTime(DateUtl.CalculateElapsedTime(new Date().getTime(), Long.parseLong(item.getTime())));
            } else {
                item.setElapsedTime(DateUtl.CalculateElapsedTime(Long.parseLong(item.getDeliveredToTargetPlatform()),
                        Long.parseLong(item.getTime())));
            }
        }

    }

    /**
     * Mapping data with data from ECX database
     */
    @Override
    public void DataMapping() {

        mapCtrl.RequestData(); // Request ECX Data to map
        mapCtrl.Mapping(data.getResults()); // Map results
    }

    @Override
    public void SetTimeEpoch() {
        for (int index = 0; index < getData().getResults().size(); index++) {
            Proftpd item = getData().getResults().get(index);
            //item.setTime(DateUtl.getEpochFromDateString(item.getTime(), "EEE MMM dd HH:mm:ss yyyy"));
            item.setTime(DateUtl.convertMs(item.getTime()));
            if (item.getFileName() != null) {
                ArrayList<String> extract = RegexUtl.extractDataFromString(item.getFileName(),
                        new String[] { "((?!.*\\/))(.*)(?>\\.[a-zA-Z]{0,6})" }, new String[] { "'" },
                        new String[] { "'" });

                if (extract.size() > 0)
                    item.setFileName(extract.get(0));
                else {
                    String chars = item.getFileName().contains("/") ? "/" : "\\";
                    String[] spl = item.getFileName().split(chars);
                    if (spl.length > 0)
                        item.setFileName(spl[spl.length - 1]);
                }
            }
        }
    }

    /**
     * Not need it on this process, only on MoveItcController and T2AppController,
     * consider iterface refactor
     */
    @Override
    public <T extends IProdFTPD> void SetQueryFilter(ArrayList<T> data) {
    }

    @Override
    public void CompleteFromMoveIt(ArrayList<MoveItc> data) {
        log.info("MoveIt -> Proftpd");
        ArrayList<MoveItc> newMoveIt = new ArrayList<MoveItc>();
        ArrayList<Proftpd> notMovement = new ArrayList<Proftpd>();

        ArrayList<Integer> proccesed = new ArrayList<Integer>();
        int size = proccesed.size();
        Proftpd pr_item = null;
        for (int pr_index = 0; pr_index < this.data.getResults().size(); pr_index++) {
            pr_item = this.data.getResults().get(pr_index);

            for (int mv_index = 0; mv_index < data.size(); mv_index++) {
                if (proccesed.contains(mv_index))
                    continue;

                MoveItc mv_item = data.get(mv_index);
                if (mv_item.getTaskName() == null) {
                    if (mv_item.getFileName() != null) {
                        mv_item.setTaskName(mv_item.getFileName());
                    } else {
                        continue;
                    }
                }

                /** if task name not contains a full filename then search without extension */
                String _filename = pr_item.getFileName();

                ProftpdVsMoveIt(newMoveIt, proccesed, pr_item, mv_index, mv_item, _filename);
            }

            if (size == proccesed.size()) {
                notMovement.add(pr_item);
            }
            size = proccesed.size();
        }
        // Second round vs filename without extension
        for (int pr_index = 0; pr_index < notMovement.size(); pr_index++) {
            pr_item = notMovement.get(pr_index);

            for (int mv_index = 0; mv_index < data.size(); mv_index++) {
                if (proccesed.contains(mv_index))
                    continue;

                MoveItc mv_item = data.get(mv_index);
                if (mv_item.getTaskName() == null)
                    continue;

                String _filename = pr_item.GetFileNameWithOutExtension();

                ProftpdVsMoveIt(newMoveIt, proccesed, pr_item, mv_index, mv_item, _filename);
            }
        }

        data = newMoveIt;

    }

    private void ProftpdVsMoveIt(ArrayList<MoveItc> newMoveIt, ArrayList<Integer> proccesed, Proftpd pr_item,
            int mv_index, MoveItc mv_item, String _filename) {
        // if task name contains the filename this move belongs to this job
        if (_filename !=null && mv_item.getTaskName().contains(_filename)) {
            mv_item.setFileName(pr_item.getFileName());
            proccesed.add(mv_index);
            // if task name contains uploaded this is the delivered targeg platform time.
            if (mv_item.getTaskName().toLowerCase().contains("uploaded")) {
                pr_item.setDeliveredToTargetPlatform(mv_item.getTime());
            }
            // Lastest movement from this job
            if (DateUtl.getDateFromEpoch(mv_item.getTime())
                    .after(DateUtl.getDateFromEpoch(pr_item.getLastMovement()))) {
                pr_item.setLastMovement(mv_item.getTime());
            }

            String taskname = mv_item.getTaskName();
            ArrayList<String> matches = RegexUtl.extractDataFromString(taskname,
                    new String[] { "((?!.*\\/))(.*)(?>\\.\\w{1,3}\\s)", "((?!.*\\/))(.*)(?>\\.[a-zA-Z]{1,3}'\\s)",
                            "((?!.*\\\\))(.*)(?>\\.\\w{1,3}\\Z)", "(?!.*\\/)(.*)(?=\\.\\w{1,3}\"\\z)",
                            "(?<=inbound\\/)(.*)(?=\\sto)" },
                    new String[] { "'" }, new String[] { "\\", "/", " " });
            pr_item.setAssociatedFiles(matches);
            pr_item.setAssociatedFiles(_filename);
            newMoveIt.add(mv_item);
        }
    }

    @Override
    public void CompleteFromT2App(ArrayList<T2App> data) {
        log.info("T2App -> Proftpd");
        for (int t2_index = 0; t2_index < data.size(); t2_index++) {
            T2App t2AppItem = data.get(t2_index);
            for (int pr_index = 0; pr_index < this.getData().getResults().size(); pr_index++) {
                Proftpd prItem = this.getData().getResults().get(pr_index);
                for (int assoc_index = 0; assoc_index < prItem.getAssociatedFiles().size(); assoc_index++) {
                    String file = prItem.getAssociatedFiles().get(assoc_index);
                    if (t2AppItem.getFileName().contains(file)) {
                        prItem.setAppCode(t2AppItem.getAppCode());
                        prItem.setJobNumber(t2AppItem.getJobNumber());
                        prItem.setSchedule(DateUtl.convertToUnixTime(t2AppItem.getSchedule()));
                        prItem.setPlatformStartTime(DateUtl.convertToUnixTime(t2AppItem.getPlatformStartTime()));
                        break;
                    }
                }
            }
        }
    }

    public ProftpController() {
    }

    public void AddAditionalJobs(ArrayList<Proftpd> jobs) {
        ArrayList<Proftpd> toAdd = new ArrayList<Proftpd>();
        // New jobs
        for (int index = 0; index < this.data.getResults().size(); index++) {
            Proftpd newJobs = this.data.getResults().get(index);
            // Jobs that exist on elastic searc
            boolean found = false;
            for (int nj_index = 0; nj_index < jobs.size(); nj_index++) {
                Proftpd elasticJobs = jobs.get(nj_index);
                if (newJobs.getFileName().equals(elasticJobs.getFileName())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                toAdd.add(newJobs);

        }
        jobs.addAll(toAdd);
        this.data.setResults(jobs);

    }

    public ArrayList<Proftpd> GetLatestFileNames(Setting sett, int interval) throws IOException {
        // IF IS NULL RETRIEVE DATA FROM ELASTIC SEARCH
        log.info("Request a FileNames from latest " + interval + " day(s)");
        ElasticSearchController run = new ElasticSearchController();
        sett.setQuery(sett.getQuery().replace("changeme", DateUtl.GetInterval(interval)));
        String stringResult = run.RunQuery(sett);
        // CLEAR UNNECESSARY TAGS
        stringResult = ClearTags(stringResult);
        if (stringResult.equals("{ }") || stringResult.contains("{  \"error\" : {    \"root_cause\" : [ "))
            return new ArrayList<Proftpd>();

        // PARSE RESULT TO OBJECT
        ArrayList<Proftpd> resObj = JsonUtl.ProftpdStrToObj(stringResult);

		// RETURN VALUE
		if (resObj != null) {
			log.info(resObj.size() + " Filenames were retrieved from ElastichSearch");
			return resObj;
		} else {
			log.info("0 Filenames were retrieved from ElastichSearch");
			return new ArrayList<Proftpd>();
		}
    }

    private String ClearTags(String strResult) {
        strResult = strResult.replace("\n", "").replace("\r", "").replace("{  \"hits\" : {    \"hits\" : ", "")
                .replace("{        \"_source\" :", "").replace("        }      },", "},").replace("}    ]  }}", "]");
        return strResult;
    }

    @Override
    public void CompleteFromRpdsInput(ArrayList<RpdsInput> data) {
        for (int idx = 0; idx < data.size(); idx++) {
            RpdsInput item = data.get(idx);

            List<Proftpd> proftpdSelected = this.data.getResults().stream()
                    .filter(x -> x.getFileName() != null
                            && item.getInFileName().toLowerCase().trim().contains(x.getFileName().toLowerCase().trim())
                            && x.getPlatform().equals("RPDS"))
                    .collect(Collectors.toList());

            if (proftpdSelected.size() > 0) {
                for (int index = 0; index < proftpdSelected.size(); index++) {
                    Proftpd proftpd = proftpdSelected.get(index);
                    proftpd.setAppCode(item.getAppCode());
                    proftpd.setJobNumber(item.getJobNumber());
                }
            } else {
                proftpdSelected = this.data.getResults().stream()
                        .filter(x -> x.GetFileNameWithOutExtension() != null
                                && item.getInFileName().toLowerCase().trim()
                                        .contains(x.GetFileNameWithOutExtension().toLowerCase().trim())
                                && x.getPlatform().equals("RPDS"))
                        .collect(Collectors.toList());

                for (int index = 0; index < proftpdSelected.size(); index++) {
                    Proftpd proftpd = proftpdSelected.get(index);
                    proftpd.setAppCode(item.getAppCode());
                    proftpd.setJobNumber(item.getJobNumber());
                }
            }

        }

    }

}