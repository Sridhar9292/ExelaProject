package com.exelatech.ecx.backend.monitor;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;

import com.exelatech.ecx.backend.jms.EventMonitorListenerImpl;
import com.exelatech.ecx.backend.model.Dashboard;
import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.model.ExpectedDatafeed;
import com.exelatech.ecx.backend.service.ElasticSearchManager;
import com.exelatech.ecx.backend.util.CommonUtil;
import com.exelatech.ecx.backend.util.DateUtil;

public class SLAMonitor extends Thread{
    private ElasticSearchManager<Dashboard> dashboardService;
    private EventMonitorListener eventMonitorListener;
    private ExpectedDatafeed expectedDataFeed;
    private String dashboardID;

    public SLAMonitor(JmsTemplate jmsTemplate, ExpectedDatafeed expectedDataFeed, ElasticSearchManager<Dashboard> dashboardService, String dashboardID){
        this.eventMonitorListener = new EventMonitorListenerImpl(jmsTemplate);
        this.expectedDataFeed = expectedDataFeed;
        this.dashboardService = dashboardService;
        this.dashboardID = dashboardID;
    }

    @Async
    public void run(){
        long sleeptime=0;
        try {
            Date nextRunDate = CommonUtil.getNextRunDate(dashboardID, expectedDataFeed.getFileSchedule(), TimeZone.getTimeZone("America/Los_Angeles"));

            Calendar slaCalendar = Calendar.getInstance();
            slaCalendar.setTime(nextRunDate);
            slaCalendar.add(Calendar.HOUR, expectedDataFeed.getGracePeriod());

            sleeptime = slaCalendar.getTimeInMillis()-new Date().getTime();
            if(sleeptime>0){ //if less than 0, already sla missed
                System.out.println("Sleeping for: "+ sleeptime + "," +expectedDataFeed.toString());
                sleep(sleeptime);
            }else{
                sleep(30000); //wait 30 secs before triggering sla emails
            }
            Dashboard dashboard = dashboardService.get(dashboardID);
            if(dashboard==null){
                //sleep for 30 secs
                Thread.sleep(30000);
                dashboard = dashboardService.get(dashboardID);
            }
            boolean dataFeedReceived = dashboard.isDataFeedReceived(expectedDataFeed.getFeedID());
            if(!dataFeedReceived){
                ECXEvent event = new ECXEvent("SLAMissedError");
                event.put(ECXEvent.NAME, "SLAMissedError");
                event.put("feedID", String.valueOf(expectedDataFeed.getFeedID()));
                event.put("feedName", expectedDataFeed.getFeedName());
                event.put("vendorCode", expectedDataFeed.getVendorCode());
                event.put("filePattern", expectedDataFeed.getFilePattern());
                event.put("fileType", expectedDataFeed.getFileType());
                event.put("expectedTime", expectedDataFeed.getExpectedTime());
                event.put("fileSchedule", expectedDataFeed.getFileSchedule());
                event.put("singleMulti", expectedDataFeed.getSingleMulti());
                event.put("gracePeriod", String.valueOf(expectedDataFeed.getGracePeriod()));
                event.put("moment", DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss", new Date()));
                //TODO - get context from properties file and set here
//                event.put("context", context);
                event.put(ECXEvent.ERROR_MESSAGE, "File not received on time, missed SLA");
                eventMonitorListener.send(event);
            }
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
