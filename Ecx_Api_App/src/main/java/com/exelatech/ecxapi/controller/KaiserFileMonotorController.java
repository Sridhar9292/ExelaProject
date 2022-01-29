package com.exelatech.ecxapi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.KaiserFileMonitor;
import com.exelatech.ecxapi.model.KaiserMonitor;
import com.exelatech.ecxapi.util.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/kaiser-file-monitor")
public class KaiserFileMonotorController {

	
	@Autowired
	private KaiserFileMonitor kaiserFilemonitor;


	@PreAuthorize("hasAnyAuthority('_print:kaiserFileMonitor:view','_print:kaiserFileMonitor:manage')")
	//@PreAuthorize("hasAnyAuthority('_print:kaiserPaperSummary:view','_print:kaiserPaperSummary:manage')")
	@GetMapping
	public List<KaiserMonitor> getall() throws NotFoundException  { 
		List<KaiserMonitor> FileMonotorInfo = null;
		try {
			FileMonotorInfo=kaiserFilemonitor.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return 	FileMonotorInfo;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserFileMonitor:view','_print:kaiserFileMonitor:manage')")
	//@PreAuthorize("hasAnyAuthority('_print:kaiserPaperSummary:view','_print:kaiserPaperSummary:manage')")
	@Operation(summary= "Get FileMonitor date details - input date format DDMMYYYY")
	@GetMapping("/{date}")
	public List<KaiserMonitor> getFileMonitorUsingDate(@PathVariable String date) throws NotFoundException  { 
		List<KaiserMonitor> FileMonotorInfo = null;
		try {
			SimpleDateFormat sourceFormat = new SimpleDateFormat("ddMMyyyy");
			Date date_check= (Date) sourceFormat.parse(date);
			FileMonotorInfo=kaiserFilemonitor.getDetailsUsingDate(date);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return 	FileMonotorInfo;
	}

}
