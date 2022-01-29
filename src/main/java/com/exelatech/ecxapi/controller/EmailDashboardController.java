package com.exelatech.ecxapi.controller;

import java.util.List;

import com.exelatech.ecxapi.mapper.EmailJobDetailMapper;
import com.exelatech.ecxapi.mapper.EmailJobMapper;
import com.exelatech.ecxapi.model.EmailJob;
import com.exelatech.ecxapi.model.EmailJobDetail;
import com.exelatech.ecxapi.util.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@Slf4j	
@RestController
@RequestMapping("/email-dashboard")
public class EmailDashboardController {

	@Autowired
	private EmailJobMapper emailJobMapper;

	@Autowired
	private EmailJobDetailMapper emailJobDetailMapper;

	@PreAuthorize("hasAnyAuthority('_email:emailDashboard:view','_email:emailDashboard:manage')")
	@GetMapping
	public List<EmailJob> getEmailJobs()
			throws NotFoundException {
		List<EmailJob> emailJobs = null;
		try {
			emailJobs = emailJobMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return emailJobs;
	}

/*
	@PreAuthorize("hasAnyAuthority('_email:subaccountDashboard:manage','_email:subaccountDashboard:view','_email:*:manage','_email:*:view')")
	@GetMapping("/search/{searchTerm}")
	public List<EmailJob> getEmailJobs(@PathVariable  String searchTerm)
			throws NotFoundException {

		List<EmailJob> emailJobs = null;
		try {
			if (searchTerm == null || searchTerm.equals("")) {
				emailJobs = emailJobMapper.getAll();
			} else {
				emailJobs = emailJobMapper.search("%" + searchTerm + "%");
			}
		} catch (final Exception e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		}
		return emailJobs;
	}
*/
	@PreAuthorize("hasAnyAuthority('_email:emailDashboard:view','_email:emailDashboard:manage')")
	@GetMapping("{fedDocID}")
	public List<EmailJobDetail> getEmailJobDetails(@PathVariable String fedDocID) throws NotFoundException {

		List<EmailJobDetail> emailJobDetails = null;
		try {
			emailJobDetails = emailJobDetailMapper.getJobDetails(fedDocID);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return emailJobDetails;
	}

}
