package com.exelatech.ecxapi.mapper;

import com.exelatech.ecxapi.model.SuppressionList;

public interface SuppressionManager {
	public int removeEmailAddress(SuppressionList suppressionList, String apikey);
}
