package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class ExternalBillerIds implements Serializable {
    private static final long serialVersionUID = 7652354049839318495L;
    private String clientCode;
    private String clientLobCode;
    private String vendorCode;
    private String billerId;
    private String enabled="FALSE";
    private boolean added;
    private String vendorDisplayName;
    private String returnsEnabled="N";
    private String returnsConfEnabled="N";
    private String stopFileEnabled="N";
    private String stopFileRetcode;
    private String posFileEnabled="N";
    private String posFileRetcode;
    private String vendorChannelId="";
    private Date goLiveDate;
}
