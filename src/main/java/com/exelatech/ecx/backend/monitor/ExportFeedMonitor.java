package com.exelatech.ecx.backend.monitor;

import com.exelatech.ecx.backend.workflow.ECXEvents;

/**
 * Created by VenkataDurgavarjhula on 11/16/2015.
 */
public class ExportFeedMonitor extends RemitBillerFeedMonitor {
    public ExportFeedMonitor(){
        super(ECXEvents.BillerRemitDataImported, ECXEvents.BillerRemitDataExportRequested, ECXEvents.BillerRemitDataExported, ECXEvents.BillerRemitDataExportComplete, ECXEvents.BillerRemitDataExportError, ECXEvents.BillerRemitDataExportWarning);
    }
}
