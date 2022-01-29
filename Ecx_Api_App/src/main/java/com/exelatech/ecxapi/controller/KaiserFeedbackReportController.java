package com.exelatech.ecxapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.KaiserFeedbackReportMapper;
import com.exelatech.ecxapi.model.KaiserFeedbackReport;
import com.exelatech.ecxapi.util.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/kaiser-feedback-report")
public class KaiserFeedbackReportController {

	@Autowired
	private KaiserFeedbackReportMapper kaiserFeedbackReportMapper;

	@PreAuthorize("hasAnyAuthority('_print:kaiserFeedbackReport:view','_print:kaiserFeedbackReport:manage')")
	@GetMapping
	public List<KaiserFeedbackReport> getall() throws NotFoundException {
		List<KaiserFeedbackReport> kaiserFeedbackReports = null;
		try {
			kaiserFeedbackReports = kaiserFeedbackReportMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return kaiserFeedbackReports;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserFileMonitor:view','_print:kaiserFileMonitor:manage')")
	@Operation(summary = "Get FeedbackReport date details - input date format DDMMYYYY")
	@GetMapping("/{date}")
	public List<KaiserFeedbackReport> getFeedbackReportUsingDate(@PathVariable String date) throws NotFoundException {
		List<KaiserFeedbackReport> kaiserFeedbackReports = null;
		try {
			kaiserFeedbackReports = kaiserFeedbackReportMapper.getDetailsUsingDate(date);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return kaiserFeedbackReports;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserFileMonitor:view','_print:kaiserFileMonitor:manage')")
	@GetMapping("/status/{status}")
	public List<KaiserFeedbackReport> getallByStatus(@PathVariable String status) throws NotFoundException {
		List<KaiserFeedbackReport> kaiserFeedbackReports = null;
		try {
			kaiserFeedbackReports = kaiserFeedbackReportMapper.getByStatus(status);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return kaiserFeedbackReports;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserFileMonitor:view','_print:kaiserFileMonitor:manage')")
	@GetMapping("/reportName/{reportName}")
	public List<KaiserFeedbackReport> getallByReportName(@PathVariable String reportName) throws NotFoundException {
		List<KaiserFeedbackReport> kaiserFeedbackReports = null;
		try {
			kaiserFeedbackReports = kaiserFeedbackReportMapper.getByReportName(reportName);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return kaiserFeedbackReports;
	}

}
