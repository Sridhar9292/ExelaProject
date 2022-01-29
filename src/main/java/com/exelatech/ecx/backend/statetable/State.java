package com.exelatech.ecx.backend.statetable;

public class State { 
	public enum Type { INITIAL, PROCESSING, FINAL, ISSUE, WARNING, ERROR, NULL };
	private Type type = Type.INITIAL;
		public Type getType() { return type; }
		public State setType(Type type) { this.type=type; return this; }

	private String name=null;
		public State setName(String name) { this.name=name; return this; }
		public String getName() { return name; }
	
	public State(String name, Type type) { this.name=name; setType(type); }
	public State(String name) { this.name=name; }
    private static int SCnt=1;
	public State() { this.name="S"+SCnt++; }

	public String toString() { return name + ":" + type; }

	@Override
	/**
	 * @returns True If the State's names and types are equal.
	 */
	public boolean equals(Object s) { 
		return (((State)s).name.equalsIgnoreCase(this.name) 
				&& (((State)s).getType() == this.type)); 
	}
}
