package com.exelatech.ecxapi.statetable;

import java.util.List;

import com.exelatech.ecxapi.model.ECXEvent;

public class Transition {
	private State fromState, toState;

	public State getFrom() {
		return this.fromState;
	}

	public State getTo() {
		return this.toState;
	}

	private ECXEvent event;

	public ECXEvent getEvent() {
		return this.event;
	}

	private List<Action> actions;

	public List<Action> getActions() {
		return actions;
	}

	public Transition addAction(Action action) {
		actions.add(action);
		return this;
	}

	public Transition removeAction(Action action) {
		actions.remove(action);
		return this;
	}

	private List<Guard> guards;

	public List<Guard> getGuard() {
		return guards;
	}

	public Transition addGuard(Guard action) {
		guards.add(action);
		return this;
	}

	public Transition removeGuard(Guard action) {
		guards.remove(action);
		return this;
	}

	public Transition() {
	}

	public Transition(State from, ECXEvent event, State to) {
		this.fromState = from;
		this.toState = to;
		this.event = event;
	}

	public Transition from(State fromState) {
		this.fromState = fromState;
		return this;
	}

	public Transition to(State toState) {
		this.toState = toState;
		return this;
	}

	public Transition on(ECXEvent onEvent) {
		this.event = onEvent;
		return this;
	}

	public Transition when(Guard guard) {
		this.guards.add(guard);
		return this;
	}

	public Transition perform(Action action) {
		this.actions.add(action);
		return this;
	}

	public String toString() {
		return fromState + ":" + event + "->" + toState;
	}
}