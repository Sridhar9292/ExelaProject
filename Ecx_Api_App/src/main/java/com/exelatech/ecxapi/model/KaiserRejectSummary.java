package com.exelatech.ecxapi.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/* Added To Display the Rejected Directories,
 * Rejected Files and
 * Rejected Records in Kaiser Process
 */
@Data
public class KaiserRejectSummary {
	private List<String> rejDirList = new ArrayList<String>();
	private List<String> rejXMLList = new ArrayList<String>();
	private List<RejectStatus> fileRejList = new ArrayList<RejectStatus>();
	private List<RejectStatus> recordRejList = new ArrayList<RejectStatus>();
}
