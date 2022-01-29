package com.exelatech.ecx.backend.model;

import java.io.Serializable;
import java.util.Map;

public class ExpectedDatafeed extends BaseObject implements Serializable {
    private Long feedID;
    private String feedName;
    private String vendorCode;
    private String filePattern;
    private String fileType;
    private String expectedTime;
    private String fileSchedule;
    private String singleMulti;
    private Integer gracePeriod;

    public Long getFeedID() {
        return feedID;
    }

    public void setFeedID(Long feedID) {
        this.feedID = feedID;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getFilePattern() {
        return filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getFileSchedule() {
        return fileSchedule;
    }

    public void setFileSchedule(String fileSchedule) {
        this.fileSchedule = fileSchedule;
    }

    public String getSingleMulti() {
        return singleMulti;
    }

    public void setSingleMulti(String singleMulti) {
        this.singleMulti = singleMulti;
    }

    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
   
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpectedDatafeed that = (ExpectedDatafeed) o;

        return feedID == that.feedID;

    }

    @Override
    public int hashCode() {
        return feedID.intValue();
    }

    @Override
    public String toString() {
        return "ExpectedDatafeed{" +
                "feedID=" + feedID +
                ", feedName='" + feedName + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", filePattern='" + filePattern + '\'' +
                ", fileType='" + fileType + '\'' +
                ", expectedTime='" + expectedTime + '\'' +
                ", fileSchedule='" + fileSchedule + '\'' +
                ", singleMulti='" + singleMulti + '\'' +
                ", gracePeriod=" + gracePeriod +
                '}';
    }
}