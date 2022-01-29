package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.ACGDashboard;
import com.exelatech.ecxapi.model.ACGPrintRecord;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ACGMapper extends GenericMapper<ACGDashboard, String> {

	public List<ACGDashboard> getACGDashBoardInfo(String startDate);

	public List<ACGPrintRecord> getACGImmediatePrintRecordInlineInfo(String msgId);

	public List<ACGDashboard> getACGMessageErr(String msgId);

	public List<ACGDashboard> getACGDashboardSuccess(String stdate, String status);

	public List<ACGDashboard> getACGDashboardIncomplete(String status);

}
