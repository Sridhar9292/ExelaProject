package com.exelatech.ecx.backend.model;

import java.io.Serializable;

/**
 * Created by VenkataDurgavarjhula on 11/19/2015.
 */
public class EmailTemplate extends BaseObject implements Serializable {
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

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailCc() {
        return emailCc;
    }

    public void setEmailCc(String emailCc) {
        this.emailCc = emailCc;
    }

    public String getEmailBcc() {
        return emailBcc;
    }

    public void setEmailBcc(String emailBcc) {
        this.emailBcc = emailBcc;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public boolean isFileAsBody() {
        return fileAsBody;
    }

    public void setFileAsBody(boolean fileAsBody) {
        this.fileAsBody = fileAsBody;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailTemplate that = (EmailTemplate) o;

        return templateId.equals(that.templateId);

    }

    @Override
    public int hashCode() {
        return templateId.hashCode();
    }

    @Override
    public String toString() {
        return "EmailTemplate{" +
                "templateId='" + templateId + '\'' +
                ", templateName='" + templateName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", emailFrom='" + emailFrom + '\'' +
                ", emailTo='" + emailTo + '\'' +
                ", emailCc='" + emailCc + '\'' +
                ", emailBcc='" + emailBcc + '\'' +
                ", emailSubject='" + emailSubject + '\'' +
                ", emailBody='" + emailBody + '\'' +
                ", fileAsBody=" + fileAsBody +
                ", enabled=" + enabled +
                '}';
    }
}
