package com.exelatech.ecxapi.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.BillerMapper;
import com.exelatech.ecxapi.model.Biller;
import com.exelatech.ecxapi.model.ExternalBillerIds;
import com.exelatech.ecxapi.model.LookUp;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j	
@RestController
@RequestMapping("/billers")
public class BillerController {

	@Autowired
	private BillerMapper billerMapper;

	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:view','_remit:billerManagement:manage')")
	@GetMapping("/{clientCode}/{clientLobCode}")
	public Biller getBiller(@PathVariable String clientCode, @PathVariable String clientLobCode)
			throws NotFoundException {		
		// List<String> filter = ((User) authentication.getPrincipal()).getFilter(Constants.DataFilter.REMIT_CLIENT_CODE.toString());

		Biller biller = null;
		try {
			biller = billerMapper.get(clientCode,clientLobCode,null);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return biller;
	}

	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:edit','_remit:billerManagement:manage')")
	@PutMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> updateBiller(@RequestBody Biller biller) throws NotFoundException {
		int count1=0; int count2=0; int count3=0;int count4=0;

		try {
			for (Iterator<ExternalBillerIds> i = biller.getExternalBillerIdsList().iterator(); i.hasNext();) {
				ExternalBillerIds ebi = i.next();
				if (ebi.getBillerId() == null || ebi.getBillerId().equals("")) {
					i.remove();
				}

			}
			for (ExternalBillerIds ebi : biller.getExternalBillerIdsList()) {
				ebi.setClientCode(biller.getClientCode());
				ebi.setClientLobCode(biller.getClientLobCode());
				ebi.setReturnsConfEnabled(
						(ebi.getReturnsEnabled().equals("Y") && ebi.getVendorCode().equals("RPPS")) ? "Y" : "N");
			}
			if (("true").equalsIgnoreCase(biller.getBillerEnabled())) {
				biller.setBillerEnabled("Y");
				billerMapper.updateBillerEnabled(biller);
			} else {
				biller.setBillerEnabled("N");
				billerMapper.updateBillerEnabled(biller);
			}
			count1=billerMapper.updateClient(biller);
			count2=billerMapper.updateClientLob(biller);
			count3=billerMapper.updateBiller(biller);

			billerMapper.deleteBillerVendor(biller);
			count4=billerMapper.addBillerVendor(biller.getExternalBillerIdsList());
			if (count1 != 0 && count2 != 0 &&count3 != 0 && count4 != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("Biller "+Constants.NOT_EXIST);
			}			
			} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:add','_remit:billerManagement:manage')")
	@PostMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> addBiller(@RequestBody Biller biller) throws NotFoundException {
		System.out.println("biller--->" + biller);
		int count1=0; int count2=0; int count3=0;int count4=0;
		try {

			for (Iterator<ExternalBillerIds> i = biller.getExternalBillerIdsList().iterator(); i.hasNext();) {
				ExternalBillerIds ebi = i.next();
				if (ebi.getBillerId() == null || ebi.getBillerId().equals("")) {
					i.remove();
				}

			}
			for (ExternalBillerIds ebi : biller.getExternalBillerIdsList()) {
				ebi.setClientCode(biller.getClientCode());
				ebi.setClientLobCode(biller.getClientLobCode());
				ebi.setReturnsConfEnabled(
						(ebi.getReturnsEnabled().equals("Y") && ebi.getVendorCode().equals("RPPS")) ? "Y" : "N");
			}

			boolean isNew = billerMapper.billerExists(biller).getCount() == 0 ? true : false;

			if (isNew) {
				
				if(billerMapper.clientExists(biller.getClientCode()).getCount()==0)
				{
					count1=billerMapper.addClient(biller);
				}else {
					count1=1;
				}				
		
				count2=billerMapper.addClientLob(biller);			
				count3=billerMapper.addBiller(biller);				
				count4=billerMapper.addBillerVendor(biller.getExternalBillerIdsList());
				
			}
			if (count1 != 0 && count2 != 0 &&count3 != 0 && count4 != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				
				throw new NotFoundException("Biller "+Constants.NOT_INSERT);
			}
		}catch ( DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("Cannot add Biller. Biller "+Constants.ALREDY_EXIST);
	

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:delete','_remit:billerManagement:manage')")
	@DeleteMapping("/{clientCode}/{clientLobCode}")
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> deleteBiller(@PathVariable("clientCode") String clientCode, @PathVariable("clientLobCode")  String clientLobCode)
			throws NotFoundException {
		int count=0; 

		Biller biller = new Biller();
		biller.setClientCode(clientCode);
		biller.setClientLobCode(clientLobCode);

		try {
			billerMapper.deleteBillerVendor(biller);
			count=billerMapper.deleteBiller(biller);
			if (count != 0 ) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("Biller "+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}
	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:manage','_remit:billerManagement:view')")
	@GetMapping("/subtype-details")
	public List<LookUp>  subtypeDetails() throws NotFoundException {
		List<LookUp>  data = new ArrayList<LookUp> ();
		try {
		data = billerMapper.getLookupDetails();
	} catch (final Exception e) {
		e.printStackTrace();
		log.debug(e.getMessage());
		throw new NotFoundException(e.getMessage());
	}
		return data;
	}

	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:manage','_remit:billerManagement:view')")
	@GetMapping("/posting-id/{postingId}")
	public LookUp showPostingId(@PathVariable("postingId") String postingId) throws NotFoundException {
		LookUp data = new LookUp();
		try {
			data = billerMapper.getPostingId(postingId);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return data;
	}

	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:manage','_remit:billerManagement:view')")
	@GetMapping("vendor-check/{vendor}/{billerId}")
	public ResponseEntity<?> duplicateBillerId(@PathVariable("vendor")String vendor,@PathVariable("billerId") String billerId) throws NotFoundException {
		boolean getValue=true;
		try {
			if(billerMapper.billerVendorExists(vendor,billerId).getCount()!=0)
			{
				getValue=false;
			}
			return ResponseEntity.ok().body(getValue); 
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_remit:billerManagement:manage')")
	@PutMapping("vendor-status/{client_Code}/{client_Lob_Code}/{status}")
	public ResponseEntity<String> enableBiller(@PathVariable("client_Code") String clientCode,
			@PathVariable("client_Lob_Code") String clientLobCode,
			@PathVariable("status") String status)
					throws NotFoundException {
		int count =0;   
		try {

			Biller biller= new Biller();
			biller.setClientCode(clientCode);
			biller.setClientLobCode(clientLobCode);
			if (status.equalsIgnoreCase("true")) {
				biller.setBillerEnabled("Y");
				count=billerMapper.updateBillerEnabled(biller);
			} else {
				biller.setBillerEnabled("N");
				count=billerMapper.updateBillerEnabled(biller);
			}		
			if (count != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("Biller "+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}
}
