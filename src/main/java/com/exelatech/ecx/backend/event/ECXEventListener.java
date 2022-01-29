package com.exelatech.ecx.backend.event;

import com.exelatech.ecx.backend.model.ECXEvent;

/**
 * Callback to process an ECXEvent.
 * 
 * Eg. to update the printdashboard when the event is a print event.
 * 
 * The ECXEventListenerManger uses a ECXEventListenerPredicate to match any 
 * event that an associated ECXEventListener should handle.  The association of 
 * ECXEventListenerPredicat to ECXEventListener is made using an ECXEventListening.
 * 
 * @author WilliamMcDonald
 * @see ECXEventListening, ECXEventListenerPredicate, ECXEventListenerManager
 */
public interface ECXEventListener {
	public void handle(ECXEvent event) throws Exception;
}
