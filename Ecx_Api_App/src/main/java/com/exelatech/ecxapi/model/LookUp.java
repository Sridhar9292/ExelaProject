package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class LookUp implements Serializable {
	private static final long serialVersionUID = 4716017077397855358L;
	private String name;
	private String subType;
	private String value;
}
