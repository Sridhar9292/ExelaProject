package com.exelatech.ecx.backend.event;

import javax.jms.JMSException;

import com.exelatech.ecx.backend.model.ECXEvent;

/**
 * Translates a JMS message into an ECXEvent object.
 * 
 * @author WilliamMcDonald
 * @see ECXEventVirtualTopicEventListener, 
 */
public interface ECXEventMessageHandler {
	ECXEvent getECXEvent() throws JMSException;
}
