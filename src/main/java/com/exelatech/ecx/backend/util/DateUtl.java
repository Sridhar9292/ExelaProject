package com.exelatech.ecx.backend.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

public class DateUtl {

    /**
     * This method return a Date from epoch milliseconds time
     *
     * @param timeString the epoch time in milliseconds
     * @return a converted Date object
     */
    public static String getEpochFromDateString(String dateString,String format) {
        if (dateString == null || dateString.equals(""))
            return null;
        SimpleDateFormat f = new SimpleDateFormat(format, Locale.ENGLISH);

        Date date;
        try {
            date = (Date) f.parse(dateString);
        } catch (ParseException e) {
           return null;
        }
        return String.valueOf(date.getTime());
    }



    /**
     * This method return a Date from epoch milliseconds time
     *
     * @param timeString the epoch time in milliseconds
     * @return a converted Date object
     */
    public static Date getDateFromEpoch(String timeString) {
        if (timeString == null || timeString.equals(""))
            timeString = "-2208988800000";

        long seconds = Long.parseLong(timeString);
        Date date = new Date(seconds);
        return date;
    }

    /**
     * This method return a Date in epoch milliseconds format
     *
     * @param timeString the epoch time in seconds
     * @return a converted Date object
     */
    public static String convertToUnixTime(String timeString) {
        if (timeString == null || timeString.equals(""))
            return null;

        long seconds = Long.parseLong(timeString) * 1000;
        return String.valueOf(seconds);
    }

    public static String convertToUnixTime(Date timeString) {
        if (timeString == null)
            return "";

        return String.valueOf(timeString.getTime());
    }

    public static String CalculateElapsedTime(long d1, long d2) {
        DateTime startTime = new DateTime(d1);
        DateTime endTime = new DateTime(d2);
        long h = Math.abs((Days.daysBetween(startTime, endTime).getDays() * 24)
                + (Hours.hoursBetween(startTime, endTime).getHours() % 24));
        long m = Math.abs(Minutes.minutesBetween(startTime, endTime).getMinutes() % 60);

        return h + "h +" + m + "m";
    }

    public static String GetInterval(int days) {
        long interval = LocalDateTime.now().minusDays(days).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

        return String.valueOf(interval) + "000";
    }
    
    public static String convertMs(String time) {
    	long targetTime = Long.valueOf(time);
    	int length = time.length();
    	if(length == 10) {
    		targetTime = Long.valueOf(time)*1000;
    	}
    	return String.valueOf(targetTime);
    }

    public DateUtl() {
    }
}