package com.exelatech.ecx.backend.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.exelatech.ecx.backend.dao.Dao;
import com.exelatech.ecx.backend.dao.DpsJob;
import com.exelatech.ecx.backend.dao.DpsJobStatus;
import com.exelatech.ecx.backend.dao.Prisma;
import com.exelatech.ecx.backend.domain.Proftpd;
import com.exelatech.ecx.backend.domain.SenderReceiver;
import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.service.IMapping;
import com.exelatech.ecx.backend.util.DateUtl;
import com.exelatech.ecx.backend.util.JsonUtl;

import org.apache.log4j.Logger;

public class Dps2Controller implements IMapping<ArrayList<Proftpd>> {

	static Logger log = Logger.getLogger(Dps2Controller.class.getName());

	private ArrayList<DpsJob> DpsList;
	private List<DpsJobStatus> dpsJobStatus;
	private ArrayList<DpsJob> LatestDpsJobs;
	Setting settCtrl;

	public Dps2Controller(Setting _setting) {
		settCtrl = _setting;
	}

	public Dps2Controller() {

	}

	@Override
	public void RequestData() {
		LocalDate localdate = LocalDate.now().minusDays(settCtrl.getDpsMonitorInterval());
		// LocalDate localdate = LocalDate.now().minusDays(30);

		Date date = java.sql.Date.valueOf(localdate);

		if (DpsList == null && DpsList == null) {
			log.info("Connecting to ECX Monitor");
			Dao<DpsJob> dao = new Dao<DpsJob>("ecx_monitor");

			log.info("Getting jobs from Ecx Monitor");
			this.DpsList = new ArrayList<DpsJob>(dao.selectMultiple("DpsJob.getJobsByTime", "dps_start_time", date));
			dao.Close();
		}

		if (dpsJobStatus == null && dpsJobStatus == null) {
			Dao<DpsJobStatus> dao = new Dao<DpsJobStatus>("ecx_monitor");

			log.info("Getting jobs status from Ecx Monitor");
			this.dpsJobStatus = dao.selectMultiple("DpsJobStatus.getJobsStatus");
			dao.Close();
		}

	}

	public void CompleteFromSenderReceiver(ArrayList<SenderReceiver> data) {
		for (int index = 0; index < data.size(); index++) {
			SenderReceiver sender = data.get(index);

			List<DpsJob> dpsJobs = DpsList.stream()
					.filter(x -> x.getMajorWO() != null && x.getMajorWO().trim().equals(sender.getJobNumber().trim()))
					.collect(Collectors.toList());

			for (int job_index = 0; job_index < dpsJobs.size(); job_index++) {
				DpsJob item = dpsJobs.get(job_index);
				item.setDeliveryStatus(sender.getDeliveryStatus());
			}
		}

	}

	public void CompleteFromJobActivity(ArrayList<Prisma> data) {
		for (int index = 0; index < data.size(); index++) {
			Prisma prismaJobActivity = data.get(index);

			List<DpsJob> dpsJobs = DpsList.stream()
					.filter(x -> x.getJobNumber() != null
							&& x.getJobNumber().trim().equals(prismaJobActivity.getJobNumber().trim()))
					.collect(Collectors.toList());

			for (int job_index = 0; job_index < dpsJobs.size(); job_index++) {
				DpsJob item = dpsJobs.get(job_index);

				// Print Status If all associated records (for a given job number) contain
				// Ord.Status = “Printed” or “Completed” display “Printed” otherwise display
				// “Printing”
				item.setMajorWO(prismaJobActivity.getMajorWO());
				if (prismaJobActivity.getStatus().equals("Printed")
						|| prismaJobActivity.getStatus().equals("Completed")) {
					item.setPrintStatus("Printed");
				} else {
					item.setPrintStatus("Printing");
				}

				// Insertion Status If any associated record (for a given job number) contain
				// Ord.Status = “Rework” display “Recoveries Pending”
				// If all associated records (for a given job number) contain Ord.Status =
				// “Printed” or “Completed” display “Completed” otherwise display “Inserting”

				if (prismaJobActivity.getStatus().equals("Rework")) {
					item.setInsertionStatus("Recoveries Pending");
				} else {
					if (prismaJobActivity.getStatus().equals("Printed")
							|| prismaJobActivity.getStatus().equals("Completed")) {
						item.setInsertionStatus("Completed");
					} else {
						item.setInsertionStatus("Inserting");
					}
				}
				// If all associated records (for a given job number) contain Ord.Status =
				// “Completed” display latest ord.MailDate value as MM/DD/YYYY

				if (prismaJobActivity.getStatus().equals("Completed") && prismaJobActivity.getMailDate() != null) {
					item.setMailDate(String.valueOf(prismaJobActivity.getMailDate().getTime()));
				}

			}
		}

	}

	public void Mapping(ArrayList<Proftpd> DataToMap) {
		for (int index = 0; index < DpsList.size(); index++) {
			DpsJob job = DpsList.get(index);
			List<Proftpd> proftpdSelected = DataToMap.stream()
					.filter(x -> x.getJobNumber() != null
							&& x.getJobNumber().toLowerCase().trim().equals(job.getJobNumber().toLowerCase().trim()))
					.collect(Collectors.toList());

			List<DpsJobStatus> jobStatus = this.dpsJobStatus.stream()
					.filter(x -> x.getId() != null && x.getId().equals(job.getStatus())).collect(Collectors.toList());

			for (int indexjob = 0; indexjob < proftpdSelected.size(); indexjob++) {
				Proftpd item = proftpdSelected.get(indexjob);
				if (jobStatus != null && jobStatus.size() > 0) {
					job.setStatus(jobStatus.get(0).getName());
				}
				job.setAppCode(item.getAppCode());
				job.setPlatForm(item.getPlatform());
				job.setTime("");
			}
		}
		DpsList = new ArrayList<DpsJob>(
				DpsList.stream().filter(x -> x.getPlatForm() != null).collect(Collectors.toList()));

	}

	public ArrayList<DpsJob> GetLatestDpsJobs(Setting sett, int interval) throws IOException {
		// IF IS NULL RETRIEVE DATA FROM ELASTIC SEARCH
		log.info("Request a DpsJobs from latest " + interval + " day(s)");
		ElasticSearchController run = new ElasticSearchController();
		sett.setQuery(sett.getQuery().replace("changeme", DateUtl.GetInterval(interval)));
		String stringResult = run.RunQuery(sett);
		// CLEAR UNNECESSARY TAGS
		stringResult = ClearTags(stringResult);
		if (stringResult.equals("{ }") || stringResult.contains("{  \"error\" : {    \"root_cause\" : [ "))
			return new ArrayList<DpsJob>();

		// PARSE RESULT TO OBJECT
		LatestDpsJobs = JsonUtl.DpsJobStrToObj(stringResult);

		log.info(LatestDpsJobs.size() + " DpsJobs were retrieved");

		// RETURN VALUE
		return LatestDpsJobs;
	}

	public void FilterNewDpsJobs() {

		if (this.LatestDpsJobs == null) {
			this.LatestDpsJobs = new ArrayList<DpsJob>();
		}

		for (int index = 0; index < this.DpsList.size(); index++) {
			DpsJob item = this.DpsList.get(index);

			List<DpsJob> jobs = this.LatestDpsJobs.stream()
					.filter(x -> x.getJobNumber() != null && x.getJobNumber().equals(item.getJobNumber()))
					.collect(Collectors.toList());

			if (jobs.size() == 0)
				this.LatestDpsJobs.add(item);

		}
		this.DpsList = this.LatestDpsJobs;

		DpsList = new ArrayList<DpsJob>(
				DpsList.stream().filter(x -> x.getJobNumber() != null).collect(Collectors.toList()));
	}

	private String ClearTags(String strResult) {
		strResult = strResult.replace("\n", "").replace("\r", "").replace("{  \"hits\" : {    \"hits\" : ", "")
				.replace("{        \"_source\" :", "").replace("        }      },", "},").replace("}    ]  }}", "]");
		return strResult;
	}

	public ArrayList<DpsJob> getDpsList() {
		return DpsList;
	}

	public void setDpsList(ArrayList<DpsJob> dpsList) {
		DpsList = dpsList;
	}

}