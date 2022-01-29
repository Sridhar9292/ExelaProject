package com.exelatech.ecxapi.controller;

import static com.exelatech.ecxapi.util.Constants.FAILED_STATUS;
import static com.exelatech.ecxapi.util.Constants.SUCCESS_STATUS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.ACGMapper;
import com.exelatech.ecxapi.model.ACGDashboard;
import com.exelatech.ecxapi.model.ACGPrintRecord;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/acg")
public class ACGController {

	@Value("${spring.activemq.user}")
	private String activeMqUser;

	@Value("${spring.activemq.password}")
	private String activeMqPassword;

	@Value("${spring.activemq.broker-url}")
	private String activeMqUrl;
	
	@Value("${spring.activemq.event-topic}")
	private String eventTopic;
	@Autowired
	private ACGMapper acgMapper;

	@PreAuthorize("hasAnyAuthority('_print:acgDashboard:view','_print:acgDashboard:manage')")
	@Operation(summary = "Date format yyyyMMdd")
	@GetMapping("/search/{createdDate}")
	public List<ACGDashboard> getACGDashboardSearch(@PathVariable("createdDate") String createdDate)
			throws NotFoundException {
		List<ACGDashboard> acgDashBoard = null;
		if (!createdDate.equals("") && !createdDate.equals(null)) {
			try {
				SimpleDateFormat fromDateFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dt1;

				dt1 = fromDateFormat.parse(createdDate);
				String startDate = toDateFormat.format(dt1);

				acgDashBoard = acgMapper.getACGDashBoardInfo(startDate);

			} catch (final Exception e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				throw new NotFoundException(e.getMessage());
			}
		}
		return acgDashBoard;
	}

	@PreAuthorize("hasAnyAuthority('_print:acgDashboard:view','_print:acgDashboard:manage')")
	@GetMapping("/search/{createdDate}/{dropdownStatus}")
	public List<ACGDashboard> getACGDashboard(@PathVariable("createdDate") String createdDate,
			@PathVariable("dropdownStatus") String status) throws NotFoundException {
		List<ACGDashboard> acgDashBoard = null;
		System.out.println("date===>>" + createdDate);
		System.out.println("status===>>" + status);
		try {
			SimpleDateFormat fromDateFormat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dt1 = fromDateFormat.parse(createdDate);
			String startDate = toDateFormat.format(dt1);
			if (status.equals(FAILED_STATUS) && (!startDate.isEmpty() || !startDate.equals(null))) {
				acgDashBoard = acgMapper.getACGDashboardIncomplete(status);
				// System.out.println("List ==> "+acgDashBoard);
			} else if (status.equals(SUCCESS_STATUS) && (!startDate.isEmpty() || !startDate.equals(null))) {
				acgDashBoard = acgMapper.getACGDashboardSuccess(startDate, status);
				// System.out.println("List1 ==> "+acgDashBoard);
			} else if (status.equals("") && !startDate.equals("") || !startDate.equals(null)) {
				acgDashBoard = acgMapper.getACGDashBoardInfo(startDate);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return acgDashBoard;
	}

	@PreAuthorize("hasAnyAuthority('_print:acgDashboard:view','_print:acgDashboard:manage')")
	@GetMapping("{msgId}")
	public List<ACGPrintRecord> getACGImmediatePrintRecordInfo(@PathVariable String msgId) throws NotFoundException {
		List<ACGPrintRecord> imdPrintRec = new ArrayList<ACGPrintRecord>();
		try {
			System.out.println("MessageId msgId==> " + msgId);
			imdPrintRec = acgMapper.getACGImmediatePrintRecordInlineInfo(msgId);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return imdPrintRec;
	}

	@PreAuthorize("hasAnyAuthority('_print:acgDashboard:view','_print:acgDashboard:manage')")
	@GetMapping("/error-msg/{msgId}")
	public @ResponseBody List<ACGDashboard> getACGMessageError(@PathVariable String msgId) throws NotFoundException {
		List<ACGDashboard> acgdErrList = null;
		try {
			System.out.println("MessageId error-msg/ ==> " + msgId);
			acgdErrList = acgMapper.getACGMessageErr(msgId);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return acgdErrList;

	}

	@PreAuthorize("hasAnyAuthority('_print:acgDashboard:manage')")
	@PostMapping("/retry/{messageId}/{appId}")
	public ResponseEntity<String> generateRemitExport(@PathVariable("messageId") String messageId,
			@PathVariable("appId") String appID) throws NotFoundException {

		try {

			ConnectionFactory factory = new ActiveMQConnectionFactory(activeMqUser, activeMqPassword, activeMqUrl);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(eventTopic);
			// Added as a producer
			MessageProducer producer = session.createProducer(topic);
			TextMessage msg = session.createTextMessage();
			msg.setStringProperty("eventName", "retryFailedStatus");
			msg.setStringProperty("messageId", messageId);
			msg.setStringProperty("appID", appID);
			// msg.setStringProperty("reExportStatus", "ReExported");
			msg.setText("ACG Dashboard - Retry the Failed Transactions Event");
			producer.send(msg);
			System.out.println("ACG Dashboard - Retry the Failed Transactions Event");

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}

}
