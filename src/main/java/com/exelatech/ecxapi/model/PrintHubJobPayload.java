package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class PrintHubJobPayload implements Serializable {
	private static final long serialVersionUID = 1L;
	private String contID;
	private String instanceID;
	private String payloadName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss:SSS")
	private Date payloadDate;
	private String payloadSize;
	private String payloadTgt;
	private String payStatus;
	@JsonIgnore
	private Date entryDate;
	@JsonIgnore
	private Date modifiedDate;

}
