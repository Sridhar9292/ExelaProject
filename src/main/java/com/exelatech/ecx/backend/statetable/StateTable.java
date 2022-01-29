package com.exelatech.ecx.backend.statetable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.exelatech.ecx.backend.model.ECXEvent;

//@Component
public class StateTable {
	
	// Common (Default) States
	public static State waitingState 		= new State(	"Waiting"	).setType(State.Type.INITIAL);
	public static State sendingState 		= new State(	"Processing").setType(State.Type.PROCESSING);
	public static State sentState 			= new State(	"Success"	).setType(State.Type.FINAL);
	public static State errorState 			= new State(	"Error"		).setType(State.Type.ERROR);
	public static State issueState 			= new State(	"Issue"		).setType(State.Type.ISSUE);
	public static State warningState 		= new State(	"Warning"	).setType(State.Type.WARNING);
	public static State unkownState 		= new State(	"Unkown"	).setType(State.Type.NULL);

	private State currentState = unkownState;
		public void setState(State s1) { currentState=s1; }
		public State getState() { return currentState; }
		public Set<State> getStates() { 
			Set<State> states = new HashSet<State>();
			for (Transition trans : getTransitions())
			{
				states.add(trans.getFrom());
				states.add(trans.getTo());
			}
			return states;
		}
		public State getInitial() { 
			for (State state : getStates()) {
				if (state.getType()==State.Type.INITIAL) {
					return state; 
				}
			}
			return unkownState;
		}
	private List<Transition> transitions = new ArrayList<Transition>();
		public List<Transition> getTransitions() { return this.transitions; }

	public State nextState(ECXEvent event) { return currentState = nextState(currentState, event);}
	public State nextState(List<ECXEvent> events) { return currentState = nextState(currentState, events); }
	public State nextState(State from, ECXEvent event) { return (isTransitionExists(from, event) ? getTo(from, event) : from); }
	public State nextState(State from, List<ECXEvent> events) { 
		State tmpState = from;
		for (ECXEvent event : events)  {
			if (isTransitionExists(tmpState, event)) 
				tmpState = nextState(tmpState, event);
		} return tmpState;
	}

	public boolean isTransitionExists(State from, ECXEvent event) { return (getTo(from, event) != null); }
	public boolean isTransitionExists(Transition trans) { return (getTo(trans.getFrom(), trans.getEvent()) != null); }
	private State getTo(State from, ECXEvent event) { Transition T = getTransition(from, event); return (T != null)?T.getTo():null;}
	public void addTransitions(List<Transition> transList) { for (Transition trans : transList) addTransition(trans); }
	public void addTransition(Transition trans) {  // override any existing matching transitions.
		if (isTransitionExists(trans)) removeTransition(trans); transitions.add(trans); }
	public void removeTransition(Transition trans) { Transition T = getTransition(trans.getFrom(), trans.getEvent()); if (T != null) transitions.remove(T); }
	public Transition getTransition(State from, ECXEvent event) { 
		for (Transition trans : transitions) {
			ECXEvent e = trans.getEvent();
			State f = trans.getFrom();
			if (event.equals(e) && from.equals(f))
				return trans;
		} return null;
	}
	public String toString() { 
		StringBuffer buf = new StringBuffer(); 
		for (Transition trans : transitions) 
			buf.append(trans.toString()).append("\n"); 
		return buf.toString(); 
	}
	private String name=null;
	public String getName() { return this.name; }
	public StateTable setName(String name) { this.name=name; return this; }
}