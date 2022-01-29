package com.exelatech.ecx.backend.statetable;

import java.util.Properties;

public class Guard {
	private int GCnt = 1;
	private String name = null;
		public String getName() { return name; }

	private Properties props = new Properties();
		public Guard setProperty(String key, String value) { props.setProperty(key, value); return this; }
		public String getProperty(String key) { return props.getProperty(key); }

	public Guard(String name) { this.name=name; }
	public Guard() { this.name = "G" + GCnt++; }

	public String toString() { return getName(); }
	@Override
	public boolean equals(Object g) { return ((Guard)g).getName().equalsIgnoreCase(this.getName()); }
}
