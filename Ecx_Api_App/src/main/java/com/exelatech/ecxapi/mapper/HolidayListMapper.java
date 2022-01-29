package com.exelatech.ecxapi.mapper;

import java.util.Date;
import java.util.List;

import com.exelatech.ecxapi.model.HolidayId;
import com.exelatech.ecxapi.model.HolidayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HolidayListMapper extends GenericMapper<HolidayList, HolidayId> {

	public final String SATURDAY = "saturday";

	public final String SUNDAY = "sunday";

	public final String WEEKDAY = "weekday";


	public abstract List<HolidayList> getAll();

	public  abstract HolidayList get(String date);
	
	public abstract List<HolidayList> getHolidayNames();

	public abstract int insertHoliday(HolidayList holidayList);

	public abstract int updateHoliday(Date newDate, Date oldDate, Integer saturday, Integer sunday, Integer holidayId);

	public abstract int deleteHoliday(Date date, Integer holidayId);

	public abstract HolidayList fetchHoliday(Date date, int holidayId);

	public abstract HolidayList checkExist(Integer id, String date);

	public abstract HolidayList updateCheckExist(Integer id, String date);

}
