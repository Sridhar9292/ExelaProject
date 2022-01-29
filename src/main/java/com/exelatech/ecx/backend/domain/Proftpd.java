package com.exelatech.ecx.backend.domain;

import java.util.ArrayList;

import com.exelatech.ecx.backend.service.IKey;
import com.exelatech.ecx.backend.service.IProdFTPD;
import com.exelatech.ecx.backend.util.RegexUtl;

public class Proftpd implements IKey, IProdFTPD {
    private String Method;
    private String User;
    private String FileName;
    private String Time;
    private String Size;
    private String Address;
    private String Status;
    private String JobNumber;
    private String AppCode;
    private String Platform;
    private String PlatformStartTime;
    private String Schedule;
    private String DeliveredToTargetPlatform;
    private String LastMovement;
    private ArrayList<String> Source;
    private String SLA;
    private String ElapsedTime;

    // DPS MONITOR
    private String PreProcessingStart;
    private String PreProcessingEnd;
    private String DpsStatus;
    private String PreProcessingElapsed;
    
    // Alternate for _Type search
    private String type;

    //
    private ArrayList<String> AssociatedFiles = new ArrayList<String>();
    
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMethod() {
        return Method;
    }

    public String getElapsedTime() {
        return ElapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.ElapsedTime = elapsedTime;
    }

    public String getSLA() {
        return SLA;
    }

    public void setSLA(String sLA) {
        this.SLA = sLA;
    }

    public ArrayList<String> getSource() {
        return Source;
    }

    public void setSource(ArrayList<String> source) {
        this.Source = source;
    }

    public String getLastMovement() {
        return LastMovement;
    }

    public void setLastMovement(String lastMovement) {
        this.LastMovement = lastMovement;
    }

    public String getDeliveredToTargetPlatform() {
        return DeliveredToTargetPlatform;
    }

    public void setDeliveredToTargetPlatform(String deliveredToTargetPlatform) {
        this.DeliveredToTargetPlatform = deliveredToTargetPlatform;
    }

    public String getSchedule() {
        return Schedule;
    }

    public void setSchedule(String schedule) {
        this.Schedule = schedule;
    }

    public String getPlatformStartTime() {
        return PlatformStartTime;
    }

    public void setPlatformStartTime(String platformStartTime) {
        this.PlatformStartTime = platformStartTime;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        this.Platform = platform;
    }

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String appCode) {
        this.AppCode = appCode;
    }

    public String getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.JobNumber = jobNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        this.Size = size;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        this.FileName = fileName;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        this.User = user;
    }

    public void setMethod(String method) {
        this.Method = method;
    }

    @Override
    public String GetPK() {
        return getFileName();
    }

    public Proftpd() {
    }

    @Override
    public String GetFileNameWithOutExtension() {
        ArrayList<String> _fileNames = RegexUtl.extractDataFromString(this.FileName,
                new String[] { "((^)(.*)(?=\\.\\w{1,3}\\z))" }, new String[] { "" }, new String[] {});
        if (_fileNames.size() > 0)
            return _fileNames.get(0);
        else
            return getFileName();
    }

    @Override
    public ArrayList<String> GetSource() {
        return this.Source == null ? new ArrayList<String>() : this.Source;
    }

    public ArrayList<String> getAssociatedFiles() {
        return AssociatedFiles;
    }

    public void setAssociatedFiles(String associatedFile) {
        if (!this.AssociatedFiles.contains(associatedFile))
            this.AssociatedFiles.add(associatedFile);
    }

    public void setAssociatedFiles(ArrayList<String> associatedFile) {
        for (int i = 0; i < associatedFile.size(); i++) {
            setAssociatedFiles(associatedFile.get(i));
        }
    }

    public String getPreProcessingStart() {
        return PreProcessingStart;
    }

    public void setPreProcessingStart(String preProcessingStart) {
        PreProcessingStart = preProcessingStart;
    }

    public String getPreProcessingEnd() {
        return PreProcessingEnd;
    }

    public void setPreProcessingEnd(String preProcessingEnd) {
        PreProcessingEnd = preProcessingEnd;
    }

    public String getDpsStatus() {
        return DpsStatus;
    }

    public void setDpsStatus(String dpsStatus) {
        DpsStatus = dpsStatus;
    }

    public String getPreProcessingElapsed() {
        return PreProcessingElapsed;
    }

    public void setPreProcessingElapsed(String preProcessingElapsed) {
        PreProcessingElapsed = preProcessingElapsed;
    }

	@Override
	public String toString() {
		return "Proftpd [Method=" + Method + ", User=" + User + ", FileName=" + FileName + ", Time=" + Time + ", Size="
				+ Size + ", Address=" + Address + ", Status=" + Status + ", JobNumber=" + JobNumber + ", AppCode="
				+ AppCode + ", Platform=" + Platform + ", PlatformStartTime=" + PlatformStartTime + ", Schedule="
				+ Schedule + ", DeliveredToTargetPlatform=" + DeliveredToTargetPlatform + ", LastMovement="
				+ LastMovement + ", Source=" + Source + ", SLA=" + SLA + ", ElapsedTime=" + ElapsedTime
				+ ", PreProcessingStart=" + PreProcessingStart + ", PreProcessingEnd=" + PreProcessingEnd
				+ ", DpsStatus=" + DpsStatus + ", PreProcessingElapsed=" + PreProcessingElapsed + ", type=" + type
				+ ", AssociatedFiles=" + AssociatedFiles + "]";
	}
    
    

}