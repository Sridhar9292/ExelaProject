package com.exelatech.ecxapi;

import static com.exelatech.ecxapi.util.Constants.FAILED_STATUS;
import static com.exelatech.ecxapi.util.Constants.SUCCESS_STATUS;
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

import com.exelatech.ecxapi.mapper.ACGMapper;
import com.exelatech.ecxapi.model.ACGDashboard;
import com.exelatech.ecxapi.model.ACGPrintRecord;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ACGControllerTest {
	@Autowired
	private ACGMapper acgMapper;

	@Test	
	@Order(1)
	public void getACGDashboardSearch(){
		
		String createdDate="20201223";
		
		List<ACGDashboard> acgDashBoard = null;
		if (!createdDate.equals("") && !createdDate.equals(null)) {
			try {
				SimpleDateFormat fromDateFormat = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dt1;

				dt1 = fromDateFormat.parse(createdDate);
				String startDate = toDateFormat.format(dt1);

				acgDashBoard = acgMapper.getACGDashBoardInfo(startDate);
				assertNotNull(acgDashBoard);
			} catch (ParseException e) {
				e.printStackTrace();
				assert(false);
			}
		}
	}
	@Test	
	@Order(2)
	public void getACGDashboard()  {
		
		String createdDate="20201223";
		String status="Received";
		
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
			} else if (status.equals(SUCCESS_STATUS) && (!startDate.isEmpty() || !startDate.equals(null))) {
				acgDashBoard = acgMapper.getACGDashboardSuccess(startDate, status);
			} else if (status.equals("") && !startDate.equals("") || !startDate.equals(null)) {
				acgDashBoard = acgMapper.getACGDashBoardInfo(startDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		
			assert(false);
		}
		assertNotNull(acgDashBoard);
	}

	@Test	
	@Order(3)
	public void getACGImmediatePrintRecordInfo( )  {
		String msgId="57501e40-7338-4571-b744-e9e2150853b9";
		
		List<ACGPrintRecord> imdPrintRec = new ArrayList<ACGPrintRecord>();
		try {
			System.out.println("MessageId msgId==> " + msgId);
			imdPrintRec = acgMapper.getACGImmediatePrintRecordInlineInfo(msgId);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(imdPrintRec);
	}

	@Test	
	@Order(4)
	public void getACGMessageError()  {
		String msgId="57501e40-7338-4571-b744-e9e2150853b9";
		List<ACGDashboard> acgdErrList = null;
		try {
			System.out.println("MessageId error-msg/ ==> " + msgId);
			acgdErrList = acgMapper.getACGMessageErr(msgId);
		} catch (Exception e) {
			e.printStackTrace();
			assert(false);
		}
		
		assertNotNull(acgdErrList);
			
		

	}

}
