package com.exelatech.ecxapi.controller;

import java.util.List;

import com.exelatech.ecxapi.mapper.ErrorEmailListMapper;
import com.exelatech.ecxapi.model.ErrorEmail;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j	
@RestController
@RequestMapping("/error-emails")
public class ErrorEmailListController  {

	@Autowired
	private ErrorEmailListMapper errorEmailListMapper;
	@PreAuthorize("hasAnyAuthority('_email:emailDashboard:view','_email:emailDashboard:manage')")
	@GetMapping
	public List<ErrorEmail> getErrorEmailList() throws NotFoundException {
		List<ErrorEmail> errorEmail=null;
		try {
			errorEmail=errorEmailListMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return errorEmail;
	} 
	@PreAuthorize("hasAnyAuthority('_email:emailDashboard:view','_email:emailDashboard:manage')")
	@GetMapping("{query}")
	public List<ErrorEmail> getErrorEmail(@PathVariable String query) throws NotFoundException {
		List<ErrorEmail> errorEmail=null;
		try {
			errorEmail=errorEmailListMapper.search("%" + query + "%");
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return errorEmail;
	}
	
	@PreAuthorize("hasAnyAuthority('_email:emailDashboard:add','_email:emailDashboard:manage')")
	@PutMapping
	public  ResponseEntity<String>  updateErrorEmail(ErrorEmail errorEmail) throws NotFoundException {
		int count=0;
		try {
			count = errorEmailListMapper.update(errorEmail);
			if (count != 0 ) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("EmailUUID"+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}


	/*
	@GetMapping({"{emailAddress}/{emailUUID}"}) 
	public ModelAndView handleErrorEmail(@PathVariable("emailAddress") String emailAddress,
			@PathVariable("emailUUID") String emailUUID) throws Exception {
		ModelAndView modelandview = new ModelAndView();
		ErrorEmail errorEmail = new ErrorEmail();
		if (emailAddress != null) {
			errorEmail.setEmailAddress(emailAddress);
			errorEmail.setEmailUUID(emailUUID);
			modelandview.addObject("errorEmail", errorEmail);
		} else {
			modelandview.addObject("errorEmail", errorEmail);
		}
		modelandview.setViewName("email/erroremailcorrection");
		return modelandview;

	}

	 */
}
