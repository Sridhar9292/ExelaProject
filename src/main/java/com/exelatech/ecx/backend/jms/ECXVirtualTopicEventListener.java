package com.exelatech.ecx.backend.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.exelatech.ecx.backend.event.ECXEventListener;
import com.exelatech.ecx.backend.event.ECXEventListenerManager;
import com.exelatech.ecx.backend.event.ECXEventListenerPredicate;
import com.exelatech.ecx.backend.event.ECXEventListening;
import com.exelatech.ecx.backend.event.ECXEventMessageHandlerFactory;
import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.service.DashboardManager;
import com.exelatech.ecx.backend.service.ElasticSearchManager;
import com.exelatech.ecx.backend.service.EmailTemplateManager;
import com.exelatech.ecx.backend.service.ReturnDashboardManager;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class ECXVirtualTopicEventListener implements MessageListener {

	@Autowired
	private ElasticSearchManager<ECXEvent> eventService;
	@Autowired
	private DashboardManager dashboardManager;

	@Autowired
	private ReturnDashboardManager returnDashboardManager;
	@Autowired
	EmailTemplateManager emailTemplateManager;

	protected final transient Log log = LogFactory.getLog(getClass());

	ECXEventListenerManager listenerManager = new ECXEventListenerManager();

	boolean notExists(String str) {
		return StringUtils.isBlank(str);
	}

	boolean exists(String str) {
		return StringUtils.isNotBlank(str);
	}

	boolean equalsIgnoreCase(String str1, String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}

	public ECXVirtualTopicEventListener() {
		initListenerManager();
	}

	public void initListenerManager() {

		// TODO - the saveToElasticSearch(event) would have logically gone here.

		// convenience: multiple listeners that have the same predicate can reuse this
		// object.
		ECXEventListenerPredicate nonPrintStatusEventPredicate = new ECXEventListenerPredicate() {
			@Override
			public boolean match(ECXEvent event) {
				return (!equalsIgnoreCase(event.getEventName(), "PrintStatus")) ? true : false;
			}
		};

		listenerManager.listen(new ECXEventListening(nonPrintStatusEventPredicate, new ECXEventListener() {
			@Override
			public void handle(ECXEvent event) throws JSONException, Exception {
				dashboardManager.processEvent(event);
			}
		}));
		listenerManager.listen(new ECXEventListening(nonPrintStatusEventPredicate, new ECXEventListener() {
			@Override
			public void handle(ECXEvent event) throws JSONException, Exception {
				returnDashboardManager.processEvent(event);
			}
		}));

		listenerManager.listen(new ECXEventListening(new ECXEventListenerPredicate() {
			@Override
			public boolean match(ECXEvent event) {
				return true;
			} // Always run this listener.
		}, new ECXEventListener() {
			@Override
			public void handle(ECXEvent event) throws JSONException, Exception {
				emailTemplateManager.processEmail(event);
			}
		}));

	}

	@JmsListener(destination = "${backend.events.virtual.topic}")
	public void receiveMessage(final Message message) throws JMSException {
		// System.out.println("receiveMessage()");
		try {
			onMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception processing JMS message - onMessage(Message=" + message + ") Exception: " + e);
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			ECXEvent event = new ECXEventMessageHandlerFactory().getInstance(message).getECXEvent();
			saveToElasticSearch(event);
			listenerManager.dispatch(event);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception processing JMS message - onMessage(Message=" + message + ") Exception: " + e);
		}
	}

	@Async
	private void saveToElasticSearch(ECXEvent event) {
		boolean eventIdExists = eventService.exists(event.getId());
		if (eventIdExists) {
			String errorStr = new StringBuilder().append("duplicate event ID, same event already exists in ES - ")
					.append(event).toString();
			log.warn(errorStr);
		}
		try {
			eventService.save(event);
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
		}
	}

}
