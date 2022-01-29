package com.exelatech.ecx.backend.dao;

import java.util.Date;

import javax.persistence.*;

import com.exelatech.ecx.backend.service.IKey;

@Entity
@Table(name = "dps2.job")
@NamedQueries({
        @NamedQuery(name = "DpsJob.getJobsByTime", query = "SELECT c FROM DpsJob c WHERE c.DpsStartTime >= :dps_start_time") })
public class DpsJob implements IKey {

    @Id
    @Column(name = "id")
    private int JobId;

    @Column(name = "print_job_number")
    private String JobNumber;

    @Column(name = "dps_start_time")
    private Date DpsStartTime;

    @Column(name = "dps_end_time")
    private Date DpsEndTime;

    @Column(name = "status_id")
    private String Status;

    @Column(name = "rits_app_code")
    private String RitsAppCode;

    @Transient
    private String Time; // from proftpd

    @Transient
    private String PlatForm; // from proftpd

    @Transient
    private String AppCode; // from proftpd

    @Transient
    private String DeliveryStatus; // fron sender receiver

    @Transient
    private String PreProcessingElapsed;

    @Transient
    private String PrintStatus;

    @Transient
    private String InsertionStatus;

    @Transient
    private String MailDate;

    @Transient
    private String MajorWO; 
    
    // Alternate for _Type search
    @Transient
    private String type;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getJobId() {
        return JobId;
    }

    public void setJobId(int jobId) {
        JobId = jobId;
    }

    public String getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(String jobNumber) {
        JobNumber = jobNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPlatForm() {
        return PlatForm;
    }

    public void setPlatForm(String platFormId) {
        PlatForm = platFormId;
    }

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }

    public String getPreProcessingElapsed() {
        return PreProcessingElapsed;
    }

    public void setPreProcessingElapsed(String preProcessingElapsed) {
        PreProcessingElapsed = preProcessingElapsed;
    }

    public DpsJob() {
    }

    @Override
    public String GetPK() {
        return getJobNumber();
    }

    public Date getDpsStartTime() {
        return DpsStartTime;
    }

    public void setDpsStartTime(Date dpsStartTime) {
        setTime(String.valueOf(dpsStartTime.getTime()));
        DpsStartTime = dpsStartTime;
    }

    public Date getDpsEndTime() {
        return DpsEndTime;
    }

    public void setDpsEndTime(Date dpsEndTime) {
        DpsEndTime = dpsEndTime;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = String.valueOf(getDpsStartTime() != null ? getDpsStartTime().getTime(): "");
    }

    public String getDeliveryStatus() {
        return DeliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        DeliveryStatus = deliveryStatus;
    }

    public String getPrintStatus() {
        return PrintStatus;
    }

    public void setPrintStatus(String printStatus) {
        PrintStatus = printStatus;
    }

    public String getInsertionStatus() {
        return InsertionStatus;
    }

    public void setInsertionStatus(String insertionStatus) {
        InsertionStatus = insertionStatus;
    }

    public String getMailDate() {
        return MailDate;
    }

    public void setMailDate(String mailDate) {
        MailDate = mailDate;
    }

    public String getRitsAppCode() {
        return RitsAppCode;
    }

    public void setRitsAppCode(String ritsAppCode) {
        RitsAppCode = ritsAppCode;
    }

    public String getMajorWO() {
        return MajorWO;
    }

    public void setMajorWO(String majorWO) {
        MajorWO = majorWO;
    }

	@Override
	public String toString() {
		return "DpsJob [JobId=" + JobId + ", JobNumber=" + JobNumber + ", DpsStartTime=" + DpsStartTime
				+ ", DpsEndTime=" + DpsEndTime + ", Status=" + Status + ", RitsAppCode=" + RitsAppCode + ", Time="
				+ Time + ", PlatForm=" + PlatForm + ", AppCode=" + AppCode + ", DeliveryStatus=" + DeliveryStatus
				+ ", PreProcessingElapsed=" + PreProcessingElapsed + ", PrintStatus=" + PrintStatus
				+ ", InsertionStatus=" + InsertionStatus + ", MailDate=" + MailDate + ", MajorWO=" + MajorWO + ", type="
				+ type + "]";
	}
    
    

}