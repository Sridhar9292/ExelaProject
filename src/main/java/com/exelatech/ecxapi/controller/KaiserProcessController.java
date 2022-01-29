package com.exelatech.ecxapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.KaiserMapper;
import com.exelatech.ecxapi.model.KaiserDirectories;
import com.exelatech.ecxapi.model.KaiserProcessInfo;
import com.exelatech.ecxapi.model.KaiserRejectSummary;
import com.exelatech.ecxapi.model.KaiserStatus;
import com.exelatech.ecxapi.model.RejectStatus;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/kaiser-process")
public class KaiserProcessController {

	@Autowired
	private KaiserMapper kaiserMapper;

	@PreAuthorize("hasAnyAuthority('_print:kaiserProcessSummary:view','_print:kaiserProcessSummary:manage')")
	@GetMapping
	public List<KaiserProcessInfo> getall() throws NotFoundException {
		List<KaiserProcessInfo> kaiserProcessInfos = null;
		try {
			kaiserProcessInfos = kaiserMapper.getKaiProcessInfo();

				for (KaiserProcessInfo KaiserProcessInfo : kaiserProcessInfos) {
					
					List<RejectStatus> kaiserErrorFileList = kaiserMapper.getKaiErrorStatusFile(KaiserProcessInfo.getInfoId());
					List<RejectStatus> kaiserErrorRecordList = kaiserMapper.getKaiErrorStatusRecord(KaiserProcessInfo.getInfoId());
					List<String> kaiserErrorDirList = kaiserMapper.getKaiRejDirList(KaiserProcessInfo.getInfoId());

					if (kaiserErrorFileList.size() > 0 || kaiserErrorRecordList.size() > 0 || kaiserErrorDirList.size() > 0) {
						KaiserProcessInfo.setIsErrorPresent(true);
					} else {
						KaiserProcessInfo.setIsErrorPresent(false);
					}
				}

			
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return kaiserProcessInfos;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserProcessSummary:view','_print:kaiserProcessSummary:manage')")
	@GetMapping("search/{filter-column}/{filter-value}")
	public List<KaiserProcessInfo> serach(@PathVariable("filter-column") String column,
			@PathVariable("filter-value") String value) throws NotFoundException {
		List<KaiserProcessInfo> kaiserProcessInfo = null;
		try {
			if (column.equalsIgnoreCase("job")) {

				kaiserProcessInfo = kaiserMapper.getKaiProcessInfoSearchJob(value);
			} else {
				kaiserProcessInfo = kaiserMapper.getKaiProcessInfo();
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return kaiserProcessInfo;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserProcessSummary:view','_print:kaiserProcessSummary:manage')")
	@GetMapping("status/{infoId}")
	public List<KaiserStatus> handleRequest(@PathVariable("infoId") String infoId) throws NotFoundException {
		List<KaiserStatus> kaiserStatusList = null;
		try {
			kaiserStatusList = kaiserMapper.getKaiProcessStatus(infoId);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return kaiserStatusList;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserProcessSummary:view','_print:kaiserProcessSummary:manage')")
	@GetMapping("directories/{infoId}")
	public List<KaiserDirectories> getDirectories(@PathVariable("infoId") String infoId) throws NotFoundException {
		List<KaiserDirectories> kaiProcessStatus = null;
		try {
			kaiProcessStatus = kaiserMapper.getKaiProcessDirectories(infoId);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return kaiProcessStatus;
	}

	@PreAuthorize("hasAnyAuthority('_print:kaiserProcessSummary:view','_print:kaiserProcessSummary:manage')")
	@GetMapping("error-status/{infoId}")
	public KaiserRejectSummary handleErrorRequest(@PathVariable("infoId") String infoId) throws NotFoundException {
		try {
			String regex = "[0-9]+";
			KaiserRejectSummary kaiserRejectSummary = new KaiserRejectSummary();
			List<RejectStatus> kaiserErrorFileList = kaiserMapper.getKaiErrorStatusFile(infoId);
			List<RejectStatus> kaiserErrorRecordList = kaiserMapper.getKaiErrorStatusRecord(infoId);
			List<String> kaiserErrorDirList = kaiserMapper.getKaiRejDirList(infoId);
			List<String> kaiserErrorXMLList = new ArrayList<String>();
			if (infoId.matches(regex)) {
				kaiserErrorXMLList = kaiserMapper.getKaiRejXMLList(infoId);
			}
			kaiserRejectSummary.setRejDirList(kaiserErrorDirList);
			kaiserRejectSummary.setRecordRejList(kaiserErrorRecordList);
			kaiserRejectSummary.setFileRejList(kaiserErrorFileList);
			kaiserRejectSummary.setRejXMLList(kaiserErrorXMLList);
			// kaiserErrorXMLList.forEach(System.out::println);

			return kaiserRejectSummary;
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}
}
