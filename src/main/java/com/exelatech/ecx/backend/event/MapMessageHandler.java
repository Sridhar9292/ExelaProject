package com.exelatech.ecx.backend.event;

import java.util.Map;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;

import com.exelatech.ecx.backend.model.ECXEvent;

/**
 * Translates a ActiveMQMapMessage message into an ECXEvent object.
 * 
 * @author WilliamMcDonald
 * @see ECXEventVirtualTopicEventListener, ECXEventMessageHandler
 */
public class MapMessageHandler extends SimpleMessageHandler { 
	private ActiveMQMapMessage msg = null;
	@Override
	protected ActiveMQMessage getMessage() { return msg; }
	@Override
	public ECXEventMessageHandler setMessage(ActiveMQMessage message) throws JMSException { msg = (ActiveMQMapMessage) message; return this; }

	/**
	 * Add Message headers to event.
	 * 
	 * @param event
	 * @throws JMSException
	 */
	@Override
	protected void setHeaders(ECXEvent event) throws JMSException {
		try {
			Map<String, Object> map = msg.getContentMap();
			for (String key : map.keySet()) {
				event.put(key, map.get(key).toString());
			}
		}
		catch (JMSException je) {
			throw new JMSException(je + " extracting keySet from ECXEvent JMS message:" + getMessage());
		}
	}	
}
