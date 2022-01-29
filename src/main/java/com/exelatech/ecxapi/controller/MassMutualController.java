package com.exelatech.ecxapi.controller;

import java.util.List;

import com.exelatech.ecxapi.mapper.MassMutualMapper;
import com.exelatech.ecxapi.model.MassMutualDetailInfo;
import com.exelatech.ecxapi.model.MassMutualFeedInfo;
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
@RequestMapping("/mass-mutual")
public class MassMutualController {

	@Autowired
	public MassMutualMapper massMutualMapper;

	@PreAuthorize("hasAnyAuthority('_print:massMutualDashboard:view','_print:massMutualDashboard:manage')")
	@GetMapping 
	public List<MassMutualFeedInfo> getAll() throws NotFoundException {
		List<MassMutualFeedInfo> massMutualFeedInfo=null;
		try {
			massMutualFeedInfo=massMutualMapper.getMassMutualProcessInfo();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return massMutualFeedInfo;
	}

	@PreAuthorize("hasAnyAuthority('_print:massMutualDashboard:view','_print:massMutualDashboard:manage')")
	@GetMapping("/{infoId}")
	public List<MassMutualDetailInfo> handleDetailRequest(@PathVariable("infoId") String infoId) throws NotFoundException{	
		List<MassMutualDetailInfo> massMutualDetailInfo=null;
		try {
			massMutualDetailInfo=massMutualMapper.getmassmutualDetailInfo(Integer.parseInt(infoId));	 
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return massMutualDetailInfo;
	}
	@PreAuthorize("hasAnyAuthority('_print:massMutualDashboard:view','_print:massMutualDashboard:manage')")
	@GetMapping("/{infoId}/{condition}/{query}")
	public List<MassMutualDetailInfo> handleSearchRequest(@PathVariable("infoId")  String infoId,@PathVariable("condition")  String condition, @PathVariable("query") String query) throws NotFoundException {
		try {

			System.out.println("condition===>>>>"+infoId+"/"+query+"/"+condition);
			if ((query == null || query.equals("")) && (infoId !=null && !infoId.equals(""))) {
				return massMutualMapper.getmassmutualDetailInfo(Integer.parseInt(infoId));
			} else {
				if (condition.equals("reference")) {

					return	massMutualMapper.getmassmutualDetailInfoSearchRef("%" + query + "%", Integer.parseInt(infoId));
				}
				else if (condition.equals("tracking")){
					return	massMutualMapper.getmassmutualDetailInfoSearchTN("%" + query + "%", Integer.parseInt(infoId));
				}else if (condition.equals("status")){
					if(query.equals("all")) {
						return massMutualMapper.getmassmutualDetailInfo(Integer.parseInt(infoId));
					}else {
						return	massMutualMapper.getmassmutualDetailInfoStatus("%" + query + "%", Integer.parseInt(infoId));
					}
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return  massMutualMapper.getmassmutualDetailInfo(Integer.parseInt(infoId));
	}
}