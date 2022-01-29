package com.exelatech.ecxapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.exelatech.ecxapi.model.KaiserMonitor;
@Mapper
public interface KaiserFileMonitor extends GenericMapper<KaiserMonitor, String> {
	
	List<KaiserMonitor> getDetailsUsingDate(String date);
	
}
