package com.exelatech.ecx.backend.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

/**
 * Created by VenkataDurgavarjhula on 12/7/2015.
 */
//public class VendorConfig extends org.appfuse.model.BaseObject implements Serializable {

public class VendorConfig implements Serializable {
	
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String vendorShortCode;
    private String vendorName;
    private String logPrefix;
    private String logSuffix;
    private String nagiosService;
    private boolean checkHoliday;
    private boolean insertEmptyBatches;
    private boolean requireAllBillers;
    private String fileOutName;
    private String fileTmpName;
    private String vendorOrigin;
    private String vendorCode;
    private Date entryDate;
    private Date modifyDate;
    private String userAbbrev;
    private String holidayName;

    public String getVendorShortCode() {
        return vendorShortCode;
    }

    public void setVendorShortCode(String vendorShortCode) {
        this.vendorShortCode = vendorShortCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getLogPrefix() {
        return logPrefix;
    }

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public String getLogSuffix() {
        return logSuffix;
    }

    public void setLogSuffix(String logSuffix) {
        this.logSuffix = logSuffix;
    }

    public String getNagiosService() {
        return nagiosService;
    }

    public void setNagiosService(String nagiosService) {
        this.nagiosService = nagiosService;
    }

    public boolean isCheckHoliday() {
        return checkHoliday;
    }

    public void setCheckHoliday(boolean checkHoliday) {
        this.checkHoliday = checkHoliday;
    }

    public boolean isInsertEmptyBatches() {
        return insertEmptyBatches;
    }

    public void setInsertEmptyBatches(boolean insertEmptyBatches) {
        this.insertEmptyBatches = insertEmptyBatches;
    }

    public boolean isRequireAllBillers() {
        return requireAllBillers;
    }

    public void setRequireAllBillers(boolean requireAllBillers) {
        this.requireAllBillers = requireAllBillers;
    }

    public String getFileOutName() {
        return fileOutName;
    }

    public void setFileOutName(String fileOutName) {
        this.fileOutName = fileOutName;
    }

    public String getFileTmpName() {
        return fileTmpName;
    }

    public void setFileTmpName(String fileTmpName) {
        this.fileTmpName = fileTmpName;
    }

    public String getVendorOrigin() {
        return vendorOrigin;
    }

    public void setVendorOrigin(String vendorOrigin) {
        this.vendorOrigin = vendorOrigin;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getUserAbbrev() {
        return userAbbrev;
    }

    public void setUserAbbrev(String userAbbrev) {
        this.userAbbrev = userAbbrev;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VendorConfig that = (VendorConfig) o;

        return vendorCode.equals(that.vendorCode);

    }

    @Override
    public int hashCode() {
        return vendorCode.hashCode();
    }

    @Override
    public String toString() {
        return "VendorConfig{" +
                "vendorShortCode='" + vendorShortCode + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", logPrefix='" + logPrefix + '\'' +
                ", logSuffix='" + logSuffix + '\'' +
                ", nagiosService='" + nagiosService + '\'' +
                ", checkHoliday=" + checkHoliday +
                ", insertEmptyBatches=" + insertEmptyBatches +
                ", requireAllBillers=" + requireAllBillers +
                ", fileOutName='" + fileOutName + '\'' +
                ", fileTmpName='" + fileTmpName + '\'' +
                ", vendorOrigin='" + vendorOrigin + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", entryDate=" + entryDate +
                ", modifyDate=" + modifyDate +
                ", userAbbrev='" + userAbbrev + '\'' +
                ", holidayName='" + holidayName + '\'' +
                '}';
    }
}
