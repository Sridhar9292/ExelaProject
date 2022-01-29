package com.exelatech.ecx.backend.dao.mapper;

import java.util.List;

import com.exelatech.ecx.backend.model.Holiday;

public interface HolidayMapper extends GenericMapper<Holiday, String> {
	  List<Holiday> getHoliday();
}


 