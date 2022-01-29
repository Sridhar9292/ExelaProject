package com.exelatech.ecxapi.model;

import lombok.Data;

@Data
public class RejectStatus {
	 private String dirName;
	 private String fileName;
	 private String purchaserId;
	 private String contractId;
	 private String versionNumber;
	 private String recipientType;
	 private String name;
	 private String status;
	 private String processStatusId;
	 private String processInfoId;
}
