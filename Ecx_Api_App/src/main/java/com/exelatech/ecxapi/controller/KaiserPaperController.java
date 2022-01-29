package com.exelatech.ecxapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.exelatech.ecxapi.mapper.KaiserTicketMapper;
import com.exelatech.ecxapi.mapper.KaiserUpdateMapper;
import com.exelatech.ecxapi.model.KaiserPaperTicketInfo;
import com.exelatech.ecxapi.model.KaiserPrintRecord;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/kaiser-paper")
public class KaiserPaperController {

	@Autowired
	private KaiserTicketMapper kaiserTicketMapper;
	@Autowired
	private KaiserUpdateMapper kaiserUpdateMapper;

	@PreAuthorize("hasAnyAuthority('_print:kaiserPaperSummary:view','_print:kaiserPaperSummary:manage')")
	@GetMapping
	public List<KaiserPaperTicketInfo> getKaiProcess() throws NotFoundException {
		List<KaiserPaperTicketInfo> kaiserPaperTicketInfo = null;
		try {
			kaiserPaperTicketInfo = kaiserTicketMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return kaiserPaperTicketInfo;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserPaperSummary:view','_print:kaiserPaperSummary:manage')")
	@GetMapping("search/{filter-column}/{filter-value}")
	public List<KaiserPaperTicketInfo> getsearch(@PathVariable("filter-column") String column,
			@PathVariable("filter-value") String value) throws NotFoundException {
		List<KaiserPaperTicketInfo> result=null;
		try {
			if (column.equalsIgnoreCase("status")) {
				List<KaiserPaperTicketInfo> all = kaiserTicketMapper.getAll();
					result = all.stream() // convert list to stream
						.filter(line -> line.getStatus().equals(value)).collect(Collectors.toList());
				
			} else if (column.equalsIgnoreCase("job")) {
				result= kaiserTicketMapper.search_job(value);
			} else if (column.equalsIgnoreCase("purchaser")) {
				result= kaiserTicketMapper.search_Purchaser(value);
			}else {
				result= kaiserTicketMapper.getAll();
			}
			
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return result;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserPaperSummary:view','_print:kaiserPaperSummary:manage')")
	@GetMapping("/{jobNumber}")
	public List<KaiserPrintRecord> getPrintRecord(@PathVariable("jobNumber") String jobNumber)
			throws NotFoundException {
		List<KaiserPrintRecord> KaiserPrintRecord = null;
		try {
			KaiserPrintRecord = kaiserUpdateMapper.search(jobNumber);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return KaiserPrintRecord;
	}
	@PreAuthorize("hasAnyAuthority('_print:kaiserPaperSummary:send','_print:kaiserPaperSummary:manage')")
	@PutMapping("/update-sent-status/{jobNumber}")
	public ResponseEntity<String> updateAll(@PathVariable("jobNumber") String jobNumber) throws NotFoundException {
		int count=0;
		try {
			count=kaiserTicketMapper.updateSent(jobNumber);
			if (count != 0 ) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("RITS_JOB_NUMBER" + Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserPaperSummary:update','_print:kaiserPaperSummary:manage')")
	@PutMapping
	public ResponseEntity<String> save(@RequestBody List<KaiserPrintRecord> KaiserPrintRecord)
			throws NotFoundException {
		int count=0;
		try {		
			for (KaiserPrintRecord rec : KaiserPrintRecord) {
				if (rec.getShipDate() != null && !rec.getShipDate().equals("") && rec.getJob() != null
						&& !rec.getJob().equals("")) {
					count=kaiserUpdateMapper.updateOn(rec.getId(), rec.getShipDate(), rec.getStatus(), rec.getJob());
				    if(count==0) {
				    	throw new NotFoundException("RITS_JOB_NUMBER" +Constants.NOT_EXIST);
				    }
				    count=0;
				} 
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}
}
