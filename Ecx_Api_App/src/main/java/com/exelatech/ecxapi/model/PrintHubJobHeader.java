package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class PrintHubJobHeader implements Serializable {
	private static final long serialVersionUID = 1L;
	private String groupID;
	private String solutionID;
	private String appCode;
	private String jobNumber;
	private String destination;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss:SSS")
	private Date receivedTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss:SSS")
	private Date deliveryTime;
	private String elapsedTime;
	private String status;
	@JsonIgnore
	private Date entryDate;
	@JsonIgnore
	private Date modifiedDate;
	private String src;
	private String errDesc;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<PrintHubJobDetails> summary;
}
