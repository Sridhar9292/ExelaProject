package com.exelatech.ecx.backend.service;

import java.util.concurrent.Future;

//import com.exelatech.ecxapi.monitor.SLAMonitor;
import org.springframework.stereotype.Service;

import com.exelatech.ecx.backend.model.Dashboard;
import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.model.ExpectedDatafeed;
import com.exelatech.ecx.backend.monitor.SLAMonitor;

@Service("dashboardManager")
public interface DashboardManager {
	Dashboard processEvent(ECXEvent event) throws Exception;

	Dashboard createDashboard(String id) throws Exception;

	// void addComments(String id, int feedIndex, Comment comment) throws Exception;
	
	Future<SLAMonitor> monitorSLA(ExpectedDatafeed expectedDatafeed, ElasticSearchManager<Dashboard> dashboardService,
			String dashboardID);
}
