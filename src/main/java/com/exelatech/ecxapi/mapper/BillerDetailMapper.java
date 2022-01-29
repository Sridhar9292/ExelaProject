package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.BillerDetail;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillerDetailMapper extends GenericMapper<BillerDetail, String> {
    <BillerDetail> BillerDetail getBillerDetails(String feedDocID, String clientCode, String clientLobCode);

    BillerDetail getBillerDelivery(String clientCode, String clientLobCode);

    BillerDetail getBillerDeliveryDetails(String clientCode, String clientLobCode);

    List<BillerDetail> getProcessedBillerDetails(String feedDocID, String billerCode);
}
