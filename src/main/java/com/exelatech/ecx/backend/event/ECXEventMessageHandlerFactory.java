package com.exelatech.ecx.backend.event;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

/**
 * Factory which returns the correct JMS-to-ECXEvent Message Handler which 
 * translates the JMS message into an ECXEvent message.
 * 
 * @author WilliamMcDonald
 * @see ECXEventVirtualTopicEventListener, ECXEventMessageHandler
 */
// TODO - make factory external java file and static?
public class ECXEventMessageHandlerFactory {
	public ECXEventMessageHandler getInstance(Message message) throws JMSException {
		if(message instanceof ActiveMQMapMessage){
			return new MapMessageHandler().setMessage((ActiveMQMapMessage)message);
		}else if(message instanceof ActiveMQTextMessage){
			return new TextMessageHandler().setMessage((ActiveMQTextMessage)message);
        }else if(message instanceof ActiveMQMessage){
			return new SimpleMessageHandler().setMessage((ActiveMQMessage)message);
        }
		throw new JMSException("No ECXEventMessageHandler found for ECXEvent JMS message type "+message.getClass().toString());
	}
}