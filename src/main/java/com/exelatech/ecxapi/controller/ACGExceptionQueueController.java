package com.exelatech.ecxapi.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.model.ACGExceptionQueue;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/acg-exception")
public class ACGExceptionQueueController {

	@Value("${spring.activemq.user}")
	private String activeMqUser;

	@Value("${spring.activemq.password}")
	private String activeMqPassword;

	@Value("${spring.activemq.broker-url}")
	private String activeMqUrl;

	@Autowired
	private JmsTemplate jmsTemplate;

	@PreAuthorize("hasAnyAuthority('_print:acgExceptionsDashboard:manage','_print:acgExceptionsDashboard:view')")
	@GetMapping
	public List<ACGExceptionQueue> getACGDashboard() throws NotFoundException {

		List<ACGExceptionQueue> msgList = new ArrayList<>();
		try {
			jmsTemplate.browse("ACG.ExceptionQueue", new BrowserCallback<String>() {
				public String doInJms(Session session, QueueBrowser browser) throws JMSException {
					Enumeration<TextMessage> messages = browser.getEnumeration();
					while (messages.hasMoreElements()) {
						ACGExceptionQueue aCGExceptionQueue = new ACGExceptionQueue();
						TextMessage message = messages.nextElement();
						aCGExceptionQueue.setMsgId(message.getJMSMessageID());
						aCGExceptionQueue.setCorelationId(message.getJMSCorrelationID());
						aCGExceptionQueue.setPriority(message.getJMSPriority());
						aCGExceptionQueue.setRedelivered(message.getJMSRedelivered());
						aCGExceptionQueue.setTimestamp(message.getJMSTimestamp());
						aCGExceptionQueue.setType(message.getJMSType());
						aCGExceptionQueue.setMsg(message.getText());

						msgList.add(aCGExceptionQueue);

					}
					return null;
				}

			});
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		} 
		return msgList;
	}

	@PreAuthorize("hasAnyAuthority('_print:acgExceptionsDashboard:manage')")
	@GetMapping("/move-inbound-queue")
	public ResponseEntity<String> acgRetryAppIdFailed() throws NotFoundException {
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(activeMqUser, activeMqPassword, activeMqUrl);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("ecx.event.topic");
			// Added as a producer
			MessageProducer producer = session.createProducer(topic);
			TextMessage msg = session.createTextMessage();
			msg.setStringProperty("eventName", "moveToInboundQ");
			producer.send(msg);
			System.out.println("ACG Dashboard - moveToInboundQ process");
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}

}
