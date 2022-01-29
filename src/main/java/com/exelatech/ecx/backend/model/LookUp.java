package com.exelatech.ecx.backend.model;

import java.io.Serializable;

public class LookUp implements Serializable {
	private String name;
	private String subType;
	private String value;

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
        return "LookUp{" +
        		"name='" + name + '\'' +
        		"subType='" + subType + '\'' +
        		"value='" + value + '\'' +
        		'}';
	}

}
