package com.exelatech.ecxapi.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	private String categoryName="";
	private String userAbbr="";
	private boolean selected;
	private int emailCategoryId;
	private String subaccountCode="";
	private String categoryValue="";
	private int dynamicMail;
	private boolean selectedDynamicMail;
}
