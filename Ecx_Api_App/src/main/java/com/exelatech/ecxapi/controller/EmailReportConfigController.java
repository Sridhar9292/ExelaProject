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

import com.exelatech.ecxapi.mapper.EmailReportConfigMapper;
import com.exelatech.ecxapi.model.EmailReportConfig;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/email-report-config")
public class EmailReportConfigController {
	@Autowired
	private EmailReportConfigMapper emailSubReportConfigMapper ;

	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:view','_email:subaccountDashboard:manage')")
	@GetMapping("/{subaccountCode}/{reportType}")
	protected EmailReportConfig showForm(@PathVariable("subaccountCode")String subaccountCode,@PathVariable("reportType")String reportType) throws NotFoundException{
		EmailReportConfig emailReportConfig = null;
		try {
		emailReportConfig = emailSubReportConfigMapper.get(subaccountCode, reportType);   
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return emailReportConfig;
	}

	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:view','_email:subaccountDashboard:manage')")
	@GetMapping("/{subaccountCode}")
	protected  List<EmailReportConfig> getAll(@PathVariable("subaccountCode")String subaccountCode) throws NotFoundException{
		List<EmailReportConfig> emailReportConfig = null;
		try {
		emailReportConfig = emailSubReportConfigMapper.getAll(subaccountCode);  
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return emailReportConfig;
	}

	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:add','_email:subaccountDashboard:manage')")
	@PostMapping
	public ResponseEntity<String> insert(@RequestBody EmailReportConfig emailReportConfig)throws NotFoundException {
		int count =0;

		try {  
			System.out.println("emailReportConfig==>>"+emailReportConfig.toString());
			System.out.println("getSubaccountCode==>>"+emailReportConfig.getSubaccountCode());
			count = emailSubReportConfigMapper.insert(emailReportConfig);

			if(count!=0) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException("SubaccountCode "+Constants.NOT_INSERT);
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
	public ResponseEntity<String>  emailReportUpdate(@RequestBody  EmailReportConfig emailReportConfig)throws NotFoundException {
		int count =0;

		try {
			count =  emailSubReportConfigMapper.update(emailReportConfig);

			if(count!=0) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException("SubaccountCode "+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}
	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:delete','_email:subaccountDashboard:manage')")
	@DeleteMapping
	public ResponseEntity<String>  emailReportdelete(@RequestBody  EmailReportConfig emailReportConfig)throws NotFoundException {
		int count =0;

		try {
			count =  emailSubReportConfigMapper.remove(emailReportConfig);

			if(count!=0) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException("SubaccountCode "+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}
}
