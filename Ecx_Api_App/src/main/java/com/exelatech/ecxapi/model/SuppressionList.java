package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SuppressionList  implements Serializable {
    private static final long serialVersionUID = -2160412830967522355L;
	private String subAccount;
	private String emailAddress;
	private String reportType;
	private String userAbbrev;
    private String unsuppressTime;
    private String reason;
    private String job_number;
    private String removed;

	
	public int hashCode() {
		int result = getSubAccount().hashCode();
		result = 31 * result + (getEmailAddress() != null ? getEmailAddress().hashCode() : 0);
		result = 31 * result + (getReportType() != null ? getReportType().hashCode() : 0);
	     result = 31 * result + (getUserAbbrev() != null ? getUserAbbrev().hashCode() : 0);
	     result = 31 * result + (getUnsuppressTime() != null ? getUnsuppressTime().hashCode() : 0);
	     result = 31 * result + (getReason() != null ? getReason().hashCode() : 0);
	     result = 31 * result + (getJob_number() != null ? getJob_number().hashCode() : 0);
	     result = 31 * result + (getRemoved() != null ? getRemoved().hashCode() : 0);
		return result;
	}
	

}
