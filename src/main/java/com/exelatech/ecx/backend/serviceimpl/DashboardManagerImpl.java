package com.exelatech.ecx.backend.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.exelatech.ecx.backend.constant.Constants;
import com.exelatech.ecx.backend.dao.mapper.BillerDetailMapper;
import com.exelatech.ecx.backend.dao.mapper.ExpectedDatafeedMapper;
import com.exelatech.ecx.backend.dao.mapper.VendorConfigMapper;
import com.exelatech.ecx.backend.jms.EventMonitorListenerImpl;
import com.exelatech.ecx.backend.model.BillerDetail;
import com.exelatech.ecx.backend.model.BusinessStep.StepColor;
import com.exelatech.ecx.backend.model.Comment;
import com.exelatech.ecx.backend.model.Dashboard;
import com.exelatech.ecx.backend.model.DataFeed;
import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.model.ExpectedDatafeed;
import com.exelatech.ecx.backend.model.VendorConfig;
import com.exelatech.ecx.backend.monitor.DataFeedMonitor;
import com.exelatech.ecx.backend.monitor.SLAMonitor;
import com.exelatech.ecx.backend.repository.DashboardRepository;
import com.exelatech.ecx.backend.service.DashboardManager;
import com.exelatech.ecx.backend.service.ElasticSearchManager;
import com.exelatech.ecx.backend.statetable.StateTable;
import com.exelatech.ecx.backend.statetable.State.Type;
import com.exelatech.ecx.backend.util.CommonUtil;
import com.exelatech.ecx.backend.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Service("dashboardManager")
@Slf4j
public class DashboardManagerImpl implements DashboardManager {

	@Autowired
	StateTable receiveStateTable;

	@Autowired
	StateTable importStateTable;

	@Autowired
	StateTable exportStateTable;

	@Autowired
	StateTable sendStateTable;

	@Autowired
	private BillerDetailMapper billerDetailMapper;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${backend.events.virtual.topic}")
	private String slaDestination;

	@Autowired
	private ExpectedDatafeedMapper expectedDatafeedMapper;

	@Autowired
	private VendorConfigMapper vendorConfigMapper;

	@Autowired
	private ElasticSearchManager<Dashboard> dashboardService;

	@Autowired
	DashboardRepository<Dashboard> dashboardRepo;

	private static Map<String, DataFeedMonitor> dataFeedMonitorMap = new HashMap<>();

	private static Set<String> slaMonitorMap = new HashSet<>();

	@Scheduled(cron = "0 * * * * *")
	public void monitorSLAScheduler() throws IOException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime now = LocalDateTime.now();
		String dashboardID = dtf.format(now);
		Dashboard dashboard = dashboardService.get(dashboardID);
		if (null != dashboard) {
			log.debug("QTest - before expectedDatafeedMapper.getAll(): " + new java.util.Date());
			List<ExpectedDatafeed> expectedDatafeeds = expectedDatafeedMapper.getAll();
			log.debug("QTest - after expectedDatafeedMapper.getAll(): " + new java.util.Date());
			for (ExpectedDatafeed edf : expectedDatafeeds) {
				if (CommonUtil.isFileExpected(dashboardID, edf.getFileSchedule(),
						TimeZone.getTimeZone("America/Los_Angeles"))) {
					// File is expected, check holiday schedule.
					VendorConfig vendorConfig = vendorConfigMapper.getVendorHoliday(edf.getVendorCode(), dashboardID);
					if (vendorConfig != null && vendorConfig.isCheckHoliday()
							&& vendorConfig.getHolidayName() != null) {

					} else {
						// set slaMonitor only for current date dashboard
						if (DateUtil.getCurrentUTCDate("yyyyMMdd").equals(dashboardID)) {
							Date nextRunDate = CommonUtil.getNextRunDate(dashboardID, edf.getFileSchedule(),
									TimeZone.getTimeZone("America/Los_Angeles"));

							Calendar slaCalendar = Calendar.getInstance();
							slaCalendar.setTime(nextRunDate);
							slaCalendar.add(Calendar.HOUR, edf.getGracePeriod());

							long sleeptime = slaCalendar.getTimeInMillis() - new Date().getTime();

							if (sleeptime < 0) {
								boolean dataFeedReceived = dashboard.isDataFeedReceived(edf.getFeedID());
								String uniqueFeed = String.valueOf(edf.getFeedID()) + ":" + dashboardID;
								if (!dataFeedReceived && !slaMonitorMap.contains(uniqueFeed)) {
									ECXEvent event = new ECXEvent("SLAMissedError");
									event.put(ECXEvent.NAME, "SLAMissedError");
									event.put("feedID", String.valueOf(edf.getFeedID()));
									event.put("feedName", edf.getFeedName());
									event.put("vendorCode", edf.getVendorCode());
									event.put("filePattern", edf.getFilePattern());
									event.put("fileType", edf.getFileType());
									event.put("expectedTime", edf.getExpectedTime());
									event.put("fileSchedule", edf.getFileSchedule());
									event.put("singleMulti", edf.getSingleMulti());
									event.put("gracePeriod", String.valueOf(edf.getGracePeriod()));
									event.put("moment", DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss", new Date()));
									event.put(ECXEvent.ERROR_MESSAGE, "File not received on time, missed SLA");
									jmsTemplate.setDefaultDestinationName(slaDestination);
									new EventMonitorListenerImpl(jmsTemplate).send(event);
									slaMonitorMap.add(uniqueFeed);
								}
							}
						}
					}
				}
			}
		}

	}

	@Override
	public Dashboard createDashboard(String id) throws Exception {
		Dashboard dashboard = new Dashboard();

		dashboard.setId(id);
		VendorConfig holiday = vendorConfigMapper.getHoliday(id);
		dashboard.setHoliday(holiday != null ? "Federal Holiday: " + holiday.getHolidayName() : "");
		List<ExpectedDatafeed> expectedDatafeeds = expectedDatafeedMapper.getAll();
		for (ExpectedDatafeed edf : expectedDatafeeds) {
			DataFeed dataFeed = new DataFeed(edf);

			if (CommonUtil.isFileExpected(id, edf.getFileSchedule(), TimeZone.getTimeZone("America/Los_Angeles"))) {
				// File is expected, check holiday schedule.
				VendorConfig vendorConfig = vendorConfigMapper.getVendorHoliday(edf.getVendorCode(), id);
				if (vendorConfig != null && vendorConfig.isCheckHoliday() && vendorConfig.getHolidayName() != null) {
					dataFeed.setExpectedTime("None");
					dataFeed.setFeedExpected(false);
				} else {
					// set slaMonitor only for current date dashboard
					if (DateUtil.getCurrentUTCDate("yyyyMMdd").equals(id)) {
						// monitorSLA(edf, dashboardService, id);
					}
				}
			} else {
				dataFeed.setFeedExpected(false);
				dataFeed.setExpectedTime("None");
			}
			if (edf.getVendorCode().equals("BILLER") || edf.getVendorCode().equals("WU")
					|| edf.getFilePattern().contains("ctribune") || edf.getFilePattern().contains("latimes")) {
				dataFeed.getDataFeedWorkflow().setExportStep(null);
				dataFeed.getDataFeedWorkflow().setSendStep(null);
			}

			dashboard.addDataFeed(dataFeed);
		}

		dashboardRepo.save(dashboard);

		return dashboardService.get(id);

	}

	/*
	 * @Override public Dashboard processEvent(ECXEvent event) throws Exception { //
	 * TODO Auto-generated method stub return null; }
	 */
	@Override
	public synchronized Dashboard processEvent(ECXEvent event) throws Exception {
		log.debug("QTest - entered dashboardManager processEvent : " + new java.util.Date());
		log.debug("eventname: " + event.get(ECXEvent.NAME));
		
		String feedDocID = event.get(ECXEvent.FEED_DOC_ID);
		// If there is no feedDocID set in event, nothing needs to be done.
		long timestamp = Long.valueOf(event.get(ECXEvent.TIMESTAMP));
		String id = DateUtil.getUTCDateFromTimestamp(timestamp, "yyyyMMdd");
		Dashboard dashboard = dashboardService.get(id);

		if (null == dashboard) {
			dashboard = createDashboard(id);
		}

		DataFeed dataFeed = dashboard.getDataFeedByFeedDocID(feedDocID);
		if (dataFeed == null && feedDocID != null) {// match by pattern, not and existing feed
			for (DataFeed df : dashboard.getRemitFeeds()) {
				if (df.getFilePattern() != null) {
					Pattern p = Pattern.compile(df.getFilePattern().replace("*", "\\w+"));
					Matcher m = p.matcher(feedDocID);
					// Specifically for WU as WU has 2 feeds with same pattern
					if (m.matches() && (df.getFeedDocID() == null || df.getFeedDocID().equals(""))) {
						df.setFeedDocID(feedDocID);
						df.setActualTime(DateUtil.getDateTime("HH:mm", new Date()));
						dataFeed = df;
						// dashboardService.save(dashboard);
						dashboardRepo.save(dashboard);
						if (!(df.getVendorCode().equals("BILLER") || df.getVendorCode().equals("WU")
								|| df.getFeedDocID().contains("ctribune")
								|| dataFeed.getFeedDocID().contains("latimes"))) {// export and send monitor not
																					// required for WU, ctribune /
																					// latimes
							jmsTemplate.setDefaultDestinationName(slaDestination);
							DataFeedMonitor dataFeedMonitor = new DataFeedMonitor(jmsTemplate);
							dataFeedMonitorMap.put(df.getFeedDocID(), dataFeedMonitor);
							log.debug("Adding monitor for :" + feedDocID);
						}
						break;
					}
				}
			}
		}
		// New or extra feed, add to remitfeed list
		// TODO - if vendor is WU - do not add Export and Sent. (Import is complete,
		// VendorRemitFileProcessed). for others BillerRemitDataSendComplete is
		// considered complete
		if (dataFeed == null && feedDocID != null) {
			List<ExpectedDatafeed> expectedDatafeeds = expectedDatafeedMapper.getAll();
			for (ExpectedDatafeed edf : expectedDatafeeds) {
				Pattern p = Pattern.compile(edf.getFilePattern().replace("*", "\\w+"));
				Matcher m = p.matcher(feedDocID);
				// Specifically for WU as WU has 2 feeds with same pattern
				if (m.matches()) {
					dataFeed = new DataFeed();
					dataFeed.setFeedID(dashboard.getRemitFeeds().size() + 1l);
					dataFeed.setFeedDocID(feedDocID);
					dataFeed.setFeedName(edf.getFeedName());
					dataFeed.setVendorCode(edf.getVendorCode());
					dataFeed.setFileType("M");
					dataFeed.setActualTime(DateUtil.getDateTime("HH:mm", new Date()));
					if (dataFeed.getVendorCode().equals("BILLER") || dataFeed.getVendorCode().equals("WU")
							|| dataFeed.getFeedDocID().contains("ctribune")
							|| dataFeed.getFeedDocID().contains("latimes")) {
						dataFeed.getDataFeedWorkflow().setExportStep(null);
						dataFeed.getDataFeedWorkflow().setSendStep(null);
					} else {// for WU ctribune / latimes, monitor is not required
						jmsTemplate.setDefaultDestinationName(slaDestination);
						DataFeedMonitor dataFeedMonitor = new DataFeedMonitor(jmsTemplate);
						dataFeedMonitorMap.put(dataFeed.getFeedDocID(), dataFeedMonitor);
					}
					dashboard.addDataFeed(dataFeed);
					dashboardRepo.save(dashboard);
					log.debug("Adding monitor for :" + feedDocID);
					break;
				}
			}
		}
		// For capture remit processing time
		if (dataFeed != null) {
			if (event.get(ECXEvent.NAME).equals("ParseVendorRemitFile")) {
				dataFeed.setReceivedTime(DateUtil.getDateTime("HH:mm", new Date()));
				dashboard.addDataFeed(dataFeed);
				dashboardRepo.save(dashboard);
			}
			if (event.get(ECXEvent.NAME).equals("VendorRemitFileProcessed")) {
				dataFeed.setImportedTime(DateUtil.getDateTime("HH:mm", new Date()));
				dashboard.addDataFeed(dataFeed);
				dashboardRepo.save(dashboard);
			}
		}
		// For many events, feed_doc_id would be null and datafeed would be null as it
		// does't match dashboard datafeeds
		if (dataFeed != null) {
			DataFeedMonitor dataFeedMonitor = dataFeedMonitorMap.get(feedDocID);
			if (dataFeedMonitor != null) { // If null, this datafeed is completed, nothing to be done.
				log.debug("Notifying monitor :" + feedDocID + " about event: " + event);
			}
			if (dataFeed.getDataFeedWorkflow().isStateChanged(receiveStateTable, importStateTable, exportStateTable,
					sendStateTable, event)) {
				dashboardRepo.save(dashboard);
			}
			if (event.get(ECXEvent.NAME).equals("BillerRemitDataImported")) {
				boolean flag = true;

				BillerDetail billerDetail = billerDetailMapper.getBillerDetails(event.get(ECXEvent.FEED_DOC_ID),
						event.get(ECXEvent.CLIENT_CODE), event.get(ECXEvent.CLIENT_LOB_CODE));
				if (billerDetail != null) {
					billerDetail.setFeedDocID(event.get(ECXEvent.FEED_DOC_ID));
					BillerDetail billerDelivery = billerDetailMapper.getBillerDeliveryDetails(
							event.get(ECXEvent.CLIENT_CODE), event.get(ECXEvent.CLIENT_LOB_CODE));
					billerDetail.setDelivery(billerDelivery.getDelivery());
					billerDetail.setBillerCode(billerDelivery.getBillerCode());
					if (!(dataFeed.getVendorCode().equals("BILLER") || dataFeed.getVendorCode().equals("WU")
							|| dataFeed.getFeedDocID().contains("ctribune")
							|| dataFeed.getFeedDocID().contains("latimes"))) {
						for (BillerDetail df : dataFeed.getFeedSummary().getBillerDetails()) {
							if (df.getFeedDocID().equals(event.get(ECXEvent.FEED_DOC_ID))
									&& df.getDelivery().equals("TMSPL")
									&& df.getBillerCode().equals(billerDelivery.getBillerCode())) {
								flag = false;
								break;
							}

						}
						
					}

					dataFeed.addBillerDetail(billerDetail);
					dashboardRepo.save(dashboard);
				}
			}

		}

		// For capture remit processing time
		if (dataFeed != null) {
			if (!(dataFeed.getVendorCode().equals("BILLER") || dataFeed.getVendorCode().equals("WU")
					|| dataFeed.getFeedDocID().contains("ctribune") || dataFeed.getFeedDocID().contains("latimes"))) {
				if (event.get(ECXEvent.NAME).equals("BillerRemitDataExportComplete")) {
					dataFeed.setExportedTime(DateUtil.getDateTime("HH:mm", new Date()));
					dashboard.addDataFeed(dataFeed);
					dashboardRepo.save(dashboard);
				}
			}
		}

		// If event Contains feedID try getting dataFeed basedon feedID (Mostly
		// generated from SLA Monitor,
		if (dataFeed == null && event.get("feedID") != null) {
			dataFeed = dashboard.getDataFeedByFeedID(Long.valueOf(event.get("feedID")));
		}

		if (dataFeed != null) {
			// Add Error Message to Comments
			if (event.get(ECXEvent.NAME).endsWith("Error") || event.get(ECXEvent.NAME).endsWith("Warning")) {
				if (event.get(ECXEvent.ERROR_MESSAGE) == null) {
					log.warn("ErrorMessage is null : " + event);
				}
				if (event.get(ECXEvent.ERROR_MESSAGE) != null) {
					if (event.get(ECXEvent.ERROR_MESSAGE).contains("No Data")) {
						dataFeed.getDataFeedWorkflow().getImportStep().getState().setName("No Data");
						dataFeed.getDataFeedWorkflow().getImportStep().getState().setType(Type.FINAL);
						dataFeed.getDataFeedWorkflow().getImportStep().setColor(StepColor.success);
					}
					Comment errorComment = new Comment();
					errorComment.setTimestamp(new Date());
					errorComment.setCommentedBy(Constants.ECX_USERNAME);
					errorComment.setRemarks((event.get(ECXEvent.NAME).endsWith("Error") ? "error: " : "warning: ")
							+ (event.get("errorCode") != null ? event.get("errorCode") + ": " : "")
							+ event.get(ECXEvent.ERROR_MESSAGE));
					dataFeed.addComments(errorComment);
					dashboardRepo.save(dashboard);
				}
			}
		}

		if (dataFeed != null) {
			if (!(dataFeed.getVendorCode().equals("BILLER") || dataFeed.getVendorCode().equals("WU")
					|| (dataFeed.getFeedDocID() != null && dataFeed.getFeedDocID().contains("ctribune"))
					|| (dataFeed.getFeedDocID() != null && dataFeed.getFeedDocID().contains("latimes")))) {

				if (dataFeed.getFeedDocID() != null) {

					// set biller detail export state to "Ëxported"
					if (event.get(ECXEvent.NAME).equals("BillerRemitDataExported")
							|| event.get(ECXEvent.NAME).equals("TMSPLBillerRemitExported")) {

						log.debug("eventname with condition: " + event.get(ECXEvent.NAME));
						log.debug("feedDocId: " + event.get(ECXEvent.FEED_DOC_ID));
						log.debug("clientCode: " + event.get(ECXEvent.CLIENT_CODE));
						log.debug("clientLobCode: " + event.get(ECXEvent.CLIENT_LOB_CODE));

						String feedDocId = event.get(ECXEvent.FEED_DOC_ID);
						String clientCode = event.get(ECXEvent.CLIENT_CODE);
						String clientLobCode = event.get(ECXEvent.CLIENT_LOB_CODE);

						int billerExportCount = 0;
						if (feedDocId.contains("rpps")) {
							billerExportCount = billerDetailMapper.getBillerExportStatus("RPPS_TRANSACTIONS", feedDocId,
									clientCode, clientLobCode);
						} else if (feedDocId.contains("fis")) {
							billerExportCount = billerDetailMapper.getBillerExportStatus("FIS_TRANSACTIONS", feedDocId,
									clientCode, clientLobCode);
						} else {
							billerExportCount = billerDetailMapper.getBillerExportStatus("TRANSACTIONS", feedDocId,
									clientCode, clientLobCode);
						}

						for (BillerDetail df : dataFeed.getFeedSummary().getBillerDetails()) {
							if (billerExportCount == 0 && feedDocId.equals(df.getFeedDocID())
									&& clientCode.equals(df.getClientCode())
									&& clientLobCode.equals(df.getClientLobCode())) {
								df.setState("Exported");
								df.setVendor(dataFeed.getVendorCode());
							}
						}
						log.debug("QTest - after BillerDetail iterate 456: " + new java.util.Date());
						dashboardRepo.save(dashboard);

						int count = 0;
						if (dataFeed.getFeedDocID().contains("rpps")) {
							count = billerDetailMapper.getExportStatus("RPPS_TRANSACTIONS", dataFeed.getFeedDocID());
						} else if (dataFeed.getFeedDocID().contains("fis")) {
							count = billerDetailMapper.getExportStatus("FIS_TRANSACTIONS", dataFeed.getFeedDocID());
						} else {
							count = billerDetailMapper.getExportStatus("TRANSACTIONS", dataFeed.getFeedDocID());
						}
						if (dataFeed.getDataFeedWorkflow().getExportStep().getState().getName().equals("Exporting")
								&& count == 0) {
							dataFeed.getDataFeedWorkflow().getExportStep().getState().setName("Exported");
							dataFeed.getDataFeedWorkflow().getExportStep().getState().setType(Type.FINAL);
							dataFeed.getDataFeedWorkflow().getExportStep().setColor(StepColor.success);
						}

						if (dataFeed.getDataFeedWorkflow().getExportStep().getState().getName().equals("Exporting")
								&& count > 0) {
							dataFeed.getDataFeedWorkflow().getExportStep().getState().setName("Exporting");
							dataFeed.getDataFeedWorkflow().getExportStep().getState().setType(Type.PROCESSING);
							dataFeed.getDataFeedWorkflow().getExportStep().setColor(StepColor.primary);
						}
						dashboardRepo.save(dashboard);

					}

				}

			} else {
				dataFeed.getDataFeedWorkflow().setExportStep(null);
				dataFeed.getDataFeedWorkflow().setSendStep(null);
				dashboardRepo.save(dashboard);
			}
		}
		log.debug("QTest - exit dashboardManager processEvent : " + new java.util.Date());
		return dashboard;

	}

	@Async
	public Future<SLAMonitor> monitorSLA(ExpectedDatafeed expectedDatafeed,
			ElasticSearchManager<Dashboard> dashboardService, String dashboardID) {
		System.out.println("Adding SLA for:" + expectedDatafeed.getFilePattern());
		SLAMonitor slaMonitor = new SLAMonitor(jmsTemplate, expectedDatafeed, dashboardService, dashboardID);
		slaMonitor.start();
		return new AsyncResult<>(slaMonitor);
	}

}
