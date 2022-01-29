
package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.ExpectedDatafeedMapper;
import com.exelatech.ecxapi.model.ExpectedDatafeed;
import com.exelatech.ecxapi.util.NotFoundException;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpectedDatafeedControllerTest  {
	@Autowired
	private ExpectedDatafeedMapper expectedDatafeedMapper;

	@Test	
	@Order(1)   
	public void  expectedDatafeed()  {		
		List<ExpectedDatafeed> ExpectedDatafeedList=null;
		try {
			ExpectedDatafeedList=expectedDatafeedMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(ExpectedDatafeedList); 
	}
	@Test	
	@Order(2) 
	public void  addExpectedDatafeed() throws NotFoundException {
		
		ExpectedDatafeed expectedDatafeed =new ExpectedDatafeed ();
		expectedDatafeed.setFeedID(Long.valueOf("1768"));
		expectedDatafeed.setFeedName("Remit123");
		expectedDatafeed.setVendorCode("RPPS");
		expectedDatafeed.setFilePattern("*.fis.remit.*");
		expectedDatafeed.setFileType("Remit");
		expectedDatafeed.setExpectedTime("10:15");
		expectedDatafeed.setFileSchedule("0 0 20 ? * MON-FRI");
		expectedDatafeed.setSingleMulti("S");
		expectedDatafeed.setGracePeriod(10);
		
		
		int count=0;
		try {
			count=expectedDatafeedMapper.insert(expectedDatafeed);
			if(count!=0) {
				assert(true);
			}else {
				assert(false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}
	@Test	
	@Order(3)   	
	public void getExpectedDatafeed() {		
		String id="1768";
		ExpectedDatafeed expectedDatafeed=null;
		try {
			expectedDatafeed= expectedDatafeedMapper.get(id);
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(expectedDatafeed); 
	}
	@Test	
	@Order(4)   	
	public void searchExpectedDatafeed(){		
		 String id="1768";
		List<ExpectedDatafeed> ExpectedDatafeedList=null;
		try {
			ExpectedDatafeedList= expectedDatafeedMapper.search("%" + id + "%"); 
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(ExpectedDatafeedList);
	}

	@Test	
	@Order(5)   	
	public void updateExpectedDatafeed() throws NotFoundException {	
		ExpectedDatafeed expectedDatafeed =new ExpectedDatafeed ();
		expectedDatafeed.setFeedID(Long.valueOf("1768"));
		expectedDatafeed.setFeedName("Remit123");
		expectedDatafeed.setVendorCode("RPPS");
		expectedDatafeed.setFilePattern("*.fis.remit.*");
		expectedDatafeed.setFileType("Remitupadate");
		expectedDatafeed.setExpectedTime("10:15");
		expectedDatafeed.setFileSchedule("0 0 20 ? * MON-FRI");
		expectedDatafeed.setSingleMulti("S");
		expectedDatafeed.setGracePeriod(10);
		int count=0;
		try {
			count=expectedDatafeedMapper.update(expectedDatafeed);
			if(count!=0) {
				assert(true);
			}else {
				assert(false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}
	
	@Test	
	@Order(6)  
	public void deleteExpectedDatafeed() throws NotFoundException {	
		String id="1768";
		int count=0;
		try {
			count=expectedDatafeedMapper.remove(Long.valueOf(id));
			if(count!=0) {
				assert(true);
			}else {
				assert(false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}

}
