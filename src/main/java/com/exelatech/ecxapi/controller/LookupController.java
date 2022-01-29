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

import com.exelatech.ecxapi.mapper.LookupConfigMapper;
import com.exelatech.ecxapi.model.Lookups;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;
@Slf4j	
@RestController
@RequestMapping("/lookups")
public class LookupController {

	@Autowired
	private LookupConfigMapper lookupConfigMapper;

	@PreAuthorize("hasAnyAuthority('_administration:setting:view','_administration:setting:manage')")	
	@GetMapping
	public List<Lookups> getAll() throws NotFoundException {
		List<Lookups> Lookups = null;
		try {
			Lookups = lookupConfigMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return Lookups;
	}

	@PreAuthorize("hasAnyAuthority('_administration:setting:view','_administration:setting:manage')")
	@GetMapping("/{type}/{subtype}/{name}")
	public Lookups getValues(@PathVariable("type") String type,@PathVariable("subtype") String subtype,
			@PathVariable("name") String name) throws NotFoundException {
		Lookups Lookups = null;
		try {
			Lookups = lookupConfigMapper.getValue(type,subtype,name);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return Lookups;
	}

	@PreAuthorize("hasAnyAuthority('_administration:setting:view','_administration:setting:manage')")
	@GetMapping("/{type}")
	public List<Lookups> getAllLookupsByType(@PathVariable String type) throws NotFoundException {
		List<Lookups> Lookups = null;
		try {
			Lookups = lookupConfigMapper.getAllLookupsByType(type);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return Lookups;
	}

	@PreAuthorize("hasAnyAuthority('_administration:setting:add','_administration:setting:manage')")
	@PostMapping
	public ResponseEntity<String> addLookups(@RequestBody Lookups lookups) throws NotFoundException {

		try{
			lookupConfigMapper.insert(lookups);
		}catch ( DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("Type-Subtype-Name"+Constants.ALREDY_EXIST);
		}catch ( Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}
	/*
	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:manage','_remit:*:manage')")
	@PutMapping
	public ResponseEntity<String> updateLookups(@RequestBody Lookups lookups) throws NotFoundException {
		int count = lookupConfigMapper.update(lookups);

		if (count != 0) {
			return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
		} else {
			throw new NotFoundException(Constants.NOT_UPDATE);
		}

	}
	 */
	@PreAuthorize("hasAnyAuthority('_administration:setting:edit','_administration:setting:manage')")
	@PutMapping
	public ResponseEntity<String> updateLookupsDetails(@RequestBody Lookups lookups) throws NotFoundException {
		try {
			int count = lookupConfigMapper.updateUsingSubType(lookups);

			if (count != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("Type-Subtype-Name"+Constants.NOT_INSERT);
			}
		}catch ( DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("Type-Subtype-Name"+ Constants.NOT_EXIST);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_administration:setting:delete','_administration:setting:manage')")
	@DeleteMapping("/{type}/{subtype}/{name}")
	public ResponseEntity<String> deleteLookupsUsingSubTypes(@PathVariable("type") String type,@PathVariable("subtype") String subtype,
			@PathVariable("name") String name) throws NotFoundException {
		try {
			int count = lookupConfigMapper.deleteUsingSubType(type,subtype,name);
			if (count != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("Type-Subtype-Name"+ Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}
	/*	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:manage','_remit:*:manage')")
	@DeleteMapping("type/{type}")
	public ResponseEntity<String> deleteLookups(@PathVariable String type) throws NotFoundException {
		int count = lookupConfigMapper.remove(type);
		if (count != 0) {
			return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
		} else {
			return ResponseEntity.ok().body(Constants.NOT_UPDATE);
		}

	}*/
}
