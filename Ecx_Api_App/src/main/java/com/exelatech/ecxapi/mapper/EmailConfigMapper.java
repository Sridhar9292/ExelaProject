package com.exelatech.ecxapi.mapper;

import com.exelatech.ecxapi.model.EmailConfig;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailConfigMapper extends GenericMapper<EmailConfig, String> {

	// LinkedHashSet<Category> getAllCategory();
	int insertCategoriesForSubaccount(EmailConfig emailConfig);

	// public List<Category> getSubAccountCategories(String subAccountCode);
	public int deleteCategoriesForSubaccount(String subAccountCode);
	int reportInsert(String subaccount_code, 
			String report_type,
			String naming_pattern ,
			String report_path,
			String report_if_empty,
			String export_status);
	
	int deleteBySubaccountCode(String subAccountCode);
}
