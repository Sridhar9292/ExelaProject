package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Biller implements Serializable {
    private static final long serialVersionUID = -3182002495312446271L;
    private String id;
    private String clientCode;
    private String clientName;
    private String clientLobCode;
    private String clientLobName;
    private String delivery="RPS";
    private String billerName;
    private String billerAliasName;
    private String billerCode;
    private String billerSiteCode;
    private String accountingEnabled;
    private String merchantId;
    private String merchantCode;
    private String billerEnabled;
    private List<String> billerIds;
    private List<String> vendors;
    private List<ExternalBillerIds> externalBillerIdsList = new ArrayList<>();
    private int count;
	private List<LookUp> lookupDetailsList = new ArrayList<>();
}
