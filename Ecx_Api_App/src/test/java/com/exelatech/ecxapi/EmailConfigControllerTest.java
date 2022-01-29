package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.EmailConfigMapper;
import com.exelatech.ecxapi.mapper.EmailReportConfigMapper;
import com.exelatech.ecxapi.model.EmailConfig;
import com.exelatech.ecxapi.model.EmailReportConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailConfigControllerTest {
	@Autowired
	private EmailConfigMapper emailConfigMapper;

	@Autowired
	private EmailReportConfigMapper emailReportConfigMapper;


	@Test	
	@Order(1)   
	public void  getEmailConfigList()  {
		List<EmailConfig> emailConfig = null;
		try {
			emailConfig = emailConfigMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(emailConfig); ;
	}


	@Test	
	@Order(1) 
	public void getEmailConfig(){
	   String id="";
		EmailConfig emailConfig = null;
		try {
			emailConfig = emailConfigMapper.get(id);
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
		assertNotNull(emailConfig);
	}


	@Test	
	@Order(1) 
	public void addEmailConfig() {
		
		EmailConfig emailConfig=new EmailConfig();
		int i = 0;
		try {
			i = emailConfigMapper.insert(emailConfig);
			if (emailConfig.getCategoryList().size() > 0) {
				emailConfigMapper.insertCategoriesForSubaccount(emailConfig);
			}

			if (emailConfig.isGenerateDefaultReports()) {
				EmailReportConfig emailReportConfigBounces = new EmailReportConfig();
				emailReportConfigBounces.setSubaccountCode(emailConfig.getSubaccountCode());
				emailReportConfigBounces.setEmailReportType("BOUNCES");
				emailReportConfigBounces.setExportEnabled(true);
			EmailReportConfig emailReportConfigClicks = new EmailReportConfig();
				emailReportConfigClicks.setSubaccountCode(emailConfig.getSubaccountCode());
				emailReportConfigClicks.setEmailReportType("CLICKS");
				emailReportConfigClicks.setExportEnabled(false);
			EmailReportConfig emailReportConfigComplaints = new EmailReportConfig();
				emailReportConfigClicks.setSubaccountCode(emailConfig.getSubaccountCode());
				emailReportConfigClicks.setEmailReportType("COMPLAINTS");
				emailReportConfigClicks.setExportEnabled(false);
			EmailReportConfig emailReportConfigDelivered = new EmailReportConfig();
				emailReportConfigClicks.setSubaccountCode(emailConfig.getSubaccountCode());
				emailReportConfigClicks.setEmailReportType("DELIVERED");
				emailReportConfigClicks.setExportEnabled(true);
			EmailReportConfig emailReportConfigIssues = new EmailReportConfig();
				emailReportConfigClicks.setSubaccountCode(emailConfig.getSubaccountCode());
				emailReportConfigClicks.setEmailReportType("ISSUES");
				emailReportConfigClicks.setExportEnabled(false);	
			EmailReportConfig emailReportConfigOpens = new EmailReportConfig();
				emailReportConfigClicks.setSubaccountCode(emailConfig.getSubaccountCode());
				emailReportConfigClicks.setEmailReportType("OPENS");
				emailReportConfigClicks.setExportEnabled(true);
			EmailReportConfig emailReportConfigSent = new EmailReportConfig();
				emailReportConfigClicks.setSubaccountCode(emailConfig.getSubaccountCode());
				emailReportConfigClicks.setEmailReportType("SENT");
				emailReportConfigClicks.setExportEnabled(false);
				emailReportConfigMapper.insert(emailReportConfigBounces);
				emailReportConfigMapper.insert(emailReportConfigClicks);
				emailReportConfigMapper.insert(emailReportConfigComplaints);
				emailReportConfigMapper.insert(emailReportConfigDelivered);
				emailReportConfigMapper.insert(emailReportConfigIssues);
				emailReportConfigMapper.insert(emailReportConfigOpens);
				emailReportConfigMapper.insert(emailReportConfigSent);
			}
			if (i != 0) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}

	@Test	
	@Order(1) 
	public void updateEmailConfig()  {
		 EmailConfig emailConfig=new EmailConfig();
		int i = 0;
		try {
			i = emailConfigMapper.update(emailConfig);
			emailConfigMapper.deleteCategoriesForSubaccount(emailConfig.getSubaccountCode());
			if (emailConfig.getCategoryList().size() > 0) {
				emailConfigMapper.insertCategoriesForSubaccount(emailConfig);
			}
			if (i != 0) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}

	@Test	
	@Order(1) 
	public void  deleteSubAccounts()  {
		String id="";
		int i = 0;
		try {

			i = emailReportConfigMapper.removeBySubaccountCode(id);
			emailConfigMapper.remove(id);
			emailConfigMapper.deleteCategoriesForSubaccount(id);

			if (i != 0) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			assert(false);
		}
	}

}
