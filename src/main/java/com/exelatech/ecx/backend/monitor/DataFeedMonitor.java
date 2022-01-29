package com.exelatech.ecx.backend.monitor;

import com.exelatech.ecx.backend.jms.EventMonitorListenerImpl;
import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.workflow.ECXEvents;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by VenkataDurgavarjhula on 12/4/2015.
 */
public class DataFeedMonitor {
    private EventMonitor exportMonitor;
    private EventMonitor sendMonitor;
    public DataFeedMonitor(JmsTemplate jmsTemplate){
        this.exportMonitor = new RemitBillerFeedMonitor(ECXEvents.BillerRemitDataImported, ECXEvents.BillerRemitDataExportRequested, ECXEvents.BillerRemitDataExported, ECXEvents.BillerRemitDataExportComplete, ECXEvents.BillerRemitDataExportError, ECXEvents.BillerRemitDataExportWarning);
        this.exportMonitor.setListener(new EventMonitorListenerImpl(jmsTemplate));

        this.sendMonitor = null;//new RemitBillerFeedMonitor(ECXEvents.BillerRemitDataImported, ECXEvents.BillerRemitDataExported, ECXEvents.ExportRemitDataSent, ECXEvents.BillerRemitDataSendComplete, ECXEvents.BillerRemitDataSendComplete);
//        this.sendMonitor.setListener(new EventMonitorListenerImpl(jmsTemplate));
    }

    public void notify(ECXEvent event){
        try {
            exportMonitor.notify(event);
//            sendMonitor.notify(event);
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
