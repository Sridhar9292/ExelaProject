package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.PrintHubJobHeaderMapper;
import com.exelatech.ecxapi.model.PrintHubDashboard;
import com.exelatech.ecxapi.model.PrintHubJobDetails;
import com.exelatech.ecxapi.model.PrintHubJobHeader;
import com.exelatech.ecxapi.model.PrintHubJobPayload;
import com.exelatech.ecxapi.util.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrintHubDashbordControllerTest {

	@Autowired
	private PrintHubJobHeaderMapper printhubJobHeaderMapper;

	@Test
	@Order(1)
	public void insertPrinthubHeader() throws NotFoundException {
		int count = 0;

	
		PrintHubJobHeader printHubJobHeader = new PrintHubJobHeader();

		printHubJobHeader.setAppCode("144");
		printHubJobHeader.setDeliveryTime(null);
		printHubJobHeader.setDestination("SLMR");
		printHubJobHeader.setElapsedTime("10");
		printHubJobHeader.setSolutionID("10");
		printHubJobHeader.setReceivedTime(null);
		printHubJobHeader.setStatus("in-progress");
		printHubJobHeader.setGroupID("1000044");
		printHubJobHeader.setJobNumber("1000044");
		// printHubJobHeader.setSummary(summary);
		System.out.println(printHubJobHeader.toString() + ">>printHubJobHeader");
		count = printhubJobHeaderMapper.jobHeaderInsert(printHubJobHeader);
		System.out.println(count + ">>>>>>>>>printHubJobHeader");
		if (count != 0) {
			assert (true);
		} else {
			assert (false);
		}

	}

	@Test
	@Order(2)
	public void insertPrintDetails() {
		int count = 0;
		int count1 = 0;

		PrintHubJobPayload printHubJobPayload = new PrintHubJobPayload();
		printHubJobPayload.setContID("1000044");
		printHubJobPayload.setInstanceID("100044");
		printHubJobPayload.setPayloadName("PROD.SHV004.AGYBDR.D.66311.S1ETM1C0001.PS");
		printHubJobPayload.setPayloadDate(null);
		printHubJobPayload.setPayloadSize("2874126");
		printHubJobPayload.setPayloadTgt("/cygdrive/c/ziptemp/printStub/data/solimar/ADF/");
		printHubJobPayload.setPayStatus("in-progress");

		PrintHubJobDetails printFeeds = new PrintHubJobDetails();
		printFeeds.setContID("1000044");
		printFeeds.setGroupID("1000044");
		printFeeds.setPipeLineID("PHP00010");
		printFeeds.setFileName("AGYBDR_A_G_001_008.zip");
		printFeeds.setFileDate(null);
		printFeeds.setFileSize("1624118");
		printFeeds.setSrcSiteID("TRY");
		printFeeds.setTgtSiteID("SLMR");
		printFeeds.setStartTime(null);
		printFeeds.setEndTime(null);
		printFeeds.setContStatus("in-progress");

		count = printhubJobHeaderMapper.jobDetailsInsert(printFeeds);

		count1 = printhubJobHeaderMapper.jobPayloadInsert(printHubJobPayload);
		if (count1 != 0 && count != 0) {
			assert (true);
		} else {
			assert (false);
		}
	}

	@Test

	@Order(3)
	public void listPrintHubDashboardDate() {
		int count = 0;
		List<PrintHubJobHeader> printHubJobHeader = new ArrayList<PrintHubJobHeader>();
		printHubJobHeader = printhubJobHeaderMapper.getDate(null);
		assertNotNull(printHubJobHeader);
	}

	@Test

	@Order(4)
	public void listPrintHubDashboard() {
		PrintHubDashboard printHubJobHeader = new PrintHubDashboard();
		String groupid = "1000028";
		printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getGroupid(groupid));
		assertNotNull(printHubJobHeader.getPrintFeeds());
	}

	@Test

	@Order(5)
	public void listPrintHubDashboardForLastMinsHours() {
		PrintHubDashboard printHubJobHeader = new PrintHubDashboard();
		String time = null;
		String from = null;
		String to = null;
		if (time == null && from == null && to == null) {
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getAll());
		}

		if (time != null) {
			System.out.println("came inside time");
			int timeInMin = 0;
			if (time.equals("3mins")) {
				timeInMin = 3;
			} else if (time.equals("3hours")) {
				timeInMin = 180;
			} else if (time.equals("3days")) {
				timeInMin = 4320;
			}
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getByMinHoursSec(Integer.toString(timeInMin)));
		} else if (from != null && to != null) {
			System.out.println("came inside from and to");
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getByFromToDate(from, to));
		} else {
			System.out.println("came inside from");
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getByFromDate(from));
		}

		assertNotNull(printHubJobHeader.getPrintFeeds());
	}

	@Test

	@Order(6)
	public void getPrintHubDashboardDetails() {
		PrintHubDashboard printHubJobHeader = new PrintHubDashboard();
		String solutionname = "10";
		String appcode = "";
		String jobnumber = "";
		String destination = "";
		String solutionid = "";
		String instanceid = "";

		if (solutionname != null) {
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlBySolutionName(solutionname));
		} else if (appcode != null) {
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlByAppCode(appcode));
		} else if (jobnumber != null) {
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlByJobNumber(jobnumber));
		} else if (destination != null) {
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlBydestination(destination));
		} else if (solutionid != null) {
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlBySolutionId(solutionid));
		} else if (instanceid != null) {
			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlByInstanceId(instanceid));
		}

		assertNotNull(printHubJobHeader.getPrintFeeds());
	}
	
	  
	  @Test
	  
	  @Order(7) public void UpdatePrintDetails() { 
		  int count = 0; int count1 = 0;
	  int count2 = 0;
	  
	  PrintHubJobHeader printHubJobHeader = new PrintHubJobHeader();
	  
	  printHubJobHeader.setAppCode("10"); printHubJobHeader.setDeliveryTime(null);
	  printHubJobHeader.setDestination("SLMR");
	  printHubJobHeader.setElapsedTime("10");
	  printHubJobHeader.setSolutionID("10");
	  printHubJobHeader.setReceivedTime(null);
	  printHubJobHeader.setStatus("in-progress");
	  printHubJobHeader.setGroupID("1000043");
	  printHubJobHeader.setJobNumber("1000043"); 
			  count = printhubJobHeaderMapper.jobHeaderUpdate(printHubJobHeader);
	  
	  PrintHubJobDetails printFeeds = new PrintHubJobDetails();
	  printFeeds.setContID("1000043"); printFeeds.setGroupID("1000043");
	  printFeeds.setPipeLineID("PHP00010");
	  printFeeds.setFileName("AGYBDR_A_G_001_008.zip");
	  printFeeds.setFileDate(null); printFeeds.setFileSize("1624118");
	  printFeeds.setSrcSiteID("TRY"); printFeeds.setTgtSiteID("SLMR"); //
	  printFeeds.setStartTime(null); //printFeeds.setEndTime(date);
	  printFeeds.setContStatus("in-progress"); PrintHubJobPayload
	  printHubJobPayload = new PrintHubJobPayload();
	  
	  PrintHubJobPayload printHubJobPayloadupdate = new PrintHubJobPayload();
		printHubJobPayloadupdate.setContID("1000043");
		printHubJobPayloadupdate.setInstanceID("100043");
		printHubJobPayloadupdate.setPayloadName("PROD.SHV004.AGYBDR.D.66311.S1ETM1C0001.PS");
		printHubJobPayloadupdate.setPayloadDate(null);
		printHubJobPayloadupdate.setPayloadSize("2874126");
		printHubJobPayloadupdate.setPayloadTgt("/cygdrive/c/ziptemp/printStub/data/solimar/ADF/");
		printHubJobPayloadupdate.setPayStatus("in-progress");
	   
	  count1 = printhubJobHeaderMapper.jobDetailsUpdate(printFeeds);
	  
	  count2 = printhubJobHeaderMapper.jobPayloadUpdate(printHubJobPayloadupdate);
	  System.out.println(count1+">>>>>count1");
	  System.out.println(count2+">>>>>count2");
	  System.out.println(count+">>>>>count");
		if (count1 != 0 && count2 != 0 && count != 0) {
			assert (true);
		} else {
			assert (false);
		}
	}
	 
}
