package com.exelatech.ecxapi.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import com.exelatech.ecxapi.model.SummaryReport;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.DateUtil;
import com.exelatech.ecxapi.util.NotFoundException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/summary-reports")
public class SummaryReportsController {
	static int BUFFER_SIZE = 4096;
	@Value("${reportPath.summary:}")
	private String BASE_REPORT_PATH;

	@Value("${spring.activemq.user}")
	private String activeMqUser;

	@Value("${spring.activemq.password}")
	private String activeMqPassword;

	@Value("${spring.activemq.broker-url}")
	private String activeMqUrl;

	@Value("${spring.activemq.event-topic}")
	private String eventTopic;

	@Value("${reportPath.prefix:}")
	private String prefix;

	@PreAuthorize("hasAnyAuthority('_reports:summaryReport:manage','_reports:summaryReport:view')")
	@Operation(summary= "Return All Summary Reports")
	@GetMapping
	public List<SummaryReport> summaryReport() throws NotFoundException {
		List<SummaryReport> summaryFiles = new ArrayList<>();
		try {

			System.out.println("File nmae BASE_REPORT_PATH ====>>>" + BASE_REPORT_PATH);
			SummaryReport summaryReport = null;
			File dir = new File(BASE_REPORT_PATH);
			FileFilter fileFilter = new RegexFileFilter("([^\\s]+(report\\.\\d{14}\\.xls)$)|((eBox Monthly Summary Report .*\\.xls)$)|((eMail Monthly Summary Report .*\\.xls)$)");
			File[] files = dir.listFiles(fileFilter);
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			for (File file : files) {
				summaryReport = new SummaryReport();
				summaryReport.setFileName(file.getName());
				summaryReport.setFilePath(file.getPath());
				// summaryReport.setLastModified(new SimpleDateFormat("MM/dd/yyyy
				// HH:mm:ss").format(file.lastModified()));
				summaryReport.setLastModified(new Date(file.lastModified()));
				summaryFiles.add(summaryReport);

			}
			summaryFiles.sort((l1, l2) -> l2.getLastModified().compareTo(l1.getLastModified()));
		} catch (Exception e) {
			log.error("SummaryReportsController summaryReport :", e);
			throw new NotFoundException(e.getMessage());
		}
		return summaryFiles;
	}

	@PreAuthorize("hasAnyAuthority('_reports:summaryReport:manage','_reports:summaryReport:view')")
	@Operation(summary= "Download Summary Reports")
	@GetMapping("/{filename}")
	public ResponseEntity<?> summaryReportDownload(@PathVariable("filename") String filename) throws NotFoundException {
		try {
			File downloadFile = new File(BASE_REPORT_PATH + "/" + filename);
			FileInputStream inputStream = new FileInputStream(downloadFile);
			InputStreamResource resource = new InputStreamResource(inputStream);
			return ResponseEntity.ok().header("Content-Disposition", "attachment;filename=" + filename)
					.contentLength(downloadFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(resource);
		} catch (Exception e) {
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}
	@PreAuthorize("hasAnyAuthority('_reports:summaryReport:manage','_reports:summaryReport:generateReport')")
	@Operation(summary= "generated Summary Reports date format yyyy-MM-dd")
	@GetMapping("/{fromDate}/{toDate}")
	public  ResponseEntity<?>  generateSummaryReport(@PathVariable("fromDate") String fromDate,@PathVariable("toDate") String toDate)
			throws NotFoundException {

		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(activeMqUser,activeMqPassword, activeMqUrl);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(eventTopic);

			// Generates statistics month Year as 1st day of next month in the format MM-dd-yyyy i.e. 12-01-2015 when run on any date in Nov.
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DATE, 1);
			String statisticsMonthYear = DateUtil.getDateTime("MM-dd-yyyy", cal.getTime());
			//Added as a producer
			MessageProducer producer = session.createProducer(topic);           
			// Create and send the message
			TextMessage eboxMsg = session.createTextMessage();
			eboxMsg.setStringProperty("eventName","ECXeboxStatisticsReportExport");
			eboxMsg.setStringProperty("fileName",BASE_REPORT_PATH+"/"+ prefix +".report");
			eboxMsg.setStringProperty("monthYear", statisticsMonthYear);
			eboxMsg.setText("ECX Console - Statistics Summary Report Event");
			eboxMsg.setStringProperty("startDate", LocalDate.parse(fromDate).format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
			eboxMsg.setStringProperty("endDate", LocalDate.parse(toDate).format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));

			TextMessage emailMsg = session.createTextMessage();
			emailMsg.setStringProperty("eventName","ECXEmailStatisticsReportExport");
			emailMsg.setStringProperty("fileName",BASE_REPORT_PATH+"/"+ prefix +".report");
			emailMsg.setStringProperty("monthYear", statisticsMonthYear);
			emailMsg.setText("ECX Console - Statistics Summary Report Event");
			emailMsg.setStringProperty("startDate", LocalDate.parse(fromDate).format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
			emailMsg.setStringProperty("endDate", LocalDate.parse(toDate).format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
			producer.send(eboxMsg);	         
			Thread.sleep(3000);
			producer.send(emailMsg);
			Thread.sleep(3000);
			session.close();
		} catch (final Exception e) {
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}
}
