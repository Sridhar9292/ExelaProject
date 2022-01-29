package com.exelatech.ecxapi.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.exelatech.ecxapi.mapper.BaseObject;
import com.exelatech.ecxapi.util.DateUtil;

import lombok.Data;

@Data
@Document(indexName = "return-console")
public class ReturnDashboard extends BaseObject {

	@Id
	private String id;
	private String date;
	private List<ReturnDataFeed> returnFeeds = new ArrayList<ReturnDataFeed>();

	public ReturnDashboard() {
		this.date = DateUtil.getCurrentUTCDate("yyyyMMdd");
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<ReturnDataFeed> getReturnFeeds() {
		return returnFeeds;
	}

	public void setReturnFeeds(List<ReturnDataFeed> returnFeeds) {
		this.returnFeeds = returnFeeds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ReturnDashboard that = (ReturnDashboard) o;

		if (!date.equals(that.date))
			return false;
		return returnFeeds != null ? returnFeeds.equals(that.returnFeeds) : that.returnFeeds == null;

	}

	@Override
	public int hashCode() {
		int result = date.hashCode();
		result = 31 * result + (returnFeeds != null ? returnFeeds.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ReturnDashboard{" + "date='" + date + '\'' + ", returnFeeds=" + returnFeeds + '}';
	}

	public void addReturnDataFeed(ReturnDataFeed returnDataFeed) {
		if (!returnFeeds.contains(returnDataFeed)) {
			if (returnDataFeed.getFeedID() == null) {
				returnDataFeed.setFeedID(new Long(returnFeeds.size() + 1));
			}
			returnFeeds.add(returnDataFeed);
		}
	}

	public ReturnDataFeed getDataFeedByFeedDocID(String feedDocID) {
		if (feedDocID == null) {
			return null;
		}

		for (ReturnDataFeed returnDataFeed : returnFeeds) {
			if (returnDataFeed.getFeedDocID() != null && returnDataFeed.getFeedDocID().equals(feedDocID)) {
				return returnDataFeed;
			}
		}

		return null;
	}

	public ReturnDataFeed getDataFeedByFeedID(Long feedID) {
		for (ReturnDataFeed returnDataFeed : returnFeeds) {
			// Get Datafeed based on feed_id
			if (returnDataFeed.getFeedID() != null && returnDataFeed.getFeedID() == feedID) {
				return returnDataFeed;
			}
		}
		return null;
	}

	public boolean isDataFeedReceived(Long feedID) {
		for (ReturnDataFeed returnDataFeed : returnFeeds) {
			if (returnDataFeed.getFeedID() == feedID && returnDataFeed.getFeedDocID() != null) {
				return true;
			}
		}
		return false;
	}

}
