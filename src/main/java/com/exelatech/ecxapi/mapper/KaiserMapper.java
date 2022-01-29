package com.exelatech.ecxapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exelatech.ecxapi.model.KaiserDirectories;
import com.exelatech.ecxapi.model.KaiserProcessInfo;
import com.exelatech.ecxapi.model.KaiserStatus;
import com.exelatech.ecxapi.model.RejectStatus;

@Mapper
public interface KaiserMapper {
	List<KaiserProcessInfo> getKaiProcessInfo();

	List<KaiserStatus> getKaiProcessStatus(String infoId);

	List<KaiserProcessInfo> getKaiProcessInfoSearch(String searchTerm);

	List<KaiserProcessInfo> getKaiProcessInfoSearchJob(String searchTerm);

	List<KaiserDirectories> getKaiProcessDirectories(String infoId);

	List<RejectStatus> getKaiErrorStatusRecord(String infoId);

	List<RejectStatus> getKaiErrorStatusFile(String infoId);

	List<String> getKaiRejDirList(String infoId);

	List<String> getKaiRejXMLList(String infoId);
}
