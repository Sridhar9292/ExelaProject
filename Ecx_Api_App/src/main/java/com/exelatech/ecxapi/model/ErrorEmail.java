package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ErrorEmail implements Serializable {
	private static final long serialVersionUID = 1468227601228392728L;
	private String emailUUID;
	private String emailAddress;
}
