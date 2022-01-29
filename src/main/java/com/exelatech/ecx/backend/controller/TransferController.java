package com.exelatech.ecx.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.exelatech.ecx.backend.dao.DpsJob;
import com.exelatech.ecx.backend.domain.MoveItc;
import com.exelatech.ecx.backend.domain.Proftpd;
import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.util.CheckStatus;
import com.exelatech.ecx.backend.util.DateUtl;

public class TransferController {
    static Logger log = Logger.getLogger(TransferController.class.getName());

    public void Run() {

        while (true) {
            CheckStatus.Check();
            log.info("Starting transfer application");
            Date dtInicial = new Date();
            try {

                // Load config file
                SettingController settCtrl = new SettingController();
                settCtrl.Load();

                // Start PROFTPD process
                ProftpController proftpdCtrl = new ProftpController();
                proftpdCtrl.Setting(settCtrl.GetSetting("PROFTPD"));
                // Request data from splunk
                proftpdCtrl.RequestData();

                CheckStatus.Check();

                proftpdCtrl
                        .AddAditionalJobs(proftpdCtrl.GetLatestFileNames(settCtrl.GetSetting("PROFTPD_ELASTICSEARCH"),
                                settCtrl.GetSetting("GENERAL").getDpsMoveIntervalDays()));

                // Mapping
                proftpdCtrl.DataMapping();
                // Aditional data transformation
                proftpdCtrl.DataTransformation();

                CheckStatus.Check();

                // Get data from RPDS Input
                RpdsInputController rpdsCtrl = new RpdsInputController();
                rpdsCtrl.Setting(settCtrl.GetSetting("RPDS_INPUT"));
                // Request data from splunk
                rpdsCtrl.RequestData();
                // Set jobnumber and appcode to RPDS Files
                proftpdCtrl.CompleteFromRpdsInput(rpdsCtrl.getData().getResults());

                CheckStatus.Check();

                // Start SENDER_RECEIVER process
                SenderReceiverController senderCtrl = new SenderReceiverController();
                senderCtrl.Setting(settCtrl.GetSetting("SENDER_RECEIVER"));
                // Request data from splunk
                senderCtrl.RequestData();

                MoveItcController moveCtrl = new MoveItcController();
                moveCtrl.Setting(settCtrl.GetSetting("MOVEITC"));
                // Add filter to moveit query
                moveCtrl.SetQueryFilter(proftpdCtrl.getData().getResults());
                // Request Moveit Data from splunk
                moveCtrl.RequestData();

                CheckStatus.Check();

                proftpdCtrl.DataTransformation(); /** to update elapsed time */
                // Interchange data
                proftpdCtrl.CompleteFromMoveIt(moveCtrl.getData().getResults());

                CheckStatus.Check();

                T2AppController t2App = new T2AppController();
                t2App.Setting(settCtrl.GetSetting("T2APP"));
                // Add filter to t2APP query
                t2App.SetQueryFilter(proftpdCtrl.getData().getResults());
                // Request Moveit Data from splunk
                t2App.RequestData();
                // Interchange data
                proftpdCtrl.CompleteFromT2App(t2App.getData().getResults());
                // Mapping
                proftpdCtrl.DataMapping(); /** Mapping again sla field after get new app codes from t2app */
                // Aditional data transformation
                proftpdCtrl.DataTransformation(); /** to update elapsed time */

                // Get data from job activity
                PrismaController prisma = new PrismaController();
                prisma.Setting(settCtrl.GetSetting("GENERAL"));
                prisma.RequestData();

                // //Get data from dps monitor and mapping
                Dps2Controller dpsCtrl = new Dps2Controller(settCtrl.GetSetting("GENERAL"));
                dpsCtrl.GetLatestDpsJobs(settCtrl.GetSetting("DPS_MONITOR_ELASTICSEARCH"),
                        settCtrl.GetSetting("GENERAL").getDpsMonitorInterval() + 5);
                dpsCtrl.RequestData();
                dpsCtrl.FilterNewDpsJobs(); // To remove jobs that is already on elastic search
                dpsCtrl.Mapping(proftpdCtrl.getData().getResults());
                dpsCtrl.CompleteFromJobActivity(prisma.getData());
                dpsCtrl.CompleteFromSenderReceiver(senderCtrl.getData().getResults());

                ElasticSearchController elsticCtrl = new ElasticSearchController();
                
                // Bulk PROFTPD DATA
                Setting settingBulkProftd = settCtrl.GetSetting("BULK_PROFTPD");
                ArrayList<Proftpd> listBulkProftd = proftpdCtrl.getData().getResults();
                for (Proftpd proftpd : listBulkProftd) {
                	proftpd.setType(settingBulkProftd.getType());
				}
                elsticCtrl.Setting(settingBulkProftd);
                elsticCtrl.BuildBulkStatment(listBulkProftd);
                elsticCtrl.Bulk();

                // Bulk MOVEIT DATA
                Setting settingBulkMoveitc = settCtrl.GetSetting("BULK_MOVEITC");
                ArrayList<MoveItc> listBulkMoveitc = moveCtrl.getData().getResults();
                for (MoveItc moveItc : listBulkMoveitc) {
                	moveItc.setType(settingBulkMoveitc.getType());
				}
                elsticCtrl.Setting(settingBulkMoveitc);
                elsticCtrl.BuildBulkStatment(listBulkMoveitc);
                elsticCtrl.Bulk();
                
                // Bulk BULK_DPSMONITOR
                Setting settingBulkDpsMonitor = settCtrl.GetSetting("BULK_DPSMONITOR");
                ArrayList<DpsJob> listBulkDpsMonitor = dpsCtrl.getDpsList();
                for (DpsJob dpsJob : listBulkDpsMonitor) {
                	dpsJob.setType(settingBulkDpsMonitor.getType());
				}
                elsticCtrl.Setting(settingBulkDpsMonitor);
                elsticCtrl.BuildBulkStatment(listBulkDpsMonitor);
                elsticCtrl.Bulk();

                dpsCtrl = null;
                prisma = null;
                proftpdCtrl = null;
                senderCtrl = null;
                moveCtrl = null;
                elsticCtrl = null;

                Date dtFinal = new Date();
                log.info("Process carried out in "
                        + DateUtl.CalculateElapsedTime(dtInicial.getTime(), dtFinal.getTime()));
                log.info("Waiting " + settCtrl.GetSetting("GENERAL").getExecutionIntervalOnSecs() + " + secs \r\n");

                TimeUnit.SECONDS.sleep(settCtrl.GetSetting("GENERAL").getExecutionIntervalOnSecs());

            } catch (Exception e) {
            	e.printStackTrace();
                log.fatal(e.getMessage());
            }
       }
    }

    public TransferController() {
    }

}