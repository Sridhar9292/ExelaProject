package com.exelatech.ecx.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Biller extends BaseObject implements Serializable {
    private String id;
    private String clientCode;
    private String clientName;
    private String clientLobCode;
    private String clientLobName;
    private String delivery="RPS";
    private String billerName;
    private String billerAliasName;
    private String billerCode;
    private String billerSiteCode;
    private String accountingEnabled;
    private String merchantId;
    private String merchantCode;
    private String billerEnabled;
    private List<String> billerIds;
    private List<String> vendors;
    private List<ExternalBillerIds> externalBillerIdsList = new ArrayList<>();
    private int count;
	private List<LookUp> lookupDetailsList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLobCode() {
        return clientLobCode;
    }

    public void setClientLobCode(String clientLobCode) {
        this.clientLobCode = clientLobCode;
    }

    public String getClientLobName() {
        return clientLobName;
    }

    public void setClientLobName(String clientLobName) {
        this.clientLobName = clientLobName;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public String getBillerAliasName() {
        return billerAliasName;
    }

    public void setBillerAliasName(String billerAliasName) {
        this.billerAliasName = billerAliasName;
    }

    public String getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getBillerSiteCode() {
        return billerSiteCode;
    }

    public void setBillerSiteCode(String billerSiteCode) {
        this.billerSiteCode = billerSiteCode;
    }

    public List<String> getBillerIds() {
        return billerIds;
    }

    public void setBillerIds(List<String> billerIds) {
        this.billerIds = billerIds;
    }

    public List<String> getVendors() {
        return vendors;
    }

    public void setVendors(List<String> vendors) {
        this.vendors = vendors;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ExternalBillerIds> getExternalBillerIdsList() {
        return externalBillerIdsList;
    }

    public void setExternalBillerIdsList(List<ExternalBillerIds> externalBillerIds) {
        this.externalBillerIdsList = externalBillerIds;
    }

    public void addExternalBillerIds(ExternalBillerIds externalBillerIds){
        externalBillerIdsList.add(externalBillerIds);
    }

    public String getAccountingEnabled() {
        return accountingEnabled;
    }

    public void setAccountingEnabled(String accountingEnabled) {
        this.accountingEnabled = accountingEnabled;
    }
    public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
    
	
    public String getBillerEnabled() {
		return billerEnabled;
	}

	public void setBillerEnabled(String billerEnabled) {
		this.billerEnabled = billerEnabled;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Biller)) return false;

        Biller biller = (Biller) o;

        if (!getClientCode().equals(biller.getClientCode())) return false;
        if (getClientName() != null ? !getClientName().equals(biller.getClientName()) : biller.getClientName() != null)
            return false;
        if (!getClientLobCode().equals(biller.getClientLobCode())) return false;
        if (getClientLobName() != null ? !getClientLobName().equals(biller.getClientLobName()) : biller.getClientLobName() != null)
            return false;
        if (getDelivery() != null ? !getDelivery().equals(biller.getDelivery()) : biller.getDelivery() != null)
            return false;
        if (!getBillerName().equals(biller.getBillerName())) return false;
        if (getBillerAliasName() != null ? !getBillerAliasName().equals(biller.getBillerAliasName()) : biller.getBillerAliasName() != null)
            return false;
        if (getBillerCode() != null ? !getBillerCode().equals(biller.getBillerCode()) : biller.getBillerCode() != null)
            return false;
        if (getBillerSiteCode() != null ? !getBillerSiteCode().equals(biller.getBillerSiteCode()) : biller.getBillerSiteCode() != null)
            return false;
        return !(getVendors() != null ? !getVendors().equals(biller.getVendors()) : biller.getVendors() != null);

    }

    @Override
    public int hashCode() {
        int result = getClientCode().hashCode();
        result = 31 * result + (getClientName() != null ? getClientName().hashCode() : 0);
        result = 31 * result + getClientLobCode().hashCode();
        result = 31 * result + (getClientLobName() != null ? getClientLobName().hashCode() : 0);
        result = 31 * result + (getDelivery() != null ? getDelivery().hashCode() : 0);
        result = 31 * result + getBillerName().hashCode();
        result = 31 * result + (getBillerAliasName() != null ? getBillerAliasName().hashCode() : 0);
        result = 31 * result + (getBillerCode() != null ? getBillerCode().hashCode() : 0);
        result = 31 * result + (getBillerSiteCode() != null ? getBillerSiteCode().hashCode() : 0);
        result = 31 * result + (getVendors() != null ? getVendors().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Biller{" +
                "id='" + id + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientLobCode='" + clientLobCode + '\'' +
                ", clientLobName='" + clientLobName + '\'' +
                ", delivery='" + delivery + '\'' +
                ", billerName='" + billerName + '\'' +
                ", billerAliasName='" + billerAliasName + '\'' +
                ", billerCode='" + billerCode + '\'' +
                ", billerSiteCode='" + billerSiteCode + '\'' +
                ", accountingEnabled='" + accountingEnabled + '\'' +
                ", billerIds=" + billerIds +
                ", vendors=" + vendors +
                ", externalBillerIdsList=" + externalBillerIdsList +
                ", count=" + count +
                ", merchantId='" + merchantId + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                '}';
    }
    
	public List<LookUp> getLookupDetailsList() {
		return lookupDetailsList;
	}

	public void setLookupDetailsList(List<LookUp> lookupDetailsList) {
		this.lookupDetailsList = lookupDetailsList;
	}
}
