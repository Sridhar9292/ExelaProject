package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class EmailJobDetail implements Serializable {
	private static final long serialVersionUID = -1554536971947315697L;
	private String emailUUID;
    private String feedDocID;
    private String subaccountCode;
    private String subaccountUniqueID;
    private String emailCategory;
    private String jobNumber;
    private String accoutNumber;
    private String emailFrom;
    private String emailTo;
    private String emailCc;
    private String emailBcc;
    private String emailReplyTo;
    private String emailSubject;
    private String emailStatus;
    private Date emailSubmittedOn;
	private Date processedOn;
    private Date deliveredOn;
    private Date deferredOn;
    private Date openedOn;
    private Date bouncedOn;
    private Date droppedOn;
	private Date lastOpenedOn;
	private Date entryDate;
}
