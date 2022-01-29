package com.exelatech.ecxapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.PrintHubJobHeaderMapper;
import com.exelatech.ecxapi.model.PrintHubDashboard;
import com.exelatech.ecxapi.model.PrintHubJobDetails;
import com.exelatech.ecxapi.model.PrintHubJobHeader;
import com.exelatech.ecxapi.model.PrintHubJobPayload;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/printhub/dashboard")
public class PrintHubDashbordController {

	@Autowired
	private PrintHubJobHeaderMapper printhubJobHeaderMapper;

	// First level data using interval
	@GetMapping
	public PrintHubDashboard listPrintHubDashboardForLastMinsHours(
			@RequestParam(value = "last", required = false) String time,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to) throws NotFoundException {
		PrintHubDashboard printHubJobHeader = new PrintHubDashboard();
		try {

			if (time == null && from == null && to == null) {
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getAll());
			}

			if (time != null) {
				int timeInMin = 0;
				if (time.contains("mins")) {
					timeInMin = Integer.parseInt(time.substring(0, time.indexOf("mins")));
				} else if (time.contains("hours")) {
					timeInMin = Integer.parseInt(time.substring(0, time.indexOf("hours"))) * 60;
				} else if (time.contains("days")) {
					timeInMin = Integer.parseInt(time.substring(0, time.indexOf("days"))) * 24 * 60;
				}
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getByMinHoursSec(Integer.toString(timeInMin)));
			} else if (from != null && to != null) {
				System.out.println("came inside from and to");
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getByFromToDate(from, to));
			} else if (from != null) {
				System.out.println("came inside from");
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getByFromDate(from));
			}

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printHubJobHeader;
	}

	// First level data using search
	@GetMapping(value = "search")
	public PrintHubDashboard getPrintHubDashboardDetails(
			@RequestParam(value = "solution-name", required = false) String solutionname,
			@RequestParam(value = "app-code", required = false) String appcode,
			@RequestParam(value = "job-number", required = false) String jobnumber,
			@RequestParam(value = "destination", required = false) String destination,
			@RequestParam(value = "solution-id", required = false) String solutionid,
			@RequestParam(value = "instance-id", required = false) String instanceid,
			@RequestParam(value = "file-name", required = false) String fileName) throws NotFoundException {
		PrintHubDashboard printHubJobHeader = new PrintHubDashboard();

		try {
			if (solutionname != null) {

				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlBySolutionName(solutionname));
			} else if (appcode != null) {
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlByAppCode(appcode));
			} else if (jobnumber != null) {
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlByJobNumber(jobnumber));
			} else if (destination != null) {
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlBydestination(destination));
			} else if (solutionid != null) {
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlBySolutionId(solutionid));
			} else if (instanceid != null) {
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlByInstanceId(instanceid));
			} else if (fileName != null) {
				printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDtlByFileName(fileName));
			}

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printHubJobHeader;
	}

	// First level data using search using start-date
	@GetMapping(value = "search/{start-date}")
	public PrintHubDashboard listPrintHubDashboardDate(@PathVariable(value = "start-date") String startdate)
			throws NotFoundException {
		PrintHubDashboard printHubJobHeader = new PrintHubDashboard();

		try {

			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getDate(startdate));
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printHubJobHeader;
	}

	// First level data using search using groupid
	@GetMapping(value = "/{groupid}")
	public PrintHubDashboard listPrintHubDashboard(@PathVariable(value = "groupid") String groupid)
			throws NotFoundException {
		PrintHubDashboard printHubJobHeader = new PrintHubDashboard();

		try {

			printHubJobHeader.setPrintFeeds(printhubJobHeaderMapper.getGroupid(groupid));
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printHubJobHeader;
	}

	// Second level data using group-id
	@GetMapping("/container-details/{group-id}")
	public List<PrintHubJobDetails> dashboardcontDetailsByGroupId(@PathVariable("group-id") String groupId)
			throws NotFoundException {
		List<PrintHubJobDetails> printHubJobDetailsList = new ArrayList<>();
		try {

			printHubJobDetailsList = printhubJobHeaderMapper.dashboardcontDetailsByGroupId(groupId);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printHubJobDetailsList;
	}

	// Second level data using cont-id
	@GetMapping("/contDetails/{cont-id}")
	public List<PrintHubJobDetails> dashboardcontDetailsByContId(@PathVariable("cont-id") String containerId)
			throws NotFoundException {
		List<PrintHubJobDetails> printHubJobDetailsList = new ArrayList<>();
		try {

			printHubJobDetailsList = printhubJobHeaderMapper.dashboardcontDetailsByContId(containerId);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printHubJobDetailsList;
	}

	// Third level data using instance-id and file-name
	@GetMapping("/details/{instance-id}/{file-name}")
	public List<PrintHubJobPayload> dashboardDetails(@PathVariable("instance-id") String instanceId,
			@PathVariable("file-name") String fileName) throws NotFoundException {
		List<PrintHubJobPayload> printHubJobDetailsList = new ArrayList<>();
		try {

			printHubJobDetailsList = printhubJobHeaderMapper.getDetailsByInstanceIdFileName(instanceId, fileName);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printHubJobDetailsList;
	}

	// Third level data using container-id
	@GetMapping("/instance-details/{container-id}")
	public List<PrintHubJobPayload> dashboardPayLoadByContId(@PathVariable("container-id") String contId)
			throws NotFoundException {
		List<PrintHubJobPayload> printHubJobDetailsList = new ArrayList<>();
		try {

			printHubJobDetailsList = printhubJobHeaderMapper.getDashboardPayLoadByContId(contId);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printHubJobDetailsList;
	}

	// Third level data using instance-id
	@GetMapping("/details/{instance-id}")
	public List<PrintHubJobPayload> dashboardDetailsByInstanceId(@PathVariable("instance-id") String instanceId)
			throws NotFoundException {
		List<PrintHubJobPayload> printHubJobDetailsList = new ArrayList<>();
		try {

			printHubJobDetailsList = printhubJobHeaderMapper.getDetailsByInstanceId(instanceId);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return printHubJobDetailsList;
	}

	@PostMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> insertPrintDetails(@RequestBody PrintHubDashboard printHubDashboard)
			throws NotFoundException {
		int count = 0;
		int count1 = 0;
		try {
			for (PrintHubJobHeader printHeader : printHubDashboard.getPrintFeeds()) {
				for (PrintHubJobDetails summaryList : printHeader.getSummary()) {
					count = printhubJobHeaderMapper.jobDetailsInsert(summaryList);
					if (count == 0)
						throw new NotFoundException("jobDetailsInsert " + Constants.NOT_EXIST);
					for (PrintHubJobPayload payload : summaryList.getDetail()) {
						count1 = printhubJobHeaderMapper.jobPayloadInsert(payload);
						if (count1 == 0)
							throw new NotFoundException("jobPayloadInsert " + Constants.NOT_EXIST);
						count1 = 0;
					}
				}
			}
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException(Constants.ALREDY_EXIST);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}

	@PostMapping("/job-header")
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> insertPrinthubHeader(@RequestBody PrintHubDashboard printHubDashboard)
			throws NotFoundException {
		int count = 0;
		try {
			for (PrintHubJobHeader printHeader : printHubDashboard.getPrintFeeds()) {
				count = printhubJobHeaderMapper.jobHeaderInsert(printHeader);
				if (count == 0)
					throw new NotFoundException("jobHeaderInsert " + Constants.NOT_EXIST);
				count = 0;
			}
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException(Constants.ALREDY_EXIST);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}

	@PutMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> UpdatePrintDetails(@RequestBody PrintHubDashboard printHubDashboard)
			throws NotFoundException {
		int count = 0;
		int count1 = 0;
		int count2 = 0;
		try {
			for (PrintHubJobHeader printHeader : printHubDashboard.getPrintFeeds()) {
				count = printhubJobHeaderMapper.jobHeaderUpdate(printHeader);
				if (count == 0)
					throw new NotFoundException("jobHeaderUpdate " + Constants.NOT_EXIST);
				count = 0;
				for (PrintHubJobDetails summaryList : printHeader.getSummary()) {
					count1 = printhubJobHeaderMapper.jobDetailsUpdate(summaryList);
					if (count1 == 0)
						throw new NotFoundException("jobDetailsUpdate " + Constants.NOT_EXIST);
					count1 = 0;
					for (PrintHubJobPayload payload : summaryList.getDetail()) {
						count2 = printhubJobHeaderMapper.jobPayloadUpdate(payload);
						if (count2 == 0)
							throw new NotFoundException("jobPayloadUpdate " + Constants.NOT_EXIST);
						count2 = 0;
					}
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}

}
