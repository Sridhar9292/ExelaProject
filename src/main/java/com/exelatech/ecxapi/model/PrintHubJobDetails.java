package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class PrintHubJobDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	private String groupID;
	private String contID;
	private String pipeLineID;
	private String fileName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss:SSS")
	private Date fileDate;
	private String fileSize;
	private String srcSiteID;
	private String tgtSiteID;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss:SSS")
	private Date startTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss:SSS")
	private Date endTime;
	private String contStatus;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<PrintHubJobPayload> detail;
	@JsonIgnore
	private Date entryDate;
	@JsonIgnore
	private Date modifiedDate;
	private String elapsedTime;
	private String errDesc;

}
