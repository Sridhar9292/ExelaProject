package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class KaiserPaperTicketInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String appCode;
	private String ritsJobNumber;
	private int recipientCopies;
	private String entryDate;
	private String printDate;
	private String insertDate;
	private String mailDate;
	private String status;
	private String name;
	private int sent;
	private int returnR;
	private int incomplete;
	private int recovery;
	private int outsource;
	private int received;
	private int undeliverable;
}
