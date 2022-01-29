package com.exelatech.ecxapi.mapper;

import com.exelatech.ecxapi.model.ExpectedDatafeed;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpectedDatafeedMapper extends GenericMapper<ExpectedDatafeed, Long> {

	ExpectedDatafeed get(String id);

	int remove(Long id);
}
