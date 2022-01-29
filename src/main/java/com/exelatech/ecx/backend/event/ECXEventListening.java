package com.exelatech.ecx.backend.event;

import org.apache.commons.lang3.tuple.MutablePair;

/**
 * Convenence class to model a 1-to-1 mapping of an ECXEventListener 
 * and ECXEventPredicate using more specific language.
 * 
 * @author WilliamMcDonald
 * @see ECXEventListener, ECXEventListenerPredicate, ECXEventListenerManager
 * 
 */
@SuppressWarnings("serial")
public class ECXEventListening extends MutablePair<ECXEventListenerPredicate, ECXEventListener> {
	public ECXEventListening(ECXEventListenerPredicate predicate, ECXEventListener listener) { super(predicate, listener); }
	public ECXEventListenerPredicate getPredicate() { return this.getKey(); }
	public ECXEventListenerPredicate setPredicate(ECXEventListenerPredicate predicate) { this.setLeft(predicate); return predicate; }
	public ECXEventListener getListener() { return this.getValue(); }
	public ECXEventListener setListener(ECXEventListener listener) { this.setValue(listener); return listener; }
}
