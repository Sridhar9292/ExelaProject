package com.exelatech.ecx.backend.service;


import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.model.ReturnDashboard;
import org.springframework.stereotype.Service;

@Service("returnDashboardManager")
//@Service("dashboardManager")
public interface ReturnDashboardManager {
    ReturnDashboard processEvent(ECXEvent event) throws Exception;
    ReturnDashboard createReturnDashboard(String id) throws Exception;
}
