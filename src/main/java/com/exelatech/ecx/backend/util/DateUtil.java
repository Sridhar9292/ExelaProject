package com.exelatech.ecx.backend.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;

import com.exelatech.ecx.backend.constant.Constants;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 *         to correct time pattern. Minutes should be mm not MM (MM is month).
 */
public final class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static final String TIME_PATTERN = "HH:mm";

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private DateUtil() {
    }

    /**
     * Return default datePattern (MM/dd/yyyy)
     *
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        Locale locale = LocaleContextHolder.getLocale();
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale)
                    .getString("date.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "MM/dd/yyyy";
        }

        return defaultDatePattern;
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * This method attempts to convert an Oracle-formatted date
     * in the form dd-MMM-yyyy to mm/dd/yyyy.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @throws ParseException when String doesn't match the expected format
     * @see SimpleDateFormat
     */
    public static Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    public static Date convertStringToUTCDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * MM/dd/yyyy HH:MM a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     *
     * @return the current date
     * @throws ParseException when String doesn't match the expected format
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.warn("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(final String strDate) throws ParseException {
        return convertStringToDate(getDatePattern(), strDate);
    }

    public static String getCurrentUTCDate(String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(getCurrentUTCDate());
    }

    public static Calendar getCurrentUTCCalendar(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar getUTCCalendar(Date date){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static String getUTCDateFromTimestamp(long timetsamp, String pattern){
        Date date = new Date(timetsamp);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    public static Date getCurrentUTCDate(){
        return getCurrentUTCCalendar().getTime();
    }
    public static Date getYesterdayUTCDate(){
        Calendar cal = getCurrentUTCCalendar();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static String convertStringUTCDateToStringUTCDate(String inputDate, String inputPattern, String outputPattern){
        String outputDate="";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(inputPattern);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(inputDate);
            sdf = new SimpleDateFormat(outputPattern);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            outputDate = sdf.format(date);
        }catch (ParseException pe){
            pe.printStackTrace();
        }
        return outputDate;
    }

    public static long getEventDateAsLong(String dataTimestamp, String timestamp){
        Date date = null;
        try {
            date = DateUtil.convertStringToDate("yyyy-MM-dd hh:mm:ss", dataTimestamp.trim());
        }catch(ParseException pe){ // try ISO Timestamp String
            date = DatatypeConverter.parseDateTime(timestamp).getTime();
        }
        return date.getTime();
    }

   public static long getDataTimestamp(String timestamp) {
	   String theTimestamp = timestamp.trim();
	   List<String> patterns = Arrays.asList( new String[] { "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy hh:mm:ss aaa", "dd.MM.yyyy HH:mm:ss" });
	   for (String pattern : patterns) {
		   try { 
			   //return DateTime.parse(theTimestamp, DateTimeFormat.forPattern(pattern)).getMillis();
			   return 0;
		   } catch (IllegalArgumentException iae) {
			   ; // try again...
		   }
	   }
	   return 0L;
   }
   
   public static long getAtTimestamp(String timestamp) {
	   return DateTime.parse(timestamp, ISODateTimeFormat.dateTime()).getMillis();
   }

   public static long defaultTimestampIfBad(String timestamp, String defaulTimestamp) throws IllegalArgumentException {
	   String theTimestamp = timestamp.trim();
	   List<String> patterns = Arrays.asList( new String[] { "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy hh:mm:ss aaa", "dd.MM.yyyy HH:mm:ss" });
	   for (String pattern : patterns) {
		   try { 
			   return DateTime.parse(theTimestamp, DateTimeFormat.forPattern(pattern)).getMillis();
		   } catch (IllegalArgumentException iae) {
			   ; // try again...
		   }
	   }
	   return DateTime.parse(defaulTimestamp, ISODateTimeFormat.dateTime()).getMillis();
    }
    public static long getPrintDateAsLong(String dataTimestamp, String timestamp){
        Date date = null;
        try {
            date = DateUtil.convertStringToDate("MM/dd/yyyy hh:mm:ss aaa", dataTimestamp);
        }catch(ParseException pe){ // try ISO Timestamp String
            date = DatatypeConverter.parseDateTime(timestamp).getTime();
        }
        return date.getTime();
    }

    public static long getPrintDateAsLong(String dataTimestamp){
        Date date = null;
        try {
            date = DateUtil.convertStringToDate("dd.MM.yyyy HH:mm:ss", dataTimestamp);
        }catch(ParseException pe){ // try ISO Timestamp String
            log.warn("Invalid date:"+pe.getMessage());
        }
        return date.getTime();
    }
    
    public static Date convertUTCStringToLocalDate(String stringDate) throws ParseException{
    	final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    	final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
    	final TimeZone utc = TimeZone.getTimeZone("UTC");
    	sdf.setTimeZone(utc);
    	return sdf.parse(stringDate);
    }
}
