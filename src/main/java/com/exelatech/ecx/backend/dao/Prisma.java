package com.exelatech.ecx.backend.dao;

import java.util.Date;

public class Prisma {
    private String MajorWO;
    private String JobNumber;
    private String CustNbr;
    private String AppCode;
    private String JobName;
    private Date StartDate;
    private Date DueDate;
    private String Status;
    private Date PrintDate;
    private Date MailDate;

    public String getMajorWO() {
        return MajorWO;
    }

    public void setMajorWO(String majorWO) {
        MajorWO = majorWO;
    }

    public String getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(String jobNumber) {
        JobNumber = jobNumber;
    }

    public String getCustNbr() {
        return CustNbr;
    }

    public void setCustNbr(String custNbr) {
        CustNbr = custNbr;
    }

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getDueDate() {
        return DueDate;
    }

    public void setDueDate(Date dueDate) {
        DueDate = dueDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Date getPrintDate() {
        return PrintDate;
    }

    public void setPrintDate(Date printDate) {
        PrintDate = printDate;
    }

    public Date getMailDate() {
        return MailDate;
    }

    public void setMailDate(Date mailDate) {
        MailDate = mailDate;
    }

    public Prisma(String majorWO, String jobNumber, String custNbr, String appCode, String jobName, Date startDate,
            Date dueDate, String status, Date printDate, Date mailDate) {
        MajorWO = majorWO;
        JobNumber = jobNumber;
        CustNbr = custNbr;
        AppCode = appCode;
        JobName = jobName;
        StartDate = startDate;
        DueDate = dueDate;
        Status = status;
        PrintDate = printDate;
        MailDate = mailDate;
    }

}