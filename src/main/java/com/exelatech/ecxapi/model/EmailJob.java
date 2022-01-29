package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class EmailJob implements Serializable {
	private static final long serialVersionUID = -6192275221441480123L;
	private String jobNumber;
	private String subaccountCode;
	private String feedDocID;
	private Date feedDttm;
	private int processed;
	private int deferred;
	private int delivered;
	private int open;
	private int bounced;
	private int dropped;
	private int received;
	private int queued;
	private int submitted;
	private String payerCode;
	private int errors;
}
