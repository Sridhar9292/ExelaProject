package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.EmailTemplateMapper;
import com.exelatech.ecxapi.model.EmailTemplate;
import com.exelatech.ecxapi.util.NotFoundException;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailTemplateControllerTest  {
	@Autowired
	private EmailTemplateMapper emailTemplateMapper;

	@Test
	@Order(1) 
	public void emailTemplate()  {		
		List<EmailTemplate> emailTemplate=null;
		emailTemplate=emailTemplateMapper.getAll();
		assertNotNull(emailTemplate);
	}



	@Test
	@Order(2)
	public void addEmailTemplate() throws NotFoundException {
		int count=0;
		EmailTemplate emailTemplate =new EmailTemplate();
		emailTemplate.setTemplateName("Templete_test_Api2");
		emailTemplate.setTemplateId("19902");
		emailTemplate.setEventName("Eventname_test_api2");
		emailTemplate.setEmailFrom("eamilapitest@exelaonline.com");
		emailTemplate.setEmailTo("eamilapitest2@exelaonline.com");
		emailTemplate.setEmailCc("eamilapitestCC@exelaonline.com");
		emailTemplate.setEmailCc("");
		emailTemplate.setEmailSubject("Test");
		emailTemplate.setEmailBody("Test_body");
		emailTemplate.setFileAsBody(false);
		emailTemplate.setEnabled(false);
		count=emailTemplateMapper.insert(emailTemplate);
		if(count!=0) {
			assert(true);
		}else {
			assert(false);
		}
	}

	@Test
	@Order(3) 
	public void updateEmailTemplate() {		
		int count=0;
		EmailTemplate emailTemplate =new EmailTemplate();
		emailTemplate.setTemplateName("Templete_test_Api1");
		emailTemplate.setTemplateId("19992");
		emailTemplate.setEventName("Eventname_test_api2");
		emailTemplate.setEmailFrom("eamilapitest@exelaonline.com");
		emailTemplate.setEmailTo("eamilapitest2@exelaonline.com");
		emailTemplate.setEmailCc("eamilapitestCC@exelaonline.com");
		emailTemplate.setEmailSubject("TestUPDATE");
		emailTemplate.setEmailBody("TestUPDATE");
		emailTemplate.setFileAsBody(false);
		emailTemplate.setEnabled(false);
		count=emailTemplateMapper.update(emailTemplate);
		if(count!=0) {
			assert(true);
		}else {
			assert(false);
		}
	}
	@Test
	@Order(4) 
	public void getEmailTemplateByEventName() {		
		List<EmailTemplate> emailTemplate=null;
		String id="Eventname_test_api2";
		emailTemplate=emailTemplateMapper.getEmailTemplateByEventName(id);
		assertNotNull(emailTemplate);
	}

	@Test
	@Order(5) 
	public void searchEmailTemplate() throws NotFoundException {
		List<EmailTemplate> emailTemplate=null;
		String id="Eventname_test_api2";
		emailTemplate=emailTemplateMapper.search("%" + id + "%"); 
		assertNotNull(emailTemplate);
	}

	@Test
	@Order(6) 
	public void deleteEmailTemplate( ){	
		int count=0;
		String id="19902";
		count=emailTemplateMapper.remove(id);
		if(count!=0) {
			assert(true);
		}else {
			assert(false);
		}
	}

}
