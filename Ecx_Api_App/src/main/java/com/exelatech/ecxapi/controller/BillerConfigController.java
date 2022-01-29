package com.exelatech.ecxapi.controller;

import java.util.List;

import com.exelatech.ecxapi.mapper.BillerConfigMapper;
import com.exelatech.ecxapi.model.BillerConfig;
import com.exelatech.ecxapi.util.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@Slf4j	
@RestController
@RequestMapping("/biller-configs")
public class BillerConfigController {
	@Autowired
	private BillerConfigMapper billerConfigMapper;
	/*@Autowired
	private BillerMapper billerMapper;
	*/
	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:view','_remit:billerManagement:manage')")
	@GetMapping
	public List<BillerConfig> getAll() throws NotFoundException {
		List<BillerConfig> billerConfig = null;
		try {
			billerConfig = billerConfigMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return billerConfig;
	}

	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:view','_remit:billerManagement:manage')")
	@GetMapping("/{clientCode}/{clientLobCode}")
	public BillerConfig getBillerDetails(@PathVariable("clientCode") String clientCode,
			@PathVariable("clientLobCode") String clientLobCode) throws NotFoundException {
		BillerConfig biller = null;
		try {

			biller = billerConfigMapper.getBillerConfigByclientCodeclientLobCode(clientCode, clientLobCode, null);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return biller;
	}

	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:view','_remit:billerManagement:manage')")
	@GetMapping("search/{query}")
	public List<BillerConfig> serach(@PathVariable("query") String query) throws NotFoundException {

		try {
			if (query == null || query.equals("")) {
				return billerConfigMapper.getAll();
			}
			return billerConfigMapper.search("%" + query + "%", null);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

}
