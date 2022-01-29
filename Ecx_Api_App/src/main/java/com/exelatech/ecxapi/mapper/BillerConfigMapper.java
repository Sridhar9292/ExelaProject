package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.BillerConfig;
import com.exelatech.ecxapi.model.BillerConfigId;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BillerConfigMapper extends GenericMapper<BillerConfig, BillerConfigId> {

	BillerConfig getBillerConfigByclientCodeclientLobCode(@Param("clientCode") String clientCode,
			@Param("clientLobCode") String clientLobCode, @Param("filter") List<String> filter);
}
