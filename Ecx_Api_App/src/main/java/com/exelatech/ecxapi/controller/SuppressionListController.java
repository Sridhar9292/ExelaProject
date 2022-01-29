package com.exelatech.ecxapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.SuppressionListMapper;
import com.exelatech.ecxapi.mapper.SuppressionManager;
import com.exelatech.ecxapi.model.SuppressionList;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@Tags(value = {@Tag(name = "suppression", description = "Enail Configeration")})
@RequestMapping("/suppression")
public class SuppressionListController  {
	@Value("${apikey.suppression:}")
	private String apikey;
	
	@Autowired
	private SuppressionListMapper suppressionListMapper;
	
	@Autowired
	private SuppressionManager suppressionManager;
	

	@PreAuthorize("hasAnyAuthority('_email:emailDashboard:view','_email:emailDashboard:manage')")
	@GetMapping("/{jobNumber}")
	public List<SuppressionList> handleRequest(@PathVariable("jobNumber") String jobNumber) throws Exception {
			List<SuppressionList> suppressionList =null;
		try {
			suppressionList=suppressionListMapper.getSuppressDetails(jobNumber);
			
		}catch (final Exception e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				throw new NotFoundException(e.getMessage());
			}
		return suppressionList;
	}


	@PreAuthorize("hasAnyAuthority('_email:emailDashboard:delete','_email:emailDashboard:manage')")
    @DeleteMapping
private ResponseEntity<String> suppressActivate(@RequestBody SuppressionList suppressionList) throws NotFoundException {
		int count=0;
    	try {
			
			String reason= suppressionListMapper.getSuppressReason(suppressionList.getEmailAddress());
			suppressionList.setReason(reason);
			count=suppressionManager.removeEmailAddress(suppressionList,apikey);
			if (count != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("subAccount"+Constants.NOT_DELETE);
			}
    	} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
    }
	
}