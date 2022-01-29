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

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j	
@RestController
@RequestMapping("/monthly-sla-reports")
public class MonthlySLAReportsController {
	static int BUFFER_SIZE = 4096;
	// static String BASE_REPORT_PATH = "/data/ecx/datafeed/email/.archive";
	// static final String BASE_REPORT_PATH = "${reportPath.monthlySLA}";
	// //"E:\\others\\TEST\\in\\report\\";
	@Value("${reportPath.monthlySLA:}")
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
	
	@PreAuthorize("hasAnyAuthority('_reports:monthlyReport:manage','_reports:monthlyReport:view')")
	@GetMapping
	public List<SummaryReport> monthlyReport() throws NotFoundException {
		List<SummaryReport> summaryFiles = new ArrayList<>();
		try {
			SummaryReport summaryReport = null;
			File dir = new File(BASE_REPORT_PATH);
			  FileFilter fileFilter = new RegexFileFilter("^([a-zA-Z]{16,16}\\d{8}.txt)$");
		        File[] files = dir.listFiles(fileFilter);
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			for (File file : files) {
				// System.out.println("File nmae ====>>>"+file.getName());
				summaryReport = new SummaryReport();
				summaryReport.setFileName(file.getName());
				summaryReport.setFilePath(file.getPath());
				// summaryReport.setLastModified(new SimpleDateFormat("MM/dd/yyyy
				// HH:mm:ss").format(file.lastModified()));
				summaryReport.setLastModified(new Date(file.lastModified()));
				summaryFiles.add(summaryReport);

			}
			summaryFiles.sort((l1, l2) -> l2.getLastModified().compareTo(l1.getLastModified()));
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return summaryFiles;
	}
	
	@PreAuthorize("hasAnyAuthority('_reports:monthlyReport:manage','_reports:monthlyReport:view')")
	@GetMapping("/{filename}")
	public ResponseEntity<?> monthlyReportDownload(@PathVariable("filename") String filename) throws NotFoundException {
		try {
			File downloadFile = new File(BASE_REPORT_PATH + "/" + filename);
			FileInputStream inputStream = new FileInputStream(downloadFile);
			InputStreamResource resource = new InputStreamResource(inputStream);
			return ResponseEntity.ok().header("Content-Disposition", "attachment;filename=" + filename)
					.contentLength(downloadFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(resource);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}
	@PreAuthorize("hasAnyAuthority('_reports:monthlyReport:generateReport','_reports:monthlyReport:manage')")
	@Operation(summary= "generated MonthlyReportExport date format yyyy-MM-dd")
	@GetMapping("/{boundmsg}/{fromDate}/{toDate}")
	public  ResponseEntity<?>  generateMonthlyReport(@PathVariable("boundmsg") String boundmsg,@PathVariable("fromDate") String fromDate,@PathVariable("toDate") String toDate)
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
		
			  TextMessage acgMsg = session.createTextMessage();
	            acgMsg.setStringProperty("eventName","MonthlyReportExport");
	            //acgMsg.setStringProperty("fileName",BASE_REPORT_PATH+"/"+ getText("prefix",locale) +".report");
	            //eboxMsg.setStringProperty("monthYear", statisticsMonthYear);
	            acgMsg.setText("ACG Invalid Messsage Report");
	            acgMsg.setStringProperty("msgBound", boundmsg);
	            acgMsg.setStringProperty("startDate", LocalDate.parse(fromDate).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	            acgMsg.setStringProperty("endDate", LocalDate.parse(toDate).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	            producer.send(acgMsg);
			Thread.sleep(3000);
			session.close();
		} catch (final Exception e) {
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}
}
