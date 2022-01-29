package com.exelatech.ecxapi.mapper;

import com.exelatech.ecxapi.model.EmailJob;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailJobMapper extends GenericMapper<EmailJob, String> {

}
