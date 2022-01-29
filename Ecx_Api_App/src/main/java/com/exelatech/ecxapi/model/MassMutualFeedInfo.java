package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class MassMutualFeedInfo  implements Serializable {
	  private static final long serialVersionUID = 3832626162173359411L;
	private int massMutualFeedID; 
	private String fileName;
	private String fileReceivedDate;
	private String description;
	private String status;
	
}
