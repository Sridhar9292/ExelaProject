package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExpectedDatafeed implements Serializable {
    private static final long serialVersionUID = 1822573745253621697L;
    private Long feedID;
    private String feedName;
    private String vendorCode;
    private String filePattern;
    private String fileType;
    private String expectedTime;
    private String fileSchedule;
    private String singleMulti;
    private Integer gracePeriod;
}