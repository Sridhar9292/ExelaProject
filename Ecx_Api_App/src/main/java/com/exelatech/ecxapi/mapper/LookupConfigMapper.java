package com.exelatech.ecxapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exelatech.ecxapi.model.Lookups;

@Mapper
public interface LookupConfigMapper extends GenericMapper<Lookups, String>{
	
	int addLookups(Lookups lookups);
	
	int updateUsingSubType(Lookups lookups);
	
	Lookups getValue(String type, String subtype,String name);
	
	List<Lookups> getAllLookupsByType(String type);
	
	int deleteUsingSubType(String type, String subtype,String name);
	List<Lookups> getUserRegisterLookups();
}
