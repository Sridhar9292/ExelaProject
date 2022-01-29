package com.exelatech.ecx.backend.monitor;

import com.exelatech.ecx.backend.model.ECXEvent;

public interface EventMonitorListener {
	public EventMonitorListener send(ECXEvent event); 
	public ECXEvent receive();
}
