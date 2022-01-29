package com.exelatech.ecxapi.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.exelatech.ecxapi.mapper.SuppressionManager;
import com.exelatech.ecxapi.mapper.UnsuppressedListMapper;
import com.exelatech.ecxapi.model.SuppressionList;
import com.exelatech.ecxapi.model.UnsuppressedList;

/**
 * @author Thirumoorthys
 *
 */
@Service("suppressionManager")
public class SuppressionManagerImpl implements SuppressionManager{
	private int statusCode = 0;
	
	@Autowired
	private UnsuppressedListMapper unSuppressedListMapper;

	private int suppressActivate(String reason, String emailAddress,String apikey ) {
		//String responseString=null;
		try {
			CloseableHttpClient client = HttpClients.createDefault();	
			StringBuilder url = new StringBuilder("https://api.sendgrid.com/v3/suppression/");		    
			
			String key ="Bearer "+ apikey;
			if( reason.equals("Bounced Address")){
			   url.append("bounces/"+emailAddress);
			}
			else if(reason.equals("Invalid"))
			{
				  url.append("invalid_emails/"+emailAddress);
			}
			HttpDelete httpDelete = new HttpDelete(url.toString());
			httpDelete.addHeader("Authorization",key);
			System.out.println("url = " +url);
			System.out.println("apikey ="+key);
			HttpResponse response = client.execute(httpDelete);
		if(response != null)
			
		{
			System.out.println("Response ="+response.getStatusLine().getStatusCode());
		}
			
		
			/*if (response.getStatusLine().getStatusCode() != 204 ) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}*/
			if(response.getStatusLine().getStatusCode() == 204){
				statusCode=204;
				//responseString = "Success"; 
			}else{
				statusCode=0;
				//responseString = "Not able to Remove"; 
			} 
			System.out.println("statusCode ="+statusCode);
	
			client.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statusCode;
	}
	

	
	public int unSuppressInsert(UnsuppressedList unSuppressedList){
		int insertResult = unSuppressedListMapper.insert(unSuppressedList);
		return insertResult;
	}

	public int removeEmailAddress(SuppressionList list,String apikey) {
		 UnsuppressedList unSuppressedList =null;
		String name =null;
		String outPut = null;	
		int count=0;
		count = suppressActivate(list.getReason(), list.getEmailAddress(),apikey);	
		if(statusCode == 204){		
	
		    unSuppressedList = new UnsuppressedList();
		    
			//unSuppressedList.setSubAccount(list.getSubAccount());
			unSuppressedList.setSubAccount(list.getJob_number());
			unSuppressedList.setEmailAddress(list.getEmailAddress());
			unSuppressedList.setSuppressTime("");
		    
		    Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
			//sdf.setTimeZone(TimeZone.getTimeZone("PST"));
			unSuppressedList.setUnSuppressTime(sdf.format(date));
			name = SecurityContextHolder.getContext().getAuthentication().getName();
			if(list.getUserAbbrev()!=null && !(list.getUserAbbrev().equals(""))){			
			unSuppressedList.setByUser(list.getUserAbbrev());
			}else
			{
				unSuppressedList.setByUser(name);
			}
			 count=unSuppressInsert(unSuppressedList);
		}
		return count; 
	} 
}
