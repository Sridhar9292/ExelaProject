package com.exelatech.ecx.backend.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.exelatech.ecx.backend.model.ErrorCodes;

public interface ErrorCodesMapper extends GenericMapper<ErrorCodes, String> {

	ErrorCodes get(@Param("errorCode") String errorCode);
	List<ErrorCodes> getAll();
}
