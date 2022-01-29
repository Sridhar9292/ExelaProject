package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.HolidayListMapper;
import com.exelatech.ecxapi.model.HolidayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HolidayListControllerTest {
	@Autowired
	private HolidayListMapper holidayListMapper;

	@Test	
	@Order(1)   
	public void  holidayList(){	
		List<HolidayList> holidayList=null;
		try {
			holidayList=holidayListMapper.getAll();
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(holidayList);
	}
	@Test	
	@Order(2)   
	public void  getHolidayNames() {
		List<HolidayList>  holidayList=null;
		try {
			holidayList=holidayListMapper.getHolidayNames();
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(holidayList); ;
	}
	@Test	
	@Order(4)   
	public void updateHoliday() {
		int count=0;
		
		String old_date="23032021";
		
		HolidayList holidayList =new HolidayList();
		holidayList.setHolidayId(12);
		holidayList.setHolidayName("TEST_HOLIDAY");
		holidayList.setHolidayDate("22032021");
		holidayList.setSaturdayFlag(1);
	
		try {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("ddMMyyyy");
		Date newDate = null, oldDate = null;
		int saturdayFlag = 0;
		int sundayFlag = 0;
		HolidayList holiday = null;
		newDate = (Date) sourceFormat.parse(holidayList.getHolidayDate());
		oldDate = (Date) sourceFormat.parse(old_date);
		System.out.println("newDate  ====>>>"+newDate);
		System.out.println("oldDate  ====>>>"+oldDate);
		holiday = holidayListMapper.updateCheckExist(holidayList.getHolidayId(),old_date);
		
		System.out.println("checkExist  ====>>>"+holiday);
		if (holidayList.getSundayFlag() != null) {
			sundayFlag = 1;
		}
		if (holidayList.getSaturdayFlag() != null) {
			saturdayFlag = 1;
		}
		

		if (holiday != null) {
			System.out.println("sourceDate update ====>>>"+holidayList.getHolidayDateFormat());
			count=holidayListMapper.updateHoliday(
					newDate, 
					oldDate, 
					saturdayFlag,
					sundayFlag,
					holidayList.getHolidayId());		

		}
			if (count != 0 ) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (Exception e) {
			assert(false);
		}
	}




	@Test	
	@Order(3)   
	public void insertHoliday() {
		HolidayList holiday = null;
		int count=0;
		try {
		HolidayList holidayList =new HolidayList();
		holidayList.setHolidayId(12);
		holidayList.setHolidayName("TEST_HOLIDAY");
		holidayList.setHolidayDate("23032021");
		holidayList.setSaturdayFlag(1);
		
		if (holidayList != null) {			 
			
			System.out.println("holidayList.getHolidayDate()  ====>>>"+holidayList.getHolidayDate());
			SimpleDateFormat sourceFormat = new SimpleDateFormat("ddMMyyyy");
			Date sourceDate = (Date) sourceFormat.parse(holidayList.getHolidayDate());
		
			holiday = holidayListMapper.checkExist(holidayList.getHolidayId(),holidayList.getHolidayDate());

			System.out.println("checkExist  ====>>>"+holiday);

			holidayList.setHolidayDateFormat(sourceDate);

			if (holidayList.getSundayFlag() != null) {
				holidayList.setSundayFlag(1);
			} else {
				holidayList.setSundayFlag(0);
			}

			if (holidayList.getSaturdayFlag() != null) {
				holidayList.setSaturdayFlag(1);
			} else {
				holidayList.setSaturdayFlag(0);
			}

		}
		if (holiday == null) {
			System.out.println("sourceDate  ====>>>"+holidayList.getHolidayDateFormat());
			count=holidayListMapper.insertHoliday(holidayList);
		}
			if (count != 0 ) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (Exception e) {
			assert(false);
		}
	}

	@Test	
	@Order(5)
	public void deleteHoliday() {
		String holidayId="12";
		String date="22032021";
		
		int count=0;
		try {
			count=holidayListMapper.deleteHoliday(new SimpleDateFormat("ddMMyyyy")
					.parse(date),Integer.parseInt(holidayId));
			if (count != 0 ) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			assert(false);
		}
	}

}
