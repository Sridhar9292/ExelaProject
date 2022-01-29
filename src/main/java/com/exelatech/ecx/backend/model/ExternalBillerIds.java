package com.exelatech.ecx.backend.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ExternalBillerIds implements Serializable {
    private String clientCode;
    private String clientLobCode;
    private String vendorCode;
    private String billerId;
    private String enabled="FALSE";
    private boolean added;
    private String vendorDisplayName;
    private String returnsEnabled="N";
    private String returnsConfEnabled="N";
    private String stopFileEnabled="N";
    private String stopFileRetcode;
    private String posFileEnabled="N";
    private String posFileRetcode;
    private String vendorChannelId="";
    private Date goLiveDate;
	
	/*private List<LookUp> consoleNameList = new ArrayList<>();

    public List<LookUp> getConsoleNameList() {
		return consoleNameList;
	}

	public void setConsoleNameList(List<LookUp> consoleNameList) {
		this.consoleNameList = consoleNameList;
	}*/

	public Date getGoLiveDate() {
		return goLiveDate;
	}

	public void setGoLiveDate(Date goLiveDate) {
		this.goLiveDate = goLiveDate;
	}

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientLobCode() {
        return clientLobCode;
    }

    public void setClientLobCode(String clientLobCode) {
        this.clientLobCode = clientLobCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getBillerId() {
        return billerId;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public String getVendorDisplayName() {
        return vendorDisplayName;
    }

    public void setVendorDisplayName(String vendorDisplayName) {
        this.vendorDisplayName = vendorDisplayName;
    }

    public String getReturnsEnabled() {
        return returnsEnabled;
    }

    public void setReturnsEnabled(String returnsEnabled) {
        this.returnsEnabled = returnsEnabled;
    }

    public String getReturnsConfEnabled() {
        return returnsConfEnabled;
    }

    public void setReturnsConfEnabled(String returnsConfEnabled) {
        this.returnsConfEnabled = returnsConfEnabled;
    }

    public String getStopFileEnabled() {
        return stopFileEnabled;
    }

    public void setStopFileEnabled(String stopFileEnabled) {
        this.stopFileEnabled = stopFileEnabled;
    }

    public String getVendorChannelId() {
        return vendorChannelId;
    }

    public void setVendorChannelId(String vendorChannelId) {
        this.vendorChannelId = vendorChannelId;
    }

    public String getStopFileRetcode() {
        return stopFileRetcode;
    }

    public void setStopFileRetcode(String stopFileRetcode) {
        this.stopFileRetcode = stopFileRetcode;
    }

    public String getPosFileEnabled() {
        return posFileEnabled;
    }

    public void setPosFileEnabled(String posFileEnabled) {
        this.posFileEnabled = posFileEnabled;
    }

    public String getPosFileRetcode() {
        return posFileRetcode;
    }

    public void setPosFileRetcode(String posFileRetcode) {
        this.posFileRetcode = posFileRetcode;
    }

    @Override
    public String toString() {
        return "ExternalBillerIds{" +
                "clientCode='" + clientCode + '\'' +
                ", clientLobCode='" + clientLobCode + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", billerId='" + billerId + '\'' +
                ", enabled='" + enabled + '\'' +
                ", added=" + added +
                ", vendorDisplayName='" + vendorDisplayName + '\'' +
                ", returnsEnabled='" + returnsEnabled + '\'' +
                ", returnsConfEnabled='" + returnsConfEnabled + '\'' +
                ", stopFileEnabled='" + stopFileEnabled + '\'' +
                ", stopFileRetcode='" + stopFileRetcode + '\'' +
                ", posFileEnabled='" + posFileEnabled + '\'' +
                ", posFileRetcode='" + posFileRetcode + '\'' +
                ", vendorChannelId='" + vendorChannelId + '\'' +
                ", goLiveDate='" + goLiveDate +'\''+
               // ", consoleNameList='"+ consoleNameList + '\''+
                '}';
    }
}
