package com.exelatech.ecx.backend.domain;

public class T2App {
    private String JobNumber;
    private String AppCode;
    private String dpsAppCode;
    private String Platform;
    private String PlatformStartTime;
    private String FileName;
    private String Schedule;

    public String getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(String jobNumber) {
        JobNumber = jobNumber;
    }

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }

    public String getDpsAppCode() {
        return dpsAppCode;
    }

    public void setDpsAppCode(String dpsAppCode) {
        this.dpsAppCode = dpsAppCode;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }

    public String getPlatformStartTime() {
        return PlatformStartTime;
    }

    public void setPlatformStartTime(String platformStartTime) {
        PlatformStartTime = platformStartTime;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getSchedule() {
        return Schedule;
    }

    public void setSchedule(String schedule) {
        Schedule = schedule;
    }

    public T2App(String jobNumber, String appCode, String dpsAppCode, String platform, String platformStartTime,
            String fileName, String schedule) {
        JobNumber = jobNumber;
        AppCode = appCode;
        this.dpsAppCode = dpsAppCode;
        Platform = platform;
        PlatformStartTime = platformStartTime;
        FileName = fileName;
        Schedule = schedule;
    }

    public T2App() {
    }
}