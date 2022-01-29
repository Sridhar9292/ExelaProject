package com.exelatech.ecx.backend.common.scheduler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.exelatech.ecx.backend.controller.TransferController;

@Component
public class Scheduler {

	@Autowired
	private QuartzJob quartzJob;
	
	//@Scheduled(fixedDelay = 60000)
	//@PostConstruct
	//@Scheduled(cron="*/5 * * * * *")
	@Scheduled(cron="0 1 1/1 * * *")
	public void cronJobDashboardScheduler() {
		//System.out.println("Scheduler Started");
		quartzJob.prepareDashboard();
		quartzJob.returnDashboard();
		//System.out.println("Scheduler Completed");
	 }
	/*
	@Scheduled(cron="0 0 5 1/1 * ? *")
	public void cronJobReturnDashboardScheduler() {
		//System.out.println("Return Scheduler Started1");
		quartzJob.returnDashboard();
		//System.out.println("Return Scheduler Completed");
	 }
	*/
	
}
