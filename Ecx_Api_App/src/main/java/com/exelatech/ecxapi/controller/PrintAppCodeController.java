package com.exelatech.ecxapi.controller;

import java.util.List;

import com.exelatech.ecxapi.mapper.PrintAppCodesMapper;
import com.exelatech.ecxapi.model.PrintAppCodes;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/print-app-codes")
public class PrintAppCodeController {
	@Autowired
	private PrintAppCodesMapper printAppCodesMapper;

	@GetMapping
	public List<PrintAppCodes> printAppCodes() throws NotFoundException {
		List<PrintAppCodes> printAppCodesList = null;
		try {
			printAppCodesList = printAppCodesMapper.getAllAppcode();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printAppCodesList;
	}

	@GetMapping("{appcodetype}")
	public List<PrintAppCodes> getprintAppcodeType(@PathVariable String appcodetype) throws NotFoundException {
		List<PrintAppCodes> PrintAppCodes = null;
		try {
			PrintAppCodes = printAppCodesMapper.getAll(appcodetype);
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
		return PrintAppCodes;
	}

	@GetMapping("{appcodetype}/{appCode}")
	public PrintAppCodes getprintAppCodes(@PathVariable("appcodetype") String appcodetype,
			@PathVariable("appCode") String appcode) throws NotFoundException {
		PrintAppCodes printAppCodes = null;
		try {
			printAppCodes = printAppCodesMapper.get(appcodetype, appcode);
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
		return printAppCodes;
	}

	@GetMapping("search/{appcodetype}/{searchappCode}")
	public List<PrintAppCodes> serachPrintAppCodes(@PathVariable("appcodetype") String appcodetype,
			@PathVariable("searchappCode") String appcode) throws NotFoundException {
		List<PrintAppCodes> printAppCodesList = null;
		try {
			printAppCodesList = printAppCodesMapper.search(appcodetype, "%" + appcode + "%");
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
		return printAppCodesList;
	}

	@PutMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> updateprintAppCodes(@RequestBody PrintAppCodes printAppCodes)
			throws NotFoundException {
		int count = 0;
		try {
			count= printAppCodesMapper.update(printAppCodes);

			if(count !=0 ) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				return ResponseEntity.ok().body(Constants.NOT_UPDATE);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PostMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> addprintAppCodes(@RequestBody PrintAppCodes printAppCodes) throws NotFoundException {
		int count = 0;
		try {
			count = printAppCodesMapper.insert(printAppCodes);
			if(count !=0 ) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				return ResponseEntity.ok().body(Constants.NOT_INSERT);
			}
		}catch ( DuplicateKeyException duplicateKeyException) {
			return ResponseEntity.ok().body(Constants.ALREDY_EXIST);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@DeleteMapping("{appcodetype}/{appCode}")
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> deleteprintAppCodes(@PathVariable("appcodetype") String appcodetype,
			@PathVariable("appCode") String appcode) throws NotFoundException {
		int count = 0;
		try {
			count = printAppCodesMapper.remove(appcodetype, appcode);
			if(count!=0 ) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException("Rolename "+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

}
