
package com.exelatech.ecxapi.controller;

import java.util.List;

import com.exelatech.ecxapi.mapper.ExpectedDatafeedMapper;
import com.exelatech.ecxapi.model.ExpectedDatafeed;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

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


@Slf4j	
@RestController
@RequestMapping("/expected-data-feed")
public class ExpectedDatafeedController  {
	@Autowired
	private ExpectedDatafeedMapper expectedDatafeedMapper;

	@PreAuthorize("hasAnyAuthority('_administration:file:manage','_administration:file:view')")
	@GetMapping
	public List<ExpectedDatafeed> expectedDatafeed() throws NotFoundException {		
		List<ExpectedDatafeed> ExpectedDatafeedList=null;
		try {
			ExpectedDatafeedList=expectedDatafeedMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return ExpectedDatafeedList;
	}

	@PreAuthorize("hasAnyAuthority('_administration:file:manage','_administration:file:view')")
	@GetMapping("/{id}")
	public ExpectedDatafeed getExpectedDatafeed(@PathVariable String id) throws NotFoundException {		
		ExpectedDatafeed expectedDatafeed=null;
		try {
			expectedDatafeed= expectedDatafeedMapper.get(id);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return expectedDatafeed;
	}
	@PreAuthorize("hasAnyAuthority('_administration:file:manage','_administration:file:view')")
	@GetMapping("search/{id}")
	public List<ExpectedDatafeed> searchExpectedDatafeed(@PathVariable String id) throws NotFoundException {		
		List<ExpectedDatafeed> ExpectedDatafeedList=null;
		try {
			ExpectedDatafeedList= expectedDatafeedMapper.search("%" + id + "%"); 
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
		return ExpectedDatafeedList;
	}

	@PreAuthorize("hasAnyAuthority('_administration:file:manage','_administration:file:edit')")
	@PutMapping
	public ResponseEntity<String> updateExpectedDatafeed(@RequestBody  ExpectedDatafeed expectedDatafeed) throws NotFoundException {		
		int count=0;
		try {
			count=expectedDatafeedMapper.update(expectedDatafeed);
			if(count!=0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException("FeedID"+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}
	@PreAuthorize("hasAnyAuthority('_administration:file:manage','_administration:file:add')")
	@PostMapping
	public ResponseEntity<String> addExpectedDatafeed(@RequestBody ExpectedDatafeed expectedDatafeed) throws NotFoundException {
		int count=0;
		try {
			count=expectedDatafeedMapper.insert(expectedDatafeed);
			if(count!=0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException(Constants.NOT_INSERT);
			}
		}catch ( DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("FeedID "+Constants.ALREDY_EXIST);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}

	@PreAuthorize("hasAnyAuthority('_administration:file:manage','_administration:file:delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteExpectedDatafeed(@PathVariable String id ) throws NotFoundException {	
		int count=0;
		try {
			count=expectedDatafeedMapper.remove(Long.valueOf(id));
			if(count!=0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				return ResponseEntity.ok().body("FeedID "+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}	
	}

}
