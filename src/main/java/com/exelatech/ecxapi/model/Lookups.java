package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Lookups implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String subType;
	private String name;
	private String value;
	private String description;
	private String active;
	private Date entryDate;
	private Date modifyDate;
	private String userAbbr;
	private String defaultValue;

}
