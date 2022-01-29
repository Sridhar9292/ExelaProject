package com.exelatech.ecxapi.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.exelatech.ecxapi.mapper.BaseObject;
import com.exelatech.ecxapi.util.DateUtil;

import lombok.Data;

@Data
@Document(indexName = "console")
public class Dashboard extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String date;
	private String holiday;
	private List<DataFeed> remitFeeds = new ArrayList<DataFeed>();

	public Dashboard() {
		this.date = DateUtil.getCurrentUTCDate("yyyyMMdd");
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public List<DataFeed> getRemitFeeds() {
		return remitFeeds;
	}

	public void setRemitFeeds(List<DataFeed> remitFeeds) {
		this.remitFeeds = remitFeeds;
	}

	public void addDataFeed(DataFeed dataFeed) {
		if (!remitFeeds.contains(dataFeed)) {
			if (dataFeed.getFeedID() == null) {
				dataFeed.setFeedID(new Long(remitFeeds.size() + 1));
			}
			remitFeeds.add(dataFeed);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Dashboard dashboard = (Dashboard) o;

		if (!date.equals(dashboard.date))
			return false;
		return !(remitFeeds != null ? !remitFeeds.equals(dashboard.remitFeeds) : dashboard.remitFeeds != null);

	}

	@Override
	public int hashCode() {
		int result = date.hashCode();
		result = 31 * result + (remitFeeds != null ? remitFeeds.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Dashboard{" + "date='" + date + '\'' + ", remitFeeds=" + remitFeeds + '}';
	}

	public DataFeed getDataFeedByFeedDocID(String feedDocID) {
		if (feedDocID == null) {
			return null;
		}
		for (DataFeed dataFeed : remitFeeds) {
			// Get Datafeed based on feed_doc_id
			if (dataFeed.getFeedDocID() != null && dataFeed.getFeedDocID().equals(feedDocID)) {
				return dataFeed;
			}
		}
		return null;
	}

	// public DataFeed getDataFeed(String feedDocID){
	// if(feedDocID==null){
	// return null;
	// }
	// for(DataFeed dataFeed : remitFeeds){
	// // Get Datafeed based on feed_doc_id
	// if(dataFeed.getFeedDocID()!=null &&
	// dataFeed.getFeedDocID().equals(feedDocID)){
	// return dataFeed;
	// }else if(dataFeed.getFilePattern()!=null){
	// Pattern p = Pattern.compile(dataFeed.getFilePattern().replace("*", "\\w+"));
	// Matcher m = p.matcher(feedDocID);
	// // Specifically for WU as WU has 2 feeds with same pattern
	// if(m.matches() && (dataFeed.getFeedDocID()==null ||
	// dataFeed.getFeedDocID().equals(""))){
	// dataFeed.setFeedDocID(feedDocID);
	// dataFeed.setActualTime(DateUtil.getDateTime("HH:mm",new Date()));
	// return dataFeed;
	// }
	// }
	// }
	// return null;
	// }

	public DataFeed getDataFeedByFeedID(Long feedID) {
		for (DataFeed dataFeed : remitFeeds) {
			// Get Datafeed based on feed_id
			if (dataFeed.getFeedID() != null && dataFeed.getFeedID() == feedID) {
				return dataFeed;
			}
		}
		return null;
	}

	public boolean isDataFeedReceived(Long feedID) {
		for (DataFeed dataFeed : remitFeeds) {
			if (dataFeed.getFeedID() == feedID && dataFeed.getFeedDocID() != null) {
				return true;
			}
		}
		return false;
	}

	// public String getDashboardDate(){
	// return DateUtil.convertStringUTCDateToStringUTCDate(date, "yyyyMMdd",
	// "yyyy-MM-dd");
	// }
}