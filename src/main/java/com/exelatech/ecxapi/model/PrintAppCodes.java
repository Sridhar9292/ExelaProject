package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PrintAppCodes implements Serializable {
	private static final long serialVersionUID = 1L;
	private String rowid;
    private String appCodeType;
    private String appCode;
    private Date effectiveDate;
    private int count;
}
