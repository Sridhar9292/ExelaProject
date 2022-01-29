package com.exelatech.ecxapi.model;

import java.util.ArrayList;
import java.util.List;

public class ReturnDataFeed {
	private Long feedID;
	private String vendorCode;
	private String fileType;
	private String feedDocID;
	private String actualTime;
	private String from;
	private Step receiveStep = new Step();
	private Step importStep = new Step();
	private Step exportStep = new Step();
	private Step sendStep = new Step();
	private String to;
	private int returns;
	private int accepted;
	private int rejected;

	private List<ReturnFeeds> summary = new ArrayList<>();
	private List<Comment> comments = new ArrayList<>();
	private boolean isCommentsPresent = false;
	
	public boolean isCommentsPresent() {
		return isCommentsPresent;
	}

	public void setCommentsPresent(boolean isCommentsPresent) {
		this.isCommentsPresent = isCommentsPresent;
	}

	public Long getFeedID() {
		return feedID;
	}

	public void setFeedID(Long feedID) {
		this.feedID = feedID;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFeedDocID() {
		return feedDocID;
	}

	public void setFeedDocID(String feedDocID) {
		this.feedDocID = feedDocID;
	}

	public String getActualTime() {
		return actualTime;
	}

	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Step getReceiveStep() {
		return receiveStep;
	}

	public void setReceiveStep(Step receiveStep) {
		this.receiveStep = receiveStep;
	}

	public Step getImportStep() {
		return importStep;
	}

	public void setImportStep(Step importStep) {
		this.importStep = importStep;
	}

	public Step getExportStep() {
		return exportStep;
	}

	public void setExportStep(Step exportStep) {
		this.exportStep = exportStep;
	}

	public Step getSendStep() {
		return sendStep;
	}

	public void setSendStep(Step sendStep) {
		this.sendStep = sendStep;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getReturns() {
		return returns;
	}

	public void setReturns(int returns) {
		this.returns = returns;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	public int getRejected() {
		return rejected;
	}

	public void setRejected(int rejected) {
		this.rejected = rejected;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<ReturnFeeds> getSummary() {
		return summary;
	}

	public void setSummary(List<ReturnFeeds> summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "ReturnDataFeed{" + "feedID=" + feedID + ", vendorCode='" + vendorCode + '\'' + ", fileType='" + fileType
				+ '\'' + ", feedDocID='" + feedDocID + '\'' + ", actualTime='" + actualTime + '\'' + ", from='" + from
				+ '\'' + ", receiveStep=" + receiveStep + ", importStep=" + importStep + ", exportStep=" + exportStep
				+ ", sendStep=" + sendStep + ", to='" + to + '\'' + ", returns=" + returns + ", accepted=" + accepted
				+ ", rejected=" + rejected + ", summary=" + summary + ", comments=" + comments + '}';
	}
}
