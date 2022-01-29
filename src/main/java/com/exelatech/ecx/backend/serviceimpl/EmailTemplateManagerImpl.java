package com.exelatech.ecx.backend.serviceimpl;

import java.io.File;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.exelatech.ecx.backend.dao.mapper.EmailTemplateMapper;
import com.exelatech.ecx.backend.dao.mapper.ErrorCodesMapper;
import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.model.EmailTemplate;
import com.exelatech.ecx.backend.model.ErrorCodes;
import com.exelatech.ecx.backend.service.EmailTemplateManager;
import com.exelatech.ecx.backend.service.MailEngine;

import freemarker.template.Configuration;

/**
 * Created by VenkataDurgavarjhula on 9/17/2015.
 */
@Service("emailTemplateManager")
public class EmailTemplateManagerImpl implements EmailTemplateManager {
	@Autowired
	@Qualifier("freeMarkerConfig")
	Configuration freeMarkerConfiguration;
	@Autowired
	MailEngine mailEngine;
	@Autowired
	JavaMailSenderImpl mailSender;
	@Autowired
	private EmailTemplateMapper emailTemplateMapper;
	@Autowired
	private ErrorCodesMapper errorCodesMapper;
	protected final transient Log log = LogFactory.getLog(getClass());

	@Override
	@Async
	public void processEmail(ECXEvent event) throws Exception {
		String eventName = event.get(ECXEvent.NAME);

		if (eventName != null) {
			String errorCode = event.get("errorCode");
			if (errorCode != null) {
				ErrorCodes errorCodesObj = errorCodesMapper.get(errorCode);
				if (errorCodesObj == null) {// Error object not found based on errorCode
					errorCodesObj = new ErrorCodes();
					event.put("errorDesc", event.get("errorMessage") != null ? event.get("errorMessage") : "");
					event.put("errorCause", "");
					event.put("errorResolution", "");
				} else {
					event.put("errorDesc", errorCodesObj.getErrorDesc());
					event.put("errorCause", errorCodesObj.getErrorCause());
					event.put("errorResolution", errorCodesObj.getErrorResolution());
				}
			}
			List<EmailTemplate> emailTemplates = emailTemplateMapper.getEnabledEmailTemplateByEventName(eventName);
			for (EmailTemplate emailTemplate : emailTemplates) {
				if (emailTemplate.isEnabled()) {
					boolean fileAttachmentSupported = event.get("emailAttachmentSupported") != null
							? Boolean.valueOf(event.get("emailAttachmentSupported"))
							: Boolean.FALSE;
					MimeMessage mimeMessage = mailSender.createMimeMessage();

					MimeMailMessage mailMessage = new MimeMailMessage(mimeMessage);
					mailEngine.setFreeMarkerConfiguration(freeMarkerConfiguration);
					mailMessage.setFrom(emailTemplate.getEmailFrom());
					mailMessage.setTo(emailTemplate.getEmailTo().split(";|,"));

					if (emailTemplate.getEmailCc() != null) {
						mailMessage.setCc(emailTemplate.getEmailCc().split(";|,"));
					}
					if (emailTemplate.getEmailBcc() != null) {
						mailMessage.setBcc(emailTemplate.getEmailBcc().split(";|,"));
					}
					if (emailTemplate.isFileAsBody()) {
						String emailBody = FileUtils.readFileToString(new File(event.get("attachmentPath")));
						mailEngine.sendFreemarkerMessage(mailMessage.getMimeMessage(), emailTemplate.getEmailSubject(),
								emailBody, event, "plain");
					} else if (fileAttachmentSupported) {
						mailEngine.sendFreemarkerMessageWithFileAttachments(emailTemplate, event);
					} else {
						mailEngine.sendFreemarkerMessage(mailMessage.getMimeMessage(), emailTemplate.getEmailSubject(),
								emailTemplate.getEmailBody(), event);
					}
				}
			}
		}
	}
}
