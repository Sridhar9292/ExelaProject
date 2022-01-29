package com.exelatech.ecx.backend.statetable;

import com.exelatech.ecx.backend.model.ECXEvent;

public interface Action {
	public void perform(ECXEvent event);
}
//public class Action {
//	private int ACnt = 1;
//	private String name = null;
//		public String getName() { return name; }
//	
//	private Properties props = new Properties();
//		public Action setProperty(String key, String value) { props.setProperty(key, value); return this; }
//		public String getProperty(String key) { return props.getProperty(key); }
//
//	public Action(String name) { this.name=name; }
//	public Action() { this.name = "A" + ACnt++; }
//
//	public String toString() { return getName(); }
//	@Override
//	public boolean equals(Object a) { return ((Action)a).getName().equalsIgnoreCase(this.getName()); }
//}
