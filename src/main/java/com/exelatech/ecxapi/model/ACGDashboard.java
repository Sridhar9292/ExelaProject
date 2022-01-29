package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ACGDashboard implements Serializable {
	private static final long serialVersionUID = 1L;

	private String msgId;
	private String startTime;
	private String endTime;
	private String status;
	private String msgType;
	private String error;
}
