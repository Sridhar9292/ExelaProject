package com.exelatech.ecx.backend.workflow;

import java.util.Arrays;
import java.util.List;

import com.exelatech.ecx.backend.statetable.State;
import com.exelatech.ecx.backend.statetable.StateTable;
import com.exelatech.ecx.backend.statetable.Transition;



public class ExportStateTable {
	public static State exportingState 		= new State(	"Exporting"	).setType(State.Type.PROCESSING);
	public static State exportedState 		= new State(	"Exported"	).setType(State.Type.FINAL);

	public static StateTable instance() {
		List<Transition> transList = Arrays.asList(new Transition[] {
			new Transition( StateTable.waitingState,	ECXEvents.BillerRemitDataAvailable,					   exportingState	),
			new Transition( 		   exportedState,	ECXEvents.BillerRemitDataAvailable,					   exportingState	),	// process stragglers.

			new Transition( StateTable.waitingState,	ECXEvents.BillerRemitExportRequestReceived,			   exportingState	),
			new Transition( 		   exportedState,	ECXEvents.BillerRemitExportRequestReceived,			   exportingState	),	// process stragglers.

			new Transition( 		   exportingState,	ECXEvents.BillerError,						StateTable.errorState		),
			new Transition( 		   exportingState,	ECXEvents.ExportError,						StateTable.errorState		),
			new Transition( 		   exportingState,	ECXEvents.BillerRemitDataExportWarning,		StateTable.warningState	    ),	// 
			new Transition( 		   exportingState,	ECXEvents.BillerRemitDataExportError,		StateTable.errorState		),	// not all pairs within time limit.
			new Transition( 		   exportingState,	ECXEvents.BillerRemitDataExportComplete,			   exportedState	),	// everything paired up now.
			new Transition( StateTable.errorState,		ECXEvents.BillerRemitDataExportComplete,			   exportedState	),	// all paired up after time limit expired is o.k. too.
			new Transition( StateTable.warningState,	ECXEvents.BillerRemitDataExportComplete,			   exportedState	),
		});
		StateTable receiveST = new StateTable();
		receiveST.addTransitions(transList);
		receiveST.setState(receiveST.getInitial());
		return receiveST;
	}
}