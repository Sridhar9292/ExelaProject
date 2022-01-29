package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class KaiserProcessInfo implements Serializable {
	private static final long serialVersionUID = -2026852293718009598L;
	private String infoId;
	 private String collectionDate;
	 private String directories;
	 private String webUnits;
	 private String combinedRun;
	 private String paperUnits;
	 private String sentToDps;
	 private String dpsJob;
	 private String entryDate;
	 private String modDate;
	 private String userAbberv;
	 private Integer error;
	 private Integer dirError;
	 private Integer present;
	 private Boolean isErrorPresent = false;
}
