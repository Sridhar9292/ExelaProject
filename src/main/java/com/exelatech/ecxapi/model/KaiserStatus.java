package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class KaiserStatus implements Serializable {
	private static final long serialVersionUID = 7136848408018831292L;
	private String statusId;
	 private String infoId;
	 private String processDate;
	 private String status;
	 private String entryDate;
	 private String modDate;
	 private String userAbberv;
}
