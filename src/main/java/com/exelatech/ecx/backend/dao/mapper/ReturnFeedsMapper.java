package com.exelatech.ecx.backend.dao.mapper;

import java.util.List;

import com.exelatech.ecx.backend.model.ReturnFeeds;
import org.apache.ibatis.annotations.Param;

public interface ReturnFeedsMapper extends GenericMapper<ReturnFeeds, String>
{

	//public abstract int getCount(String downStreamStatus);

	 ReturnFeeds getCount(String feedDocID,String downStreamStatus);

	List<ReturnFeeds> getRejectedRecords(String feedDocID);
	
	ReturnFeeds FetchRecords(String feedDocID);
	
	ReturnFeeds getErrorCount(String feedDocID );

	ReturnFeeds getRejectCount();
	
	ReturnFeeds getAcceptedCount();
	
	ReturnFeeds getVendors(@Param("feedDocID") String feedDocID);
	List<ReturnFeeds> getReturnsByFeedDocID(@Param("feedDocID") String feedDocID);
	//List<ReturnFeeds> getVendorReturns(@Param("vendor") String vendor);
	List<ReturnFeeds> getVendorReturns(@Param("vendor") String vendor,@Param("feedDocID") String feedDocID);
	ReturnFeeds getVendorReturnsByTransaction(@Param("feedDocID") String feedDocID, @Param("transactionID") String transactionID);
}

