package com.exelatech.ecxapi.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BillerDetail{
    private String clientCode;
    private String clientLobCode;
    private String billerName;
    private long paymentCount;
    private BigDecimal paymentAmount = new BigDecimal(BigInteger.ZERO).setScale(2);
    private long reversalCount;
    private BigDecimal reversalAmount = new BigDecimal(BigInteger.ZERO).setScale(2);
    private BigDecimal netSettledAmount = new BigDecimal(BigInteger.ZERO).setScale(2);
    private String State;
    private String delivery;
    private String feedDocID;
    private String billerCode;
    private String vendor;
    
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

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public long getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(long paymentCount) {
        this.paymentCount = paymentCount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public long getReversalCount() {
        return reversalCount;
    }

    public void setReversalCount(long reversalCount) {
        this.reversalCount = reversalCount;
    }

    public BigDecimal getReversalAmount() {
        return reversalAmount;
    }

    public void setReversalAmount(BigDecimal reversalAmount) {
        this.reversalAmount = reversalAmount;
    }

    public BigDecimal getNetSettledAmount() {
        return netSettledAmount;
    }

    public void setNetSettledAmount(BigDecimal netSettledAmount) {
        this.netSettledAmount = netSettledAmount;
    }

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getFeedDocID() {
		return feedDocID;
	}

	public void setFeedDocID(String feedDocID) {
		this.feedDocID = feedDocID;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	
    
}
