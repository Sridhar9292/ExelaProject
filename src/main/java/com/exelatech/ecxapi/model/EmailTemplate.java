package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmailTemplate implements Serializable {
    private static final long serialVersionUID = 1l;
    private String templateId;
    private String templateName;
    private String eventName;
    private String emailFrom;
    private String emailTo;
    private String emailCc;
    private String emailBcc;
    private String emailSubject;
    private String emailBody;
    private boolean fileAsBody;
    private boolean enabled;
}
