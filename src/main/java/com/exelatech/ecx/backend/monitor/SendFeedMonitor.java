package com.exelatech.ecx.backend.monitor;

import com.exelatech.ecx.backend.workflow.ECXEvents;

/**
 * Created by VenkataDurgavarjhula on 11/16/2015.
 */
public class SendFeedMonitor extends RemitBillerFeedMonitor {
    public SendFeedMonitor(){
        super(ECXEvents.BillerRemitDataImported,ECXEvents.BillerRemitDataExtracted,ECXEvents.ExportRemitDataSent,ECXEvents.BillerRemitDataSendComplete,ECXEvents.BillerRemitDataDeliveryError,ECXEvents.BillerRemitDataExportWarning);
    }
}
