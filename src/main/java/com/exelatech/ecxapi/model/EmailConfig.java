package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class EmailConfig  implements Serializable {
    private static final long serialVersionUID = -7219144024784917837L;
    private String subaccountCode;
    private String subaccountName;
    private String xheader1Name="";
    private String xheader1Value="";
    private String xheader2Name="";
    private String xheader2Value="";
    private String xheader3Name="";
    private String xheader3Value="";
    private String xheader4Name="";
    private String xheader4Value="";
    private String xheader5Name="";
    private String xheader5Value="";
    private Date entryDate;
    private Date modifyDate;
    private String userAbbrev="";
    private String subaccountUser="";
    private String clientCode="";
    private String clientLobCode="";
    private int count;
    private boolean generateDefaultReports;
    private boolean category;
    private List<Category> categoryList = new ArrayList<Category>();
    @JsonIgnore
    private List<Category> categoryValues = new ArrayList<Category>();
}
