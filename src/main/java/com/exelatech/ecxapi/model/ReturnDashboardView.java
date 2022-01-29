package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ReturnDashboardView implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String date;
	private List<ReturnDataFeed> confirmation = new ArrayList<ReturnDataFeed>();
	private List<ReturnDataFeed> downstreams = new ArrayList<ReturnDataFeed>();
	private List<ReturnDataFeed> vendors = new ArrayList<ReturnDataFeed>();

}
