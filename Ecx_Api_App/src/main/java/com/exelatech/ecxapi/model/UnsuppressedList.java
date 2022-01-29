package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class UnsuppressedList implements Serializable {
	private int id;
	private String subAccount;
	private String emailAddress;
    private String suppressTime;
	private String unSuppressTime;
	private String byUser;
	private Date entryDate;
    private Date modifyDate;	
	private String userAbbrev;
	
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof UnsuppressedList)) return false;

	    UnsuppressedList that = (UnsuppressedList) o;
	    if (id!=that.id) return false;
	    if (getSubAccount() != null ? !getSubAccount().equals(that.getSubAccount()) : that.getSubAccount() != null)
            return false;
	    if (getEmailAddress() != null ? !getEmailAddress().equals(that.getEmailAddress()) : that.getEmailAddress() != null)
            return false;
        if (getSuppressTime() != null ? !getSuppressTime().equals(that.getSuppressTime()) : that.getSuppressTime() != null)
            return false;
        if (getUnSuppressTime() != null ? !getUnSuppressTime().equals(that.getUnSuppressTime()) : that.getUnSuppressTime() != null)
            return false;
        if (getByUser() != null ? !getByUser().equals(that.getByUser()) : that.getByUser() != null)
            return false;
        if (getEntryDate() != null ? !getEntryDate().equals(that.getEntryDate()) : that.getEntryDate() != null)
            return false;
        if (getModifyDate() != null ? !getModifyDate().equals(that.getModifyDate()) : that.getModifyDate() != null)
            return false;
        return !(getUserAbbrev() != null ? !getUserAbbrev().equals(that.getUserAbbrev()) : that.getUserAbbrev() != null);
	}
	
	public int hashCode() {
        int result = id;
        result = 31 * result + (getSubAccount() != null ? getSubAccount().hashCode() : 0);
        result = 31 * result + (getEmailAddress() != null ? getEmailAddress().hashCode() : 0);
        result = 31 * result + (getSuppressTime() != null ? getSuppressTime().hashCode() : 0);
        result = 31 * result + (getUnSuppressTime() != null ? getUnSuppressTime().hashCode() : 0);
        result = 31 * result + (getByUser() != null ? getByUser().hashCode() : 0);
        result = 31 * result + (getEntryDate() != null ? getEntryDate().hashCode() : 0);
        result = 31 * result + (getModifyDate() != null ? getModifyDate().hashCode() : 0);
        result = 31 * result + (getUserAbbrev() != null ? getUserAbbrev().hashCode() : 0);
        return result;
	}
	
}
