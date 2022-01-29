package com.exelatech.ecxapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.exelatech.ecxapi.model.PrintHubJobDetails;
import com.exelatech.ecxapi.model.PrintHubJobHeader;
import com.exelatech.ecxapi.model.PrintHubJobPayload;

@Mapper
public interface PrintHubJobHeaderMapper extends GenericMapper<PrintHubJobHeader, String> {

	List<PrintHubJobHeader> getDate(String startdate);

	List<PrintHubJobHeader> getGroupid(String groupID);

	int jobHeaderInsert(PrintHubJobHeader jobHeader);

	int jobDetailsInsert(PrintHubJobDetails listJobDetails);

	int jobPayloadInsert(PrintHubJobPayload PrintHubJobPayload);

	int jobHeaderUpdate(PrintHubJobHeader jobHeader);

	int jobDetailsUpdate(PrintHubJobDetails listJobDetails);

	int jobPayloadUpdate(PrintHubJobPayload PrintHubJobPayload);

	List<PrintHubJobHeader> getByMinHoursSec(String time);
	
	List<PrintHubJobDetails> dashboardcontDetailsByContId(String containerId);
	
	List<PrintHubJobDetails> dashboardcontDetailsByGroupId(String groupId);

	List<PrintHubJobHeader> getByFromDate(String fromDate);

	List<PrintHubJobPayload> getDetailsByInstanceId(String instanceId);
	
	List<PrintHubJobPayload> getDashboardPayLoadByContId(String contId);

	List<PrintHubJobPayload> getDetailsByInstanceIdFileName(@Param("instanceId") String instanceId,
			@Param("fileName") String fileName);

	List<PrintHubJobHeader> getByFromToDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
	List<PrintHubJobHeader>  getDtlBySolutionName(@Param("solutionname") String solutionname);

	List<PrintHubJobHeader>  getDtlByAppCode(@Param("appcode") String appcode);

	List<PrintHubJobHeader>  getDtlByJobNumber(@Param("jobnumber") String jobnumber);

	List<PrintHubJobHeader>  getDtlBydestination(@Param("destination") String destination);

	List<PrintHubJobHeader>  getDtlBySolutionId(@Param("solutionid") String solutionid);

	List<PrintHubJobHeader>  getDtlByInstanceId(@Param("instanceid") String instanceid);
	
	List<PrintHubJobHeader>  getDtlByFileName(@Param("fileName") String fileName);
}
