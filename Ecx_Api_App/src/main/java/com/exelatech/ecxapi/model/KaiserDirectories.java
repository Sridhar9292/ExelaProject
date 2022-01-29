package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class KaiserDirectories implements Serializable {
	private static final long serialVersionUID = 1L;
	private String collectionDate;
	private String directoryName;
	private String directoryStatus;
}
