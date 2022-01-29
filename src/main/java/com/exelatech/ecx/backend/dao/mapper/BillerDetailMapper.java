package com.exelatech.ecx.backend.dao.mapper;

import com.exelatech.ecx.backend.model.BillerDetail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BillerDetailMapper extends GenericMapper<BillerDetail, String> {
	BillerDetail getBillerDetails(@Param("feedDocID") String feedDocID, @Param("clientCode") String clientCode,
			@Param("clientLobCode") String clientLobCode);

	BillerDetail getBillerDelivery(@Param("clientCode") String clientCode,
			@Param("clientLobCode") String clientLobCode);

	BillerDetail getBillerDeliveryDetails(@Param("clientCode") String clientCode,
			@Param("clientLobCode") String clientLobCode);

	List<BillerDetail> getProcessedBillerDetails(@Param("feedDocID") String feedDocID,
			@Param("billerCode") String billerCode);

	int getExportStatus(@Param("tableName") String tableName, @Param("feedDocId") String feedDocId);
	
	int getBillerExportStatus(@Param("tableName") String tableName, @Param("feedDocId") String feedDocId, @Param("clientCode") String clientCode, @Param("clientLobCode") String clientLobCode);
}
