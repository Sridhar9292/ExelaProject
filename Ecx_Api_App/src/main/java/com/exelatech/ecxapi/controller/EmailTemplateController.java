package com.exelatech.ecxapi.controller;

import java.util.List;

import com.exelatech.ecxapi.mapper.EmailTemplateMapper;
import com.exelatech.ecxapi.model.EmailTemplate;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

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

import lombok.extern.slf4j.Slf4j;


@Slf4j	
@RestController
@RequestMapping("/email-templates")
public class EmailTemplateController  {
	@Autowired
	private EmailTemplateMapper emailTemplateMapper;
	
	@PreAuthorize("hasAnyAuthority('_administration:template:view','_administration:template:manage')")
	@GetMapping
	public List<EmailTemplate> emailTemplate() throws NotFoundException {		
		List<EmailTemplate> emailTemplate=null;
		try {
			emailTemplate=emailTemplateMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return emailTemplate;
	}
	@PreAuthorize("hasAnyAuthority('_administration:template:view','_administration:template:manage')")
	@GetMapping("/{id}")
	public List<EmailTemplate> getEmailTemplateByEventName(@PathVariable String id) throws NotFoundException {		
		List<EmailTemplate> emailTemplate=null;
		try {
			emailTemplate=emailTemplateMapper.getEmailTemplateByEventName(id);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return emailTemplate; 
	}
/*	@GetMapping("{condtion}/{id}")
	public List<EmailTemplate> serachEmailTemplate(@PathVariable String id) throws NotFoundException {
		List<EmailTemplate> emailTemplate=null;
		try {
			emailTemplate=emailTemplateMapper.getEmailTemplateByTemplateId(id);
		} catch (final Exception e) {
			throw new NotFoundException(e.getMessage());
		}
		return emailTemplate;
	}
*/
	@PreAuthorize("hasAnyAuthority('_administration:template:view','_administration:template:manage')")
	@GetMapping("search/{id}")
	public List<EmailTemplate> searchEmailTemplate(@PathVariable String id) throws NotFoundException {
		List<EmailTemplate> emailTemplate=null;
		try {
			emailTemplate=emailTemplateMapper.search("%" + id + "%"); 
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return emailTemplate;
	}

	@PreAuthorize("hasAnyAuthority('_administration:template:edit','_administration:template:manage')")
	@PutMapping
	public ResponseEntity<String> updateEmailTemplate(@RequestBody  EmailTemplate emailTemplate) throws NotFoundException {		
		int count =0;
		try {
			count=emailTemplateMapper.update(emailTemplate);
			if (count != 0 ) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("TemplateId"+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}
	@PreAuthorize("hasAnyAuthority('_administration:template:add','_administration:template:manage')")
	@PostMapping
	public ResponseEntity<String> addEmailTemplate(@RequestBody EmailTemplate emailTemplate) throws NotFoundException {
		int i=0;
		try {
			i=emailTemplateMapper.insert(emailTemplate);
			if(i!=0) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				return ResponseEntity.ok().body(emailTemplate.getTemplateId()+"--"+Constants.NOT_INSERT);
			}
		}catch ( DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("TemplateId"+Constants.ALREDY_EXIST);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}
	@PreAuthorize("hasAnyAuthority('_administration:template:delete','_administration:template:manage')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmailTemplate(@PathVariable String id ) throws NotFoundException {	
		int i=0;
		try {
			i=emailTemplateMapper.remove(id);
			if(i!=0) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException("TemplateId"+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}

}
