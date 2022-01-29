package com.exelatech.ecx.backend.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.exelatech.ecx.backend.domain.MoveItc;
import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.domain.SplunkResult;
import com.exelatech.ecx.backend.service.IProdFTPD;
import com.exelatech.ecx.backend.service.ISplunkProcessing;
import com.exelatech.ecx.backend.util.DateUtl;
import com.exelatech.ecx.backend.util.JsonUtl;
import com.exelatech.ecx.backend.util.RegexUtl;

public class MoveItcController implements ISplunkProcessing {
    static Logger log = Logger.getLogger(MoveItcController.class.getName());

    private SplunkResult<MoveItc> data;
    Setting _setting;

    public SplunkResult<MoveItc> getData() {
        return data;
    }

    public void setData(SplunkResult<MoveItc> data) {
        this.data = data;
    }

    @Override
    public void Setting(com.exelatech.ecx.backend.domain.Setting setting) {
        this._setting = setting;

    }

    @Override
    public void RequestData() throws Exception {
        if (!this._setting.getQuery().contains("NO RESULT")) {

            SplunkController moveIt = new SplunkController(this._setting);
            moveIt.getJobId();
            String pp = moveIt.getResults();
            data = JsonUtl.SplMoveItStrToObj(pp);
            log.info(data.getResults().size() + " records were retrived for MOVEITC");

            SetTimeEpoch();
        } else {
            data = new SplunkResult<MoveItc>();
        }
    }

    @Override
    public void DataTransformation() {

    }

    @Override
    public void DataMapping() {
    }

    @Override
    public void SetTimeEpoch() {
        for (int index = 0; index < getData().getResults().size(); index++) {
            MoveItc item = getData().getResults().get(index);
            item.setTime(DateUtl.getEpochFromDateString(item.getFileName().substring(0, 19), "yyyy-MM-dd HH:mm:ss"));

            ArrayList<String> extract = RegexUtl.extractDataFromString(item.getFileName(),
                    new String[] { "(?=T\\d)(.*?)(?=((:|\\s)))" }, new String[] { "'" }, new String[] { "'" });

            if (extract.size() > 0)
                item.setTaskId(extract.get(0));
            extract = RegexUtl.extractDataFromString(item.getFileName(), new String[] { "(?<=\\d\\d\\d:\\s).*" },
                    new String[] {}, new String[] {});
            if (extract.size() > 0)
                item.setTaskName(extract.get(0));
        }
    }

    @Override
    public <T extends IProdFTPD> void SetQueryFilter(ArrayList<T> data) {

        StringBuilder strBuilder = new StringBuilder("");
        for (int index = 0; index < data.size(); index++) {
            T Item = data.get(index);
            if (Item.GetSource().contains("MoveIt")) {
                if (strBuilder.length() > 0)
                    strBuilder.append(" OR ");
                strBuilder.append(" ".concat(Item.GetFileNameWithOutExtension()).concat(" "));
            }
        }
        String splunkQuery = _setting.getQuery();

        if (strBuilder.toString().length() > 0)
            _setting.setQuery(splunkQuery.replace("changeme", strBuilder.toString()));
        else
            _setting.setQuery(splunkQuery.replace("changeme", "********NO RESULT*********"));

    }

    public MoveItcController() {
    }

}