package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class MassMutualDetailInfo implements Serializable {
	  private static final long serialVersionUID = 3832626162173359411L;
	private int massMutualDetailID;
	private int massMutualFeedID;
	private String reference;
	private String trackingNumber;
	private String totalCost;
	private String shipmentDate;
	private String attentionTo;
	private String company;
	private String address1;
	private String address2;
	private String address3;
	private String state;
	private String zipCode;
	private String processedStatus;
	private String description;
}
