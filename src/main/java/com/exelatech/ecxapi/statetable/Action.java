package com.exelatech.ecxapi.statetable;

import com.exelatech.ecxapi.model.ECXEvent;

public interface Action {
	public void perform(ECXEvent event);
}
