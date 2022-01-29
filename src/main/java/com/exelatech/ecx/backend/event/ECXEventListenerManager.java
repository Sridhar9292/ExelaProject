package com.exelatech.ecx.backend.event;

import java.util.ArrayList;
import java.util.List;

import com.exelatech.ecx.backend.model.ECXEvent;

/**
 * The ECXEventListenerManger maintains a list of ECXEventListening objects.  
 * When an ECXEvent is received, the ECXEventListenerManager cycles through
 * the list of ECXEventListings, and for each one, uses the ECXEventListenerPredicate 
 * to match any event that the associated ECXEventListener should handle.  
 * 
 * Eg. to update the printdashboard when the event is a regular print event.
 * 
 * NOTE: The association of ECXEventListenerPredicate to ECXEventListener is 
 * made using an ECXEventListening (KVPair container with custom methods).
 *  
 * @author WilliamMcDonald
 * @see ECXEventListening, ECXEventListenerPredicate, ECXEventListener
 * 
 */
public class ECXEventListenerManager {
	List<ECXEventListening> listenings = new ArrayList<ECXEventListening>();
	public void listen(ECXEventListening listening) { listenings.add(listening); }
	public void dispatch(ECXEvent event) throws Exception {
		for (ECXEventListening listening : listenings) {
			if (listening.getPredicate().match(event))
				listening.getListener().handle(event);
		}
	}
}	
