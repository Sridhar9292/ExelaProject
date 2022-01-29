package com.exelatech.ecxapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.EmailConfigMapper;
import com.exelatech.ecxapi.model.EmailConfig;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;
@Slf4j	
@RestController
@RequestMapping("/email-subaccounts")
public class EmailConfigController {
	
	@Autowired
	private EmailConfigMapper emailConfigMapper;

	//@Autowired
	//private EmailReportConfigMapper emailReportConfigMapper;
	 private String emailReportNamingPattern="[PREFIX].t2.email.[EMAIL_REPORT_TYPE].[SUBACCOUNT_CODE].[YYYYMMDDHHMMSS].json";
	    private String emailReportPath="/home2/ecx/endpoints/transcentra/ebox/t2/out/";
	    private boolean generateReportIfEmpty=true;


	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:view','_email:subaccountDashboard:manage')")
	@GetMapping	
	public List<EmailConfig> getEmailConfigList() throws NotFoundException {
		List<EmailConfig> emailConfig = null;
		try {
			emailConfig = emailConfigMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return emailConfig;
	}

	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:view','_email:subaccountDashboard:manage')")
	@GetMapping("/{subaccountCode}")
	public EmailConfig getEmailConfig(@PathVariable String subaccountCode) throws NotFoundException {
		EmailConfig emailConfig = null;
		try {
			emailConfig = emailConfigMapper.get(subaccountCode);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return emailConfig;
	}

	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:add','_email:subaccountDashboard:manage')")
	@PostMapping
	public ResponseEntity<String> addEmailConfig(@RequestBody EmailConfig emailConfig) throws NotFoundException {
		int count1 = 0;
		try {
			count1= emailConfigMapper.insert(emailConfig);
			if (emailConfig.getCategoryList().size() > 0) {
				emailConfigMapper.insertCategoriesForSubaccount(emailConfig);
			}
			if (emailConfig.isGenerateDefaultReports()) {
				
				emailConfigMapper.reportInsert(emailConfig.getSubaccountCode(),
						 "BOUNCES",
						 emailReportNamingPattern,
						 emailReportPath, "1","1");
				emailConfigMapper.reportInsert(emailConfig.getSubaccountCode(),
						 "CLICKS",
						 emailReportNamingPattern,
						 emailReportPath, "1","0");
				emailConfigMapper.reportInsert(emailConfig.getSubaccountCode(),
						 "COMPLAINTS",
						 emailReportNamingPattern,
						 emailReportPath, "1","0");
				emailConfigMapper.reportInsert(emailConfig.getSubaccountCode(),
						 "DELIVERED",
						 emailReportNamingPattern,
						 emailReportPath, "1","1");
				emailConfigMapper.reportInsert(emailConfig.getSubaccountCode(),
						 "ISSUES",
						 emailReportNamingPattern,
						 emailReportPath, "1","0");
				emailConfigMapper.reportInsert(emailConfig.getSubaccountCode(),
						 "OPENS",
						 emailReportNamingPattern,
						 emailReportPath, "1","1");
				emailConfigMapper.reportInsert(emailConfig.getSubaccountCode(),
						 "SENT",
						 emailReportNamingPattern,
						 emailReportPath, "1","0");

	/*	EmailReportConfig emailReportConfigBounces = new EmailReportConfig();
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
				emailReportConfigMapper.insert(emailReportConfigSent);*/
			}

			if (count1 != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException(Constants.NOT_INSERT);
			}
		}catch ( DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("SubaccountCode "+Constants.ALREDY_EXIST);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}

	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:edit','_email:subaccountDashboard:manage')")
	@PutMapping
	public ResponseEntity<String> updateEmailConfig(@RequestBody EmailConfig emailConfig) throws NotFoundException {
		int count1 = 0;
		try {
			count1= emailConfigMapper.update(emailConfig);
			emailConfigMapper.deleteCategoriesForSubaccount(emailConfig.getSubaccountCode());
			if (emailConfig.getCategoryList().size() > 0) {
				emailConfigMapper.insertCategoriesForSubaccount(emailConfig);
			}
			if (count1 != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("SubaccountCode "+ Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}

	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:delete','_email:subaccountDashboard:manage')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSubAccounts(@PathVariable String id) throws NotFoundException {
		int count1 = 0;
		try {
			emailConfigMapper.deleteBySubaccountCode(id);
			emailConfigMapper.deleteCategoriesForSubaccount(id);
		
			 count1 =emailConfigMapper.remove(id);
		
		//	emailReportConfigMapper.removeBySubaccountCode(id);
			if (count1 != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("SubaccountCode "+Constants.NOT_EXIST);

			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}
}


