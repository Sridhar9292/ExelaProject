package com.exelatech.ecx.backend.domain;

public class RpdsInput {
    private String AppCode;
    private String JobNumber;
    private String InFileName;

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }

    public String getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(String jobNumber) {
        JobNumber = jobNumber;
    }

    public String getInFileName() {
        return InFileName;
    }

    public void setInFileName(String inFileName) {
        InFileName = inFileName;
    }
}