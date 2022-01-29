package com.exelatech.ecx.backend.serviceimpl;


import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exelatech.ecx.backend.dao.mapper.ReturnFeedsMapper;
import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.model.ReturnDashboard;
import com.exelatech.ecx.backend.model.ReturnDataFeed;
import com.exelatech.ecx.backend.model.ReturnFeeds;
import com.exelatech.ecx.backend.repository.ReturnDashboardRepository;
import com.exelatech.ecx.backend.service.ElasticSearchManager;
import com.exelatech.ecx.backend.service.ReturnDashboardManager;
import com.exelatech.ecx.backend.util.DateUtil;

@Service("returnDashboardManager")
public class ReturnDashboardManagerImpl implements ReturnDashboardManager{

    @Autowired
    private ReturnFeedsMapper returnFeedsMapper;
    @Autowired
    private ElasticSearchManager<ReturnDashboard> returnDashboardService;
    
    @Autowired
    ReturnDashboardRepository<ReturnDashboard> returnRepo;
    
    protected final transient Log log = LogFactory.getLog(getClass());


	/*
	 * @Override public synchronized ReturnDashboard getReturnDashboard(String id)
	 * throws Exception { ReturnDashboard returnDashboard =
	 * returnDashboardService.get(id); if (returnDashboard == null) {
	 * returnDashboard = createReturnDashboard(id); } return returnDashboard;
	 * 
	 * }
	 */
	/*
	 * @Override public ReturnDashboard createReturnDashboard(String id) throws
	 * Exception { ReturnDashboard returnDashboard = new ReturnDashboard();
	 * returnDashboard.setId(id); ReturnDashboard returnDashboards =
	 * returnRepo.save(returnDashboard); return returnDashboards; }
	 */

	@Override
	public ReturnDashboard createReturnDashboard(String id) throws Exception {
		//System.out.println(" Id ==> "+id);
		ReturnDashboard returnDashboard = new ReturnDashboard();
		returnDashboard.setId(id);
		//System.out.println(" Return Dashboard ==> " + returnDashboard);
		//returnDashboardService.save(returnDashboard);
		returnRepo.save(returnDashboard);
		//ReturnDashboard returnDashboards = returnRepo.save(returnDashboard);
		//System.out.println("Return Dashboard1 ==> " + returnDashboardService.get(id));
		return returnDashboardService.get(id); // return returnDashboards;

	}

	@Override
	public synchronized ReturnDashboard processEvent(ECXEvent event) throws Exception {
		String feedDocID = event.get(ECXEvent.FEED_DOC_ID);
		//System.out.println(" ProcessEvent ==> "+ECXEvent.FEED_DOC_ID);
		String eventName = event.get(ECXEvent.NAME);
		String vendor = event.get("vendor");
		log.debug("feed doc id:" + feedDocID);
		log.debug("event is:" + eventName);
		//System.out.println(" feed doc id and eventname ==> "+feedDocID+": "+eventName);
		long timestamp = Long.valueOf(event.get(ECXEvent.TIMESTAMP));
		String id = DateUtil.getUTCDateFromTimestamp(timestamp, "yyyyMMdd");
		// ReturnDashboard returnDashboard = getReturnDashboard(id);
		ReturnDashboard returnDashboard = returnDashboardService.get(id);
		
		if(null == returnDashboard) {
			returnDashboard = createReturnDashboard(id);
		}
		
		if (eventName.equals("ReturnFileReceived") || eventName.equals("ReturnFileImported")
				|| eventName.equals("VendorReturnFileExported") || eventName.equals("VendorConfFileSubmitted")
				|| eventName.equals("VendorConfFileImported") || eventName.equals("DownstreamConfirmationFileProcessed")
				|| eventName.equals("ReturnsConfirmError")) {

            ReturnDataFeed returnDataFeed = returnDashboard.getDataFeedByFeedDocID(feedDocID);
            //returnDataFeed is null, create new entry
            if (returnDataFeed == null && feedDocID != null) {
                returnDataFeed = new ReturnDataFeed();
                ReturnFeeds returnFeeds = returnFeedsMapper.getVendors(feedDocID);
                String feedDocAuthor=feedDocID.split("\\.")[1].toUpperCase();
                String vendorCode;

                if(feedDocID.contains("return")){
                    returnDataFeed.setFileType("Returns");
                    if(feedDocAuthor.equals("RPPS")||feedDocAuthor.equals("FIS")||feedDocAuthor.equals("FISERV")) {
                        returnDataFeed.setTo(feedDocAuthor);
                        vendorCode = feedDocAuthor;
                    }else{
                        returnDataFeed.setFrom(feedDocAuthor);
                        vendorCode = returnFeeds.getVendorCode();
                    }
                }else{
                    returnDataFeed.setFileType("Conf");
                    if(feedDocAuthor.equals("RPPS")||feedDocAuthor.equals("FIS")||feedDocAuthor.equals("FISERV")) {
                        returnDataFeed.setFrom(feedDocAuthor);
                        vendorCode = feedDocAuthor;
                    }else{
                        returnDataFeed.setTo(feedDocAuthor);
                        vendorCode = vendor;
                    }
                }
                returnDataFeed.setVendorCode(vendorCode);
                returnDataFeed.setFileType(feedDocID.contains("return")?"Returns":"Conf");
                returnDataFeed.setFeedDocID(feedDocID);
                returnDataFeed.setActualTime(DateUtil.getDateTime("MM/dd/yyyy HH:mm", new Date()));
                returnDataFeed.setFeedID(new Long(returnDashboard.getReturnFeeds().size()+1));
                returnDashboard.getReturnFeeds().add(returnDataFeed);
                //returnDashboardService.save(returnDashboard);
                returnRepo.save(returnDashboard);
            }

            if (eventName.equals("ReturnFileReceived")) {
                returnDataFeed.getReceiveStep().setName("Successful");
                returnDataFeed.getReceiveStep().setColor("success");
                //returnDashboardService.save(returnDashboard);
                returnRepo.save(returnDashboard);
            }else if (eventName.equals("ReturnFileImported")) {
                List<ReturnFeeds> returnsList = returnFeedsMapper.getReturnsByFeedDocID(feedDocID);
                returnDataFeed.setSummary(returnsList);
                returnDataFeed.getImportStep().setName("Successful");
                returnDataFeed.getImportStep().setColor("success");
                ReturnFeeds rfs = returnFeedsMapper.getCount(feedDocID, "RECEIVED");
                returnDataFeed.setReturns(rfs.getCount());
                //returnDashboardService.save(returnDashboard);
                returnRepo.save(returnDashboard);
            }else if (eventName.equals("VendorReturnFileExported")) {
            	List<ReturnFeeds> returnsList = returnFeedsMapper.getVendorReturns(returnDataFeed.getVendorCode(),returnDataFeed.getFeedDocID());
                returnDataFeed.setSummary(returnsList);
                returnDataFeed.getExportStep().setName("Successful");
                returnDataFeed.getExportStep().setColor("success");
                returnDataFeed.getSendStep().setName("Successful");
                returnDataFeed.getSendStep().setColor("success");
                returnDataFeed.setReturns(Integer.parseInt(event.get("returnCount")));
               // returnDashboardService.save(returnDashboard);
                returnRepo.save(returnDashboard);
            }else if (eventName.equals("VendorConfFileSubmitted")) {
                returnDataFeed.getReceiveStep().setName("Successful");
                returnDataFeed.getReceiveStep().setColor("success");
                //returnDashboardService.save(returnDashboard);
                returnRepo.save(returnDashboard);
            }else if(eventName.equals("ReturnsConfirmError")){
                returnDataFeed.getImportStep().setName("Failed");
                returnDataFeed.getImportStep().setColor("danger");
            }else if (eventName.equals("VendorConfFileImported")) {
                returnDataFeed.getImportStep().setName("Successful");
                returnDataFeed.getImportStep().setColor("success");
                returnDataFeed.setAccepted(Integer.parseInt(event.get("acceptedCount")));
                returnDataFeed.setRejected(Integer.parseInt(event.get("rejectedCount")));
                //returnDashboardService.save(returnDashboard);
                returnRepo.save(returnDashboard);
            }else if (eventName.equals("DownstreamConfirmationFileProcessed")) {
                returnDataFeed.getExportStep().setName("Successful");
                returnDataFeed.getExportStep().setColor("success");
                returnDataFeed.getSendStep().setName("Successful");
                returnDataFeed.getSendStep().setColor("success");
                returnDataFeed.setAccepted(Integer.valueOf(event.get("acceptedCount")));
                returnDataFeed.setRejected(Integer.valueOf(event.get("rejectedCount")));
                //returnDashboardService.save(returnDashboard);
                returnRepo.save(returnDashboard);

            }
        }
        return returnDashboard;

    }
     

	/*
	 * @Override public Heartbeat getHeartbeat() throws Exception { return new
	 * Heartbeat("ok"); }
	 */


    //returns
    
     
 
 /*   @Override
    public ReturnDashboard updateVendorConfirmation(String id, String vendor, String feedDocID) throws Exception {
        ReturnDashboard returnDashboard = returnDashboardService.get(id);
        List<ReturnFeeds> returnFeeds = null;
        if(StringUtils.containsIgnoreCase(feedDocID, vendor)){
            ReturnDataFeed returnDataFeed = returnDashboard.getDataFeedByFeedDocID(feedDocID);
            returnFeeds = returnDataFeed.getSummary();
            for(ReturnFeeds returnFeedSummary : returnFeeds){
                ReturnFeeds returnFeed = returnFeedsMapper.getVendorReturnsByTransaction(returnFeedSummary.getFeedDocID(), returnFeedSummary.getTransactionId());
                returnFeedSummary.setVendorStatus(returnFeed.getVendorStatus());
                returnFeedSummary.setVendorRejectCode(returnFeed.getVendorRejectCode());
                returnFeedSummary.setVendorRejectDescription(returnFeed.getVendorRejectDescription());
            }
        }else{
            returnFeeds = returnFeedsMapper.getReturnsByFeedDocID(feedDocID);
            ReturnDataFeed dataFeed = returnDashboard.getDataFeedByFeedDocID(feedDocID);
            dataFeed.setSummary(returnFeeds);
        }
        returnDashboardService.save(returnDashboard);
        return returnDashboard;
    }
    */
    
    
/*
    @Override
    public List<Comment> addComments(String id, int feedIndex, Comment comment) throws Exception {
        ReturnDashboard returnDashboard = returnDashboardService.get(id);
        String commentedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        comment.setTimestamp(new Date());
        comment.setCommentedBy(commentedBy);
        ReturnDataFeed rdf = returnDashboard.getReturnFeeds().get(feedIndex);
        rdf.getComments().add(comment);
        returnDashboardService.save(returnDashboard);
        return rdf.getComments();
    }
    */
}