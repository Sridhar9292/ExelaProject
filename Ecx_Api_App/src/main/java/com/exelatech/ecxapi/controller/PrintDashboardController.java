package com.exelatech.ecxapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/print-dashboard")
public class PrintDashboardController {

	@Value("${elastic.url}")
	private String elasticUrl;
	
	@Value("${elastic.moveit-alias}")
	private String dpsMoveItAlias;
	
	@Value("${elastic.farmingdale-alias}")
	private String farmingdaleAlias;

	@Autowired
	private RestTemplate restTemplate;

	@PreAuthorize("hasAnyAuthority('_printhub:printFileStatus:view','_printhub:printFileStatus:manage')")
	@PostMapping("/dps-dashboard")
	public ResponseEntity<String> api1(@RequestBody String payload, @RequestParam int size) throws NotFoundException{
    	ResponseEntity<String> response = null;
    	try {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		
		 response = restTemplate.postForEntity(
				//"http://" + elasticUrl + "/dps-moveit2020/dps_dashboard/_search?size=" + size, payload, String.class);
				"http://" + elasticUrl + "/" + dpsMoveItAlias + "/_search?size=" + size, request, String.class);
    	} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(response.getBody());

	}

	@PreAuthorize("hasAnyAuthority('_printhub:printFileStatus:view','_printhub:printFileStatus:manage')")
	@PostMapping("/dps-moveit")
	public ResponseEntity<String> api2(@RequestBody String payload, @RequestParam int size) throws NotFoundException{
		ResponseEntity<String> response =null;
		try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		
	response = restTemplate.postForEntity(
				//"http://" + elasticUrl + "/dps-moveit2020/dps_moveit/_search?size=" + size, payload, String.class);
				"http://" + elasticUrl + "/" + dpsMoveItAlias + "/_search?size=" + size, request, String.class);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(response.getBody());

	}

	@PreAuthorize("hasAnyAuthority('_printhub:printWorkflow:view','_printhub:printWorkflow:manage')")
	@PostMapping("/dps-monitor")
	public ResponseEntity<String> api3(@RequestBody String payload, @RequestParam int size) throws NotFoundException{
		ResponseEntity<String> response = null;
		try{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		
		 response = restTemplate
				.postForEntity(
						//"http://" + elasticUrl + "/dps-moveit-2020-04/dps_monitor/_search?filter_path=hits.hits._source&size=" + size,
						"http://" + elasticUrl + "/" + dpsMoveItAlias + "/_search?filter_path=hits.hits._source&size=" + size,
						request, String.class);
	 }catch (final Exception e) {
		e.printStackTrace();
		log.debug(e.getMessage());
		throw new NotFoundException(e.getMessage());
	}
		return ResponseEntity.ok().body(response.getBody());

	}

	@PreAuthorize("hasAnyAuthority('_printhub:farmingDale:view','_printhub:farmingDale:manage')")
	@PostMapping("/farmingdale-summary")
	public ResponseEntity<String> api4(@RequestBody String payload, @RequestParam int size) throws NotFoundException{
		ResponseEntity<String> response = null;
		try{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		
		 response = restTemplate.postForEntity(
				//"http://" + elasticUrl + "/ecx-farmingdale/summary/_search?size=" + size, payload, String.class);
				"http://" + elasticUrl + "/" + farmingdaleAlias + "/_search?size=" + size, request, String.class);
	 }catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(response.getBody());

	}

	@PreAuthorize("hasAnyAuthority('_printhub:farmingDale:view','_printhub:farmingDale:manage')")
	@PostMapping("/farmingdale-event")
	public ResponseEntity<String> api5(@RequestBody String payload, @RequestParam int size) throws NotFoundException{
		ResponseEntity<String> response = null;
		try{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(payload, headers);
		
		 response = restTemplate.postForEntity(
				//"http://" + elasticUrl + "/ecx-farmingdale/event/_search?size=" + size, payload, String.class);
				"http://" + elasticUrl + "/" + farmingdaleAlias + "/_search?size=" + size, request, String.class);
	 }catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(response.getBody());

	}

}
