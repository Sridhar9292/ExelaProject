package com.exelatech.ecxapi.model;

import java.math.BigDecimal;
import java.math.BigInteger;

class BillerTotal{
    private long totalPaymentCount = 0;
    private BigDecimal totalPaymentAmount = new BigDecimal(BigInteger.ZERO).setScale(2);
    private long totalReversalCount = 0;
    private BigDecimal totalReversalAmount = new BigDecimal(BigInteger.ZERO).setScale(2);
    private BigDecimal totalNetSettledAmount = new BigDecimal(BigInteger.ZERO).setScale(2);

    public long getTotalPaymentCount() {
        return totalPaymentCount;
    }

    public void setTotalPaymentCount(long totalPaymentCount) {
        this.totalPaymentCount = totalPaymentCount;
    }

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public long getTotalReversalCount() {
        return totalReversalCount;
    }

    public void setTotalReversalCount(long totalReversalCount) {
        this.totalReversalCount = totalReversalCount;
    }

    public BigDecimal getTotalReversalAmount() {
        return totalReversalAmount;
    }

    public void setTotalReversalAmount(BigDecimal totalReversalAmount) {
        this.totalReversalAmount = totalReversalAmount;
    }

    public BigDecimal getTotalNetSettledAmount() {
        return totalNetSettledAmount;
    }

    public void setTotalNetSettledAmount(BigDecimal totalNetSettledAmount) {
        this.totalNetSettledAmount = totalNetSettledAmount;
    }

    public void addBillerDetail(BillerDetail billerDetail){
        totalPaymentCount+= billerDetail.getPaymentCount();
        totalPaymentAmount = totalPaymentAmount.add(billerDetail.getPaymentAmount());
        totalReversalCount+= billerDetail.getReversalCount();
        totalReversalAmount = totalReversalAmount.add(billerDetail.getReversalAmount());
        totalNetSettledAmount = totalNetSettledAmount.add(billerDetail.getNetSettledAmount());
    }

	@Override
	public String toString() {
		return "BillerTotal [totalPaymentCount=" + totalPaymentCount + ", totalPaymentAmount=" + totalPaymentAmount
				+ ", totalReversalCount=" + totalReversalCount + ", totalReversalAmount=" + totalReversalAmount
				+ ", totalNetSettledAmount=" + totalNetSettledAmount + "]";
	}
    
    
}