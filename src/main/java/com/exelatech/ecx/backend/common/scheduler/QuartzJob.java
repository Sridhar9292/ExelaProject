package com.exelatech.ecx.backend.common.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exelatech.ecx.backend.model.Dashboard;
import com.exelatech.ecx.backend.model.ReturnDashboard;
import com.exelatech.ecx.backend.service.DashboardManager;
import com.exelatech.ecx.backend.service.ElasticSearchManager;
import com.exelatech.ecx.backend.service.ReturnDashboardManager;
import com.exelatech.ecx.backend.util.DateUtil;

@Component
public class QuartzJob {

	@Autowired
	private ElasticSearchManager<Dashboard> dashboardService;
	@Autowired
	private DashboardManager dashboardManager;
	// @Autowired
	// private ExpectedDatafeedMapper expectedDatafeedMapper;
	@Autowired
	private ElasticSearchManager<ReturnDashboard> returnDashboardService;
	@Autowired
	private ReturnDashboardManager returnDashboardManager;

	// @Autowired
	// private ElasticSearchManager<ECXEvent> eventService;

	public void prepareDashboard() {
		try {
			Dashboard dashboard = dashboardService.get(DateUtil.getCurrentUTCDate("yyyyMMdd"));
			//System.out.println("Dashboard Quartz ==> "+dashboard);
			//System.out.println("DateUtil.getCurrentUTCDate(\"yyyyMMdd\"):...."+DateUtil.getCurrentUTCDate("yyyyMMdd"));
			if (dashboard == null) {
				dashboard = dashboardManager.createDashboard(DateUtil.getCurrentUTCDate("yyyyMMdd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public void returnDashboard() {
		try {
			//System.out.println("QuartzJob.returnDashboard()");
			ReturnDashboard returnDashboard = returnDashboardService.get(DateUtil.getCurrentUTCDate("yyyyMMdd"));
			if (returnDashboard == null) {
				//System.out.println("QuartzJob.returnDashboard()1");
				returnDashboard = returnDashboardManager.createReturnDashboard(DateUtil.getCurrentUTCDate("yyyyMMdd"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	 

}
