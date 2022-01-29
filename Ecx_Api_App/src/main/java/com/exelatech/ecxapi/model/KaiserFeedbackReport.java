package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class KaiserFeedbackReport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String reportName;
	private Date generatedTime;
	private String status;
	private Date doneTime;
	private String orderId;

}
