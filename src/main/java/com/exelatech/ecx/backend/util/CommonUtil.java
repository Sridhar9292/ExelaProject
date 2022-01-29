package com.exelatech.ecx.backend.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.exelatech.ecx.backend.constant.Constants;
import com.exelatech.ecx.backend.model.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class CommonUtil {
    private static final Log log = LogFactory.getLog(CommonUtil.class);

    private CommonUtil() {
    }

    public static Biller addDefaultVendors(Biller biller) {
        Set<LabelValue> vendors = new TreeSet<>();
        vendors.add(new LabelValue("ACI", "ACI"));
        vendors.add(new LabelValue("FIS (Metavante)", "FIS"));
        vendors.add(new LabelValue("FISERV (Checkfree)", "FISERV"));
        /*vendors.add(new LabelValue("OPC", "OPC"));*/
        vendors.add(new LabelValue("Mastercard RPPS", "RPPS"));
        /*vendors.add(new LabelValue("Western Union", "WU")); */
        for(LabelValue vendor : vendors) {
            String vendorCode = vendor.getValue();
            boolean contains = false;

            for (ExternalBillerIds ebi : biller.getExternalBillerIdsList()) {
                if (vendorCode.equals(ebi.getVendorCode())) {
                    ebi.setVendorDisplayName(vendor.getLabel());
                    ebi.setAdded(true);
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                ExternalBillerIds eBillerIds = new ExternalBillerIds();
                eBillerIds.setClientCode(biller.getClientCode());
                eBillerIds.setClientLobCode(biller.getClientLobCode());
                eBillerIds.setVendorCode(vendorCode);
                eBillerIds.setVendorDisplayName(vendor.getLabel());
                eBillerIds.setAdded(false);
                eBillerIds.setEnabled("FALSE");
                biller.getExternalBillerIdsList().add(eBillerIds);
            }
        }
        return biller;
    }

    public static String getVendorEnabled(ExternalBillerIds ebi, Biller biller) {
        if(biller!=null) {
            List<ExternalBillerIds> ebiList = biller.getExternalBillerIdsList();
            for (ExternalBillerIds externalBillerIds : ebiList) {
                if (externalBillerIds.getClientCode().equals(ebi.getClientCode()) && externalBillerIds.getClientLobCode().equals(ebi.getClientLobCode()) && externalBillerIds.getVendorCode().equals(ebi.getVendorCode())) {
                    return externalBillerIds.getEnabled();
                }
            }
        }
        return "FALSE";
    }

    public static boolean isFileExpected(String stringDate, String pattern, TimeZone timeZone){
        try {
            Date date = DateUtil.convertStringToUTCDate("yyyyMMdd", stringDate);
            return isFileExpected(date, pattern, timeZone);
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        return false;
    }

    public static boolean isFileExpectedToday(String pattern, TimeZone timeZone){
        return isFileExpected(DateUtil.getCurrentUTCDate(), pattern, timeZone);
    }

    public static boolean isFileExpected(Date currentDate, String pattern, TimeZone timeZone) {
        Date nextRunDate = getNextRunDate(currentDate, pattern, timeZone);
        if (DateUtil.getUTCDateFromTimestamp(nextRunDate.getTime(),"yyyyMMdd").equals(DateUtil.getUTCDateFromTimestamp(currentDate.getTime(), "yyyyMMdd"))) {
            return true;
        }
        return false;
    }

    public static Date getNextRunDate(Date date, String pattern, TimeZone timeZone){
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(pattern, timeZone);
        Date nextRunDate = cronSequenceGenerator.next(DateUtil.getUTCCalendar(date).getTime());
        return nextRunDate;
    }
    public static Date getNextRunDate(String stringDate, String pattern, TimeZone timeZone){
        try {
            Date date = DateUtil.convertStringToUTCDate("yyyyMMdd",stringDate);
            return getNextRunDate(date, pattern, timeZone);
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        return null;
    }

    public static ECXEvent getJsonAsEvent(String eventName, String json){
    	
        if(eventName==null){
            eventName = new JSONObject(json).getJSONArray("tags").getString(1).split("=")[1];
        }
    	
        ECXEvent ecxEvent = new ECXEvent(eventName);
        ecxEvent.setId(UUID.randomUUID().toString());
        Map<String, Object> map = new HashMap<>();
        Map<String,String> newMap =new HashMap<String,String>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                try {
                    String mapValue = "";
                    Object value = entry.getValue();
                    if(value instanceof Integer) {
                        mapValue = String.valueOf((Integer) value);
                    }else if(value instanceof ArrayList){
                        mapValue = value.toString();
                    }else if(value instanceof LinkedHashMap){
                        mapValue = value.toString();
                    }else{
                        mapValue = (String) value;
                    }
                    newMap.put(entry.getKey(), mapValue);
                } catch (ClassCastException cce) {
                    cce.printStackTrace();
                }
            }
            ecxEvent.putAll(newMap);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        return ecxEvent;
    }

    public static String getFileReferenceFromFilename(String filename){
        filename = filename.trim();
        Pattern pattern = Pattern.compile(Constants.FILENAME_MATCHING_PATTERN);
        Matcher matcher = pattern.matcher(filename);
        String fileReference = "";
        if(matcher.find()){
            fileReference = (filename.substring(0,matcher.start()));
        }
        return fileReference;
    }
    
    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
