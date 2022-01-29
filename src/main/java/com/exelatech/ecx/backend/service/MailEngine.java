package com.exelatech.ecx.backend.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.exelatech.ecx.backend.model.EmailTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * Class for sending e-mail messages based on Velocity templates or with
 * attachments.
 *
 * @author Matt Raible
 */
@Service
@PropertySource("classpath:mail.properties")
public class MailEngine {

	private final Log log = LogFactory.getLog(MailEngine.class);

	@Autowired
	private JavaMailSender mailSender;

	// @Autowired
	private VelocityEngine velocityEngine;

	@Value("${mail.default.from}")
	private String defaultFrom;

	@Autowired
	@Qualifier("freeMarkerConfig")
	private Configuration freeMarkerConfiguration;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setFrom(String from) {
		this.defaultFrom = from;
	}

	public void setFreeMarkerConfiguration(Configuration freeMarkerConfiguration) {
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	/**
	 * Send a simple message based on a Velocity template.
	 * 
	 * @param msg
	 *            the message to populate
	 * @param templateName
	 *            the Velocity template to use (relative to classpath)
	 * @param model
	 *            a map containing key/value pairs
	 */
	@SuppressWarnings("unchecked")
	public void sendMessage(MimeMessage msg, String templateName, Map model) {
		String result = null;

		try {
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", model);
		} catch (VelocityException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		try {
			msg.setText(result);
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		send(msg);
	}

	/**
	 * Send a simple message with pre-populated values.
	 * 
	 * @param msg
	 *            the message to send
	 * @throws MailException
	 *             when SMTP server is down
	 */
	public void send(MimeMessage msg) throws MailException {
		try {
			mailSender.send(msg);
		} catch (MailException ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Convenience method for sending messages with attachments.
	 *
	 * @param recipients
	 *            array of e-mail addresses
	 * @param sender
	 *            e-mail address of sender
	 * @param resource
	 *            attachment from classpath
	 * @param bodyText
	 *            text in e-mail
	 * @param subject
	 *            subject of e-mail
	 * @param attachmentName
	 *            name for attachment
	 * @throws MessagingException
	 *             thrown when can't communicate with SMTP server
	 */
	public void sendMessage(String[] recipients, String sender, ClassPathResource resource, String bodyText,
			String subject, String attachmentName) throws MessagingException {
		MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(recipients);

		// use the default sending if no sender specified
		if (sender == null) {
			helper.setFrom(defaultFrom);
		} else {
			helper.setFrom(sender);
		}

		helper.setText(bodyText);
		helper.setSubject(subject);

		helper.addAttachment(attachmentName, resource);

		((JavaMailSenderImpl) mailSender).send(message);
	}

	public void sendMessage2(String[] recipients, String sender, FileSystemResource resource, String bodyText,
			String subject, String attachmentName) throws MessagingException {
		MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(recipients);

		// use the default sending if no sender specified
		if (sender == null) {
			helper.setFrom(defaultFrom);
		} else {
			helper.setFrom(sender);
		}

		helper.setText(bodyText, bodyText);
		helper.setSubject(subject);

		helper.addAttachment(attachmentName, resource);

		((JavaMailSenderImpl) mailSender).send(message);
	}

	/**
	 * Send a simple message based on a Freemarker String template.
	 * 
	 * @param msg
	 *            the message to populate
	 * @param emailSubjectTemplateString
	 *            the Freemarker template as String to use
	 * @param emailBodyTemplateString
	 *            the Freemarker template as String to use
	 * @param model
	 *            a map containing key/value pairs
	 */
	@Async
	@SuppressWarnings("unchecked")
	public void sendFreemarkerMessage(MimeMessage msg, String emailSubjectTemplateString,
			String emailBodyTemplateString, Map model) {
		// set default to html email
		sendFreemarkerMessage(msg, emailSubjectTemplateString, emailBodyTemplateString, model,
				"text/html; charset=utf-8");
	}

	/**
	 * Send a simple message based on a Freemarker String template.
	 * 
	 * @param msg
	 *            the message to populate
	 * @param emailSubjectTemplateString
	 *            the Freemarker template as String to use
	 * @param emailBodyTemplateString
	 *            the Freemarker template as String to use
	 * @param model
	 *            a map containing key/value pairs
	 */
	@Async
	@SuppressWarnings("unchecked")
	public void sendFreemarkerMessage(MimeMessage msg, String emailSubjectTemplateString,
			String emailBodyTemplateString, Map model, String mailType) {
		if (mailType != null && mailType.equals("plain")) {
			mailType = "text/plain; charset=utf-8";
		}
		String subject = null;
		String body = null;

		try {
			Template subjectTemplate = new Template("subjectTemplate", new StringReader(emailSubjectTemplateString),
					freeMarkerConfiguration);
			Template bodyTemplate = new Template("bodyTemplate", new StringReader(emailBodyTemplateString),
					freeMarkerConfiguration);
			subject = FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, model);
			body = FreeMarkerTemplateUtils.processTemplateIntoString(bodyTemplate, model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			msg.setSubject(subject);
			SimpleMailMessage smm = new SimpleMailMessage();
			msg.setContent(body, mailType);
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		send(msg);
	}

	@Async
	@SuppressWarnings("unchecked")
	public void sendFreemarkerMessageWithFileAttachments(EmailTemplate emailTemplate, Map<String, String> model)
			throws MessagingException, IOException, TemplateException {
		Template subjectTemplate = new Template("subjectTemplate", new StringReader(emailTemplate.getEmailSubject()),
				freeMarkerConfiguration);
		Template bodyTemplate = new Template("bodyTemplate", new StringReader(emailTemplate.getEmailBody()),
				freeMarkerConfiguration);
		String subject1 = FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, model);
		String body = FreeMarkerTemplateUtils.processTemplateIntoString(bodyTemplate, model);

		MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

		// use the true flag to indicate you need a multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(emailTemplate.getEmailTo().split(";|,"));
		helper.setFrom(emailTemplate.getEmailFrom());
		if (emailTemplate.getEmailCc() != null) {
			helper.setCc(emailTemplate.getEmailCc().split(";|,"));
		}
		if (emailTemplate.getEmailBcc() != null) {
			helper.setBcc(emailTemplate.getEmailBcc().split(";|,"));
		}

		helper.setText(body, body);
		helper.setSubject(subject1);

		for (Map.Entry<String, String> entry : model.entrySet()) {
			if (entry.getKey().contains("fileAttachment")) {
				FileSystemResource attachment = new FileSystemResource(entry.getValue());
				helper.addAttachment(attachment.getFilename(), attachment);
			}
		}

		((JavaMailSenderImpl) mailSender).send(message);
	}

	/**
	 * Send a simple message based on a Freemarker String template.
	 * 
	 * @param msg
	 *            the message to populate
	 * @param templateName
	 *            the Freemarker template name to use
	 * @param model
	 *            a map containing key/value pairs
	 */
	@Async
	@SuppressWarnings("unchecked")
	public void sendFreemarkerTemplateMessage(MimeMessage msg, String subjectStringTemplate, String templateName,
			Map model) {
		String subject = null;
		String body = null;

		try {
			Template template = freeMarkerConfiguration.getTemplate(templateName);
			// String subjectStringTemplate = (String)template.
			// getCustomAttribute("subject");
			Template subjectTemplate = new Template("subjectTemplate", new StringReader(subjectStringTemplate),
					freeMarkerConfiguration);
			subject = FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, model);
			body = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			msg.setSubject(subject);
			msg.setContent(body, "text/html; charset=utf-8");
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		send(msg);
	}

}
