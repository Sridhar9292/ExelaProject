package com.exelatech.ecx.backend.event;

import com.exelatech.ecx.backend.model.ECXEvent;

/**
 * Encapsulates an instance algorithm for matching an event.  
 * 
 * For example, the match method could look to see if the event is a 
 * print event by checking the event.get(ECXEvent.PRINT_STATUS) header.
 * 
 * @author WilliamMcDonald
 * @see ECXEventListening, ECXEventListener, ECXEventListenerManager
 * 
 */
public interface ECXEventListenerPredicate {
	public boolean match(ECXEvent event);
}	
