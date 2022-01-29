package com.exelatech.ecx.backend.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.exelatech.ecx.backend.model.EmailTemplate;

public interface EmailTemplateMapper extends GenericMapper<EmailTemplate, String> {

	List<EmailTemplate> getEmailTemplateByEventName(@Param("eventName") String eventName);
	List<EmailTemplate> getEnabledEmailTemplateByEventName(@Param("eventName") String eventName);
	List<EmailTemplate> getEmailTemplateByTemplateId(String templateId);
}
