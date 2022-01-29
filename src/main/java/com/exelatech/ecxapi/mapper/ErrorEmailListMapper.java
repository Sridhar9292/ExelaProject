package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.ErrorEmail;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErrorEmailListMapper extends GenericMapper<ErrorEmail, String> {

	public List<String> getSubAccounts();

}
