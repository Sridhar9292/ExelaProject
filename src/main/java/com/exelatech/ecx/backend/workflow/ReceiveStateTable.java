package com.exelatech.ecx.backend.workflow;

import java.util.Arrays;
import java.util.List;

import com.exelatech.ecx.backend.statetable.State;
import com.exelatech.ecx.backend.statetable.StateTable;
import com.exelatech.ecx.backend.statetable.Transition;



public class ReceiveStateTable {
	public static State receivingState 		= new State(	"Receiving"	).setType(State.Type.PROCESSING);
	public static State receivedState 		= new State(	"Received"	).setType(State.Type.FINAL);
	
	public static StateTable instance() {
		List<Transition> transList = Arrays.asList(new Transition[] {
			new Transition(	StateTable.waitingState, 	ECXEvents.dfDetected, 					   receivingState	),
			new Transition(	StateTable.waitingState, 	ECXEvents.PosFileDataImport, 			   receivedState	),
			new Transition(	StateTable.waitingState, 	ECXEvents.StopFileDataImport, 			   receivedState	),
			new Transition(			   receivingState,	ECXEvents.dfArchived, 					   receivedState	),
			new Transition(			   receivingState,	ECXEvents.dfDetectionError, 	StateTable.errorState		),
			new Transition(			   receivingState,	ECXEvents.dfValidated,					   receivedState	),
			new Transition(	StateTable.waitingState,	ECXEvents.dfMissedSLA,			StateTable.issueState		),
			new Transition(	StateTable.issueState,		ECXEvents.dfResolvedMissedSLA,	StateTable.waitingState		),
			new Transition(	StateTable.issueState,		ECXEvents.dfDetected, 					   receivingState	),	// If a file is submitted to remove the issue.
			new Transition(	StateTable.errorState,		ECXEvents.dfDetected, 					   receivingState	),	// If a file is resubmitted to remove the error.
		});
		StateTable receiveST = new StateTable();
		receiveST.addTransitions(transList);
		receiveST.setState(receiveST.getInitial());
		return receiveST;
	}
}