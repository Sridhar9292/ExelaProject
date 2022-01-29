package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class EmailReportConfig implements Serializable {
	private static final long serialVersionUID = -3653703204307726332L;
    private String subaccountCode;
    private String emailReportType;
    private String emailReportNamingPattern="[PREFIX].t2.email.[EMAIL_REPORT_TYPE].[SUBACCOUNT_CODE].[YYYYMMDDHHMMSS].json";
    private String emailReportPath="/home2/ecx/endpoints/transcentra/ebox/t2/out/";
    private boolean generateReportIfEmpty=true;
  /*  private String emailReportNamingPattern;
    private String emailReportPath;
    private boolean generateReportIfEmpty;*/
    private boolean exportEnabled;
    private Date lastImportedOn;
    private Date lastExportedOn;
    private Date entryDate;
    private Date modifyDate;
    private String userAbbrev;
    private int count;

    
	/*
	 * public EmailReportConfig(String subaccountCode, String emailReportType,
	 * boolean exportEnabled){ this.subaccountCode = subaccountCode;
	 * this.emailReportType = emailReportType; this.exportEnabled = exportEnabled; }
	 */
}
