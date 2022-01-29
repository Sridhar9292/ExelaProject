package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.EmailJobDetailMapper;
import com.exelatech.ecxapi.mapper.EmailJobMapper;
import com.exelatech.ecxapi.model.EmailJob;
import com.exelatech.ecxapi.model.EmailJobDetail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailDashboardControllerTest {

	@Autowired
	private EmailJobMapper emailJobMapper;

	@Autowired
	private EmailJobDetailMapper emailJobDetailMapper;


	@Test	
	@Order(1)  
	public void getEmailJobs()	{
		String query="ECXJOB1";
		List<EmailJob> emailJobs = null;
		try {
			if (query == null || query.equals("")) {
				emailJobs = emailJobMapper.getAll();
			} else {
				emailJobs = emailJobMapper.search("%" + query + "%");
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(emailJobs);
	}
	@Test	
	@Order(2)  
	public void searchEmailJobs()	{
		String query="ECXJOB1";
		List<EmailJob> emailJobs = null;
		try {
			if (query == null || query.equals("")) {
				emailJobs = emailJobMapper.getAll();
			} else {
				emailJobs = emailJobMapper.search("%" + query + "%");
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(emailJobs);
	}
	@Test	
	@Order(3) 
	public void getEmailJobDetails ()  {
		String id="ECXJOB1";
		List<EmailJobDetail> emailJobDetails = null;
		try {
			emailJobDetails = emailJobDetailMapper.getJobDetails(id);
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(emailJobDetails); 
	}

}
