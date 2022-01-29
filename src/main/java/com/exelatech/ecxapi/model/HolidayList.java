package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class HolidayList implements Serializable {
	private static final long serialVersionUID = -8935930231599857547L;
	@JsonIgnore
	private String holidayDate1;
	@JsonIgnore
	private String holidayDate2;
	@JsonIgnore
	private String holidayDate3;
	@JsonIgnore
	private String holidayDate4;
	//@JsonIgnore
	//private HolidayId holidayId;
	private Integer holidayId;
	private String holidayName;
	@JsonIgnore
	private String holidayHolder;
	private String holidayDate;
	@JsonIgnore
	private Date holidayDateFormat;
	@JsonIgnore
	private String holidayIdList;

	private Integer saturdayFlag;
	private Integer sundayFlag;
	private String saturdayHolder;
	private String sundayHolder;
	private String save;
}
