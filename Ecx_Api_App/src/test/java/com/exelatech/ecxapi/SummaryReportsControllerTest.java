package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.model.SummaryReport;
import com.exelatech.ecxapi.util.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SummaryReportsControllerTest {
	static int BUFFER_SIZE = 4096;
	@Value("${reportPath.summary:}")
	private String BASE_REPORT_PATH;

	@Test
	@Order(1)  	
public void summaryReport()  {
		List<SummaryReport> summaryFiles = new ArrayList<>();
		try {
			SummaryReport summaryReport = null;
			File dir = new File(BASE_REPORT_PATH);
			FileFilter fileFilter = new RegexFileFilter("([^\\s]+(report\\.\\d{14}\\.xls)$)");
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
			assert(false);
		}
		assertNotNull(summaryFiles);
	}

	@Test
	@Order(2)  		
	public void summaryReportDownload() {
		 String filename="dev.report.20200902030018.xls";
		try {
			File downloadFile = new File(BASE_REPORT_PATH + "/" + filename);
			FileInputStream inputStream = new FileInputStream(downloadFile);
			InputStreamResource resource = new InputStreamResource(inputStream);
			assertNotNull(resource);
		} catch (Exception e) {
			assert(false);
		}
	}

}
