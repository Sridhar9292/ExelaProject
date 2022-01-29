package com.exelatech.ecxapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exelatech.ecxapi.model.KaiserFeedbackReport;

@Mapper
public interface KaiserFeedbackReportMapper extends GenericMapper<KaiserFeedbackReport, String>{
	
	 List<KaiserFeedbackReport> getDetailsUsingDate(String date);
	
	 List<KaiserFeedbackReport> getByReportName(String reportName);
	 
	 List<KaiserFeedbackReport> getByStatus(String status);

}
