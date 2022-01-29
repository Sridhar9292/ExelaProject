package com.exelatech.ecx.backend.jms;

import com.exelatech.ecx.backend.model.ECXEvent;
import com.exelatech.ecx.backend.monitor.EventMonitorListener;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by VenkataDurgavarjhula on 11/13/2015.
 */
@Configurable
public class EventMonitorListenerImpl implements EventMonitorListener {
    private JmsTemplate jmsTemplate;

    public EventMonitorListenerImpl(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }
    @Override
    public EventMonitorListener send(ECXEvent event) {
        jmsTemplate.convertAndSend(event);
        return this;
    }

    @Override
    public ECXEvent receive() {
        return null;
    }
}
