package com.exelatech.ecxapi.model;

import java.util.Date;

public class ReturnFeeds {
	private String feedDocID;
	private String vendorCode;
	private String clientCode;
	private Date returnDate;
	private String transactionId;
	private String serviceClassCode;
	private long netAmount;
	private String addendumRecord;
	private int addendumSequence;
	private int addendaTypeCode;
	private String returnReasonCode;
	private String addendaDate;
	private String addendumInformation;
	private String vendorRejectCode;
	private String vendorRejectDescription;
	private String downStream;
	private String batchNumber;
	private String traceNumber;
	private String vendorStatus;
	private String transactionCode;
	private String downStreamStatus;
	private int count;
	private int errorCount;
	private int rejectCount;
	private int acceptCount;
	private String feedDocAuthor;
	private String feedDocType;

	public String getFeedDocID() {
		return feedDocID;
	}

	public void setFeedDocID(String feedDocID) {
		this.feedDocID = feedDocID;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getServiceClassCode() {
		return serviceClassCode;
	}

	public void setServiceClassCode(String serviceClassCode) {
		this.serviceClassCode = serviceClassCode;
	}

	public long getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(long netAmount) {
		this.netAmount = netAmount;
	}

	public String getAddendumRecord() {
		return addendumRecord;
	}

	public void setAddendumRecord(String addendumRecord) {
		this.addendumRecord = addendumRecord;
	}

	public int getAddendumSequence() {
		return addendumSequence;
	}

	public void setAddendumSequence(int addendumSequence) {
		this.addendumSequence = addendumSequence;
	}

	public int getAddendaTypeCode() {
		return addendaTypeCode;
	}

	public void setAddendaTypeCode(int addendaTypeCode) {
		this.addendaTypeCode = addendaTypeCode;
	}

	public String getReturnReasonCode() {
		return returnReasonCode;
	}

	public void setReturnReasonCode(String returnReasonCode) {
		this.returnReasonCode = returnReasonCode;
	}

	public String getAddendaDate() {
		return addendaDate;
	}

	public void setAddendaDate(String addendaDate) {
		this.addendaDate = addendaDate;
	}

	public String getAddendumInformation() {
		return addendumInformation;
	}

	public void setAddendumInformation(String addendumInformation) {
		this.addendumInformation = addendumInformation;
	}

	public String getVendorRejectCode() {
		return vendorRejectCode;
	}

	public void setVendorRejectCode(String vendorRejectCode) {
		this.vendorRejectCode = vendorRejectCode;
	}

	public String getVendorRejectDescription() {
		return vendorRejectDescription;
	}

	public void setVendorRejectDescription(String vendorRejectDescription) {
		this.vendorRejectDescription = vendorRejectDescription;
	}

	public String getDownStream() {
		return downStream;
	}

	public void setDownStream(String downStream) {
		this.downStream = downStream;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getTraceNumber() {
		return traceNumber;
	}

	public void setTraceNumber(String traceNumber) {
		this.traceNumber = traceNumber;
	}

	public String getVendorStatus() {
		return vendorStatus;
	}

	public void setVendorStatus(String vendorStatus) {
		this.vendorStatus = vendorStatus;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getDownStreamStatus() {
		return downStreamStatus;
	}

	public void setDownStreamStatus(String downStreamStatus) {
		this.downStreamStatus = downStreamStatus;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getRejectCount() {
		return rejectCount;
	}

	public void setRejectCount(int rejectCount) {
		this.rejectCount = rejectCount;
	}

	public int getAcceptCount() {
		return acceptCount;
	}

	public void setAcceptCount(int acceptCount) {
		this.acceptCount = acceptCount;
	}

	public String getFeedDocAuthor() {
		return feedDocAuthor;
	}

	public void setFeedDocAuthor(String feedDocAuthor) {
		this.feedDocAuthor = feedDocAuthor;
	}

	public String getFeedDocType() {
		return feedDocType;
	}

	public void setFeedDocType(String feedDocType) {
		this.feedDocType = feedDocType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ReturnFeeds that = (ReturnFeeds) o;

		if (!feedDocID.equals(that.feedDocID))
			return false;
		return transactionId.equals(that.transactionId);

	}

	@Override
	public int hashCode() {
		int result = feedDocID.hashCode();
		result = 31 * result + transactionId.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "ReturnFeeds{" + "feedDocID='" + feedDocID + '\'' + ", vendorCode='" + vendorCode + '\''
				+ ", clientCode='" + clientCode + '\'' + ", returnDate=" + returnDate + ", transactionId='"
				+ transactionId + '\'' + ", serviceClassCode='" + serviceClassCode + '\'' + ", netAmount=" + netAmount
				+ ", addendumRecord='" + addendumRecord + '\'' + ", addendumSequence=" + addendumSequence
				+ ", addendaTypeCode=" + addendaTypeCode + ", returnReasonCode='" + returnReasonCode + '\''
				+ ", addendaDate='" + addendaDate + '\'' + ", addendumInformation='" + addendumInformation + '\''
				+ ", vendorRejectCode='" + vendorRejectCode + '\'' + ", vendorRejectDescription='"
				+ vendorRejectDescription + '\'' + ", downStream='" + downStream + '\'' + ", batchNumber='"
				+ batchNumber + '\'' + ", traceNumber='" + traceNumber + '\'' + ", vendorStatus='" + vendorStatus + '\''
				+ ", transactionCode='" + transactionCode + '\'' + ", downStreamStatus='" + downStreamStatus + '\''
				+ ", count=" + count + ", errorCount=" + errorCount + ", rejectCount=" + rejectCount + ", acceptCount="
				+ acceptCount + ", feedDocAuthor='" + feedDocAuthor + '\'' + ", feedDocType='" + feedDocType + '\''
				+ '}';
	}
}
