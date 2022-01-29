package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.ErrorCodes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErrorCodesMapper extends GenericMapper<ErrorCodes, String> {

	ErrorCodes get(String errorCode);

	List<ErrorCodes> getAll();
}
