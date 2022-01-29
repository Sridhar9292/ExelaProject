package com.exelatech.ecx.backend.event;

import java.io.IOException;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.commons.lang.StringUtils;

import com.exelatech.ecx.backend.model.ECXEvent;

/**
 * Translates a ActiveMQMessage message into an ECXEvent object.
 * 
 * @author WilliamMcDonald
 * @see ECXEventVirtualTopicEventListener, ECXEventMessageHandler
 */
public class SimpleMessageHandler implements ECXEventMessageHandler {
	private ActiveMQMessage msg = null;
	protected ActiveMQMessage getMessage() { return msg; } 
	public ECXEventMessageHandler setMessage(ActiveMQMessage message) throws JMSException { msg = (ActiveMQMessage) message; return this;}

	public ECXEvent getECXEvent() throws JMSException {
		ECXEvent event = new ECXEvent();
		event.put(ECXEvent.TIMESTAMP, String.valueOf(getMessage().getJMSTimestamp()));
		setHeaders(event);
		assertEventNameNotNull(event.getEventName());
		return event;
	}
	
	/**
	 * eventname should not be null
	 * 
	 * @param eventName
	 * @throws JMSException
	 */
	protected void assertEventNameNotNull(String eventName) throws JMSException {
        if(StringUtils.isBlank(eventName)){ 
            String errorStr = new StringBuilder().append("JMS Message's eventName header is missing/null/blank - message=").append(getMessage()).toString();
            throw new JMSException(errorStr);
        }
	}

	/**
	 * Add Message headers to event.
	 * 
	 * @param event
	 * @throws JMSException
	 */
	protected void setHeaders(ECXEvent event) throws JMSException {
		try {
			for (String key : getMessage().getProperties().keySet()) { 
				event.put(key, getMessage().getStringProperty(key));
			}
		}
		catch (IOException ioe) {
			throw new JMSException(ioe + " extracting keySet from ECXEvent JMS message:" + getMessage());
		}
	}	
}
