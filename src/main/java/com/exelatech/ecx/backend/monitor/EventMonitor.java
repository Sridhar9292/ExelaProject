package com.exelatech.ecx.backend.monitor;

import com.exelatech.ecx.backend.model.ECXEvent;

public interface EventMonitor {
	public EventMonitor notify(ECXEvent event) throws InterruptedException;
	public EventMonitor setListener(EventMonitorListener listener);
	public EventMonitor setTimeout(long timeout);
	public EventMonitor setPairingTimeout(long timeout);
} 
