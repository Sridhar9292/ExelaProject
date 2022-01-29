package com.exelatech.ecx.backend.model;

import java.util.ArrayList;
import java.util.List;

public class DataFeed{
    private Long feedID;
    private String feedName;
    private String vendorCode;
    private String filePattern;
    private String fileType;
    private String expectedTime;
    private String fileSchedule;
    private String singleMulti;
    private int gracePeriod;
    private String feedDocID;
    private boolean feedExpected;
    private String actualTime;
    private String rowStatus;
    private List<Comment> comments = new ArrayList<>();
    private DataFeedWorkflow dataFeedWorkflow = new DataFeedWorkflow();
    private FeedSummary feedSummary = new FeedSummary();
    private String receivedTime;
    private String importedTime;
    private String exportedTime;
  
    //TODO - Enable this moonitors here.
    //TODO - need a way to identify is sent monitor was a sucess (BillerRemitDataSendComplete).
//    private ExportFeedMonitor exportFeedMonitor = new ExportFeedMonitor();
//    private SendFeedMonitor sendFeedMonitor = new SendFeedMonitor();
//    EventMonitor exportMonitor = new RemitBillerFeedMonitor(ECXEvents.BillerRemitDataImported, ECXEvents.BillerRemitDataExportRequested, ECXEvents.BillerRemitDataExported, ECXEvents.ExtractBillerRemitDataSucceeded, ECXEvents.BillerRemitDataExportError);
//    EventMonitor sendMonitor = new RemitBillerFeedMonitor(ECXEvents.BillerRemitDataImported, ECXEvents.BillerRemitDataExported, ECXEvents.ExportRemitDataSent, ECXEvents.BillerRemitDataExportDeliveryComplete, ECXEvents.BillerRemitDataExportDeliveryError);

    public DataFeed(){
    }

    public DataFeed(ExpectedDatafeed expectedDatafeed){
        this.feedID = expectedDatafeed.getFeedID();
        this.feedName = expectedDatafeed.getFeedName();
        this.vendorCode = expectedDatafeed.getVendorCode();
        this.filePattern = expectedDatafeed.getFilePattern();
        this.fileType = expectedDatafeed.getFileType();
        this.expectedTime = expectedDatafeed.getExpectedTime();
        this.fileSchedule = expectedDatafeed.getFileSchedule();
        this.singleMulti = expectedDatafeed.getSingleMulti();
        this.gracePeriod = expectedDatafeed.getGracePeriod();
        this.feedExpected = true;
    }

    public Long getFeedID() {
        return feedID;
    }

    public void setFeedID(Long feedID) {
        this.feedID = feedID;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getFilePattern() {
        return filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getFileSchedule() {
        return fileSchedule;
    }

    public void setFileSchedule(String fileSchedule) {
        this.fileSchedule = fileSchedule;
    }

    public String getSingleMulti() {
        return singleMulti;
    }

    public void setSingleMulti(String singleMulti) {
        this.singleMulti = singleMulti;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public String getFeedDocID() {
        return feedDocID;
    }

    public void setFeedDocID(String feedDocID) {
        this.feedDocID = feedDocID;
    }

    public boolean getFeedExpected() {
        return feedExpected;
    }

    public void setFeedExpected(boolean feedExpected) {
        this.feedExpected = feedExpected;
    }

    public void setDataFeedWorkflow(DataFeedWorkflow dataFeedWorkflow) {
        this.dataFeedWorkflow = dataFeedWorkflow;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(String rowStatus) {
        this.rowStatus = rowStatus;
    }

    public DataFeedWorkflow getDataFeedWorkflow() {
        return dataFeedWorkflow;
    }

    public FeedSummary getFeedSummary() {
        return feedSummary;
    }

    public void setFeedSummary(FeedSummary feedSummary) {
        this.feedSummary = feedSummary;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComments(Comment comment){
        this.comments.add(comment);
    }

    public void addBillerDetail(BillerDetail billerDetail){
        feedSummary.addBillerDetail(billerDetail);
    }
    

    public String getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}

	public String getImportedTime() {
		return importedTime;
	}

	public void setImportedTime(String importedTime) {
		this.importedTime = importedTime;
	}

	public String getExportedTime() {
		return exportedTime;
	}

	public void setExportedTime(String exportedTime) {
		this.exportedTime = exportedTime;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataFeed dataFeed = (DataFeed) o;

        return feedID.equals(dataFeed.feedID);

    }

    @Override
    public int hashCode() {
        return feedID.hashCode();
    }

    @Override
    public String toString() {
        return "DataFeed{" +
                "feedID=" + feedID +
                ", feedName='" + feedName + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", filePattern='" + filePattern + '\'' +
                ", fileType='" + fileType + '\'' +
                ", expectedTime='" + expectedTime + '\'' +
                ", fileSchedule='" + fileSchedule + '\'' +
                ", singleMulti='" + singleMulti + '\'' +
                ", gracePeriod=" + gracePeriod +
                ", feedDocID='" + feedDocID + '\'' +
                ", feedExpected=" + feedExpected +
                ", actualTime='" + actualTime + '\'' +
                ", rowStatus='" + rowStatus + '\'' +
                ", dataFeedWorkflow=" + dataFeedWorkflow +
                ", feedSummary=" + feedSummary +
                ", receivedTime=" + receivedTime + '\'' +
                ", importedTime=" + importedTime + '\'' +
                ", exportedTime=" + exportedTime + '\'' +
                '}';
    }
}