package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.EmailJobDetail;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailJobDetailMapper extends GenericMapper<EmailJobDetail, String> {

	List<EmailJobDetail> getJobDetails(String fedDocID);

}
