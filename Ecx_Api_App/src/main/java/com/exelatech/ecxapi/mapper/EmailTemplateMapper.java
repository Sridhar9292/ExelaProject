package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.EmailTemplate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailTemplateMapper extends GenericMapper<EmailTemplate, String> {

	List<EmailTemplate> getEmailTemplateByEventName(String eventName);

	List<EmailTemplate> getEnabledEmailTemplateByEventName(String eventName);

	List<EmailTemplate> getEmailTemplateByTemplateId(String templateId);
}
