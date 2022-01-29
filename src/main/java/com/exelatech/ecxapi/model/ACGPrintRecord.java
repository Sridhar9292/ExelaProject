package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ACGPrintRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	private String appID;
	private String prodCode;
	private String funCode;
	private String docCode;
	private String fName;
	private String lName;
	private String mName;
	private String status;
	private String respCode;
	private String respDesc;
	private String errDesc;
	
}
