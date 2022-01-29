package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class KaiserPrintRecord implements Serializable {
	private static final long serialVersionUID = 593780383936760892L;
	private int rowNo;
	private String id;
	private String job;
	private String shipName;
	private String shipAttention;
	private String contract;
	private String  contractCopies;
	private String status;
	private String shipDate;
	private String printTypeCode;
	private Date completionDate;
}
