package com.exelatech.ecxapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PrintHubDashboard implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<PrintHubJobHeader> printFeeds = new ArrayList<PrintHubJobHeader>();

}
