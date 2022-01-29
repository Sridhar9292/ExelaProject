package com.exelatech.ecx.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import freemarker.template.Configuration;

@org.springframework.context.annotation.Configuration
@PropertySource("classpath:mail.properties")
public class MailEngineConfig {

	@Value("${mail.default.from}")
	private String from;

	@Value("${mail.host}")
	private String host;

	@Value("${mail.port}")
	private int port;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl impl = new JavaMailSenderImpl();
		impl.setHost(host);
		impl.setPort(port);
		impl.setDefaultEncoding("UTF-8");
		return impl;
	}

	@Bean
	@Scope("prototype")
	public SimpleMailMessage mailMessage() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(from);
		return msg;
	}

	@Bean
	public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactory() {
		FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
		factoryBean.setTemplateLoaderPath("classpath:/META-INF/freemarker");
		return factoryBean;
	}

	@Bean
	@Scope("prototype")
	public Configuration freeMarkerConfig() throws Exception {
		return freeMarkerConfigurationFactory().createConfiguration();
	}
}
