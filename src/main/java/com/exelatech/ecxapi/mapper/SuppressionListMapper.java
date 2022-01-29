package com.exelatech.ecxapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exelatech.ecxapi.model.SuppressionList;
@Mapper
public interface SuppressionListMapper extends GenericMapper<SuppressionList, String> {
	public List<SuppressionList> getSuppressDetails(String jobNumber);
	public List<SuppressionList> getCustomSearch(String jobNumber, String searchTerm);
	public String getSuppressReason(String emailAddress);
}
