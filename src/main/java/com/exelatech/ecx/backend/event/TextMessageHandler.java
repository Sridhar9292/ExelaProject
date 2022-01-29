package com.exelatech.ecx.backend.event;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;

import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.util.CommonUtil;

/**
 * Translates a ActiveMQTextMessage message into an ECXEvent object.
 * 
 * @author WilliamMcDonald
 * @see ECXEventVirtualTopicEventListener, ECXEventMessageHandler
 */
public class TextMessageHandler extends SimpleMessageHandler {
	private static final Logger logger = LogManager.getLogger();

	private ActiveMQTextMessage msg = null;
	@Override
	protected ActiveMQMessage getMessage() { return msg; }
	@Override
	public ECXEventMessageHandler setMessage(ActiveMQMessage message) throws JMSException { msg = (ActiveMQTextMessage) message; return this;}

	@Override
	public ECXEvent getECXEvent() throws JMSException {
		logger.traceEntry();
		logger.debug("ECXEvent msg={}", msg.getText());
		ECXEvent event = null;
		String eventName = msg.getStringProperty(ECXEvent.NAME); 

		try { // Message payload is either a JSON object or...
			if (CommonUtil.isJSONValid(msg.getText())) {
				// TODO Save printlookup with JobNumber and PP_JobID
				event = CommonUtil.getJsonAsEvent(eventName, msg.getText()); 
				setHeaders(event);  
				event.put(ECXEvent.TIMESTAMP, String.valueOf(getMessage().getJMSTimestamp()));
				copyHeaderValue(event, "PrintJobNumber", ECXEvent.JOB_NUMBER);
				assertEventNameNotNull(eventName);
			} else {
				event = super.getECXEvent();
			}
		} catch (JSONException je) { // ...a simple Text msg.
			event = super.getECXEvent();
		}
        return logger.traceExit(event);
	}

	/**
	 * Copy non blank/null value from one header to another.
	 * 
	 * @param event
	 * @param fromHeader
	 * @param toHeader
	 * @return true if the copy succeeded.  false if the fromHeader value is blank or null.
	 */
	protected boolean copyHeaderValue(ECXEvent event, String fromHeader, String toHeader) {
		String fromValue = event.get(fromHeader);
		if (StringUtils.isNotBlank(fromValue)) {
			event.put(toHeader, fromValue);
			return true;
		}
		return false;
	}
}
