package com.exelatech.ecx.backend.workflow;

import java.util.Arrays;
import java.util.List;

import com.exelatech.ecx.backend.statetable.State;
import com.exelatech.ecx.backend.statetable.StateTable;
import com.exelatech.ecx.backend.statetable.Transition;

public class SendStateTable {
	public static State sendingState 		= new State(	"Sending"	).setType(State.Type.PROCESSING);
	public static State sentState 			= new State(	"Sent"		).setType(State.Type.FINAL);

	public static StateTable instance() {
		List<Transition> transList = Arrays.asList(new Transition[] {
			new Transition( StateTable.waitingState,	ECXEvents.BillerRemitDataExported,				   sendingState),
			new Transition( 		   sendingState,	ECXEvents.BillerRemitDataSendComplete,			   sentState	),
			new Transition( 		   sendingState,	ECXEvents.BillerRemitDataDeliveryError,	StateTable.errorState	),
			new Transition( StateTable.errorState,		ECXEvents.BillerRemitDataSendComplete,			   sentState	),
		});
		StateTable receiveST = new StateTable();
		receiveST.addTransitions(transList);
		receiveST.setState(receiveST.getInitial());
		return receiveST;
	}
}