package com.exelatech.ecx.backend.workflow;

import java.util.Arrays;
import java.util.List;

import com.exelatech.ecx.backend.statetable.State;
import com.exelatech.ecx.backend.statetable.StateTable;
import com.exelatech.ecx.backend.statetable.Transition;


public class ImportStateTable {
	public static State importingState 		= new State(	"Importing"	).setType(State.Type.PROCESSING);
	public static State importedState 		= new State(	"Imported"	).setType(State.Type.FINAL);

	public static StateTable instance() {
		List<Transition> transList = Arrays.asList(new Transition[] {
//			new Transition( waitingState,	ECXEvents.DataFeedDetectionError,	idleState),  // if there is a non recoverable error in the previous step, the rest could be 'Stopped'.
			new Transition( StateTable.waitingState,	ECXEvents.dfValidate,					   importingState	),
			new Transition( StateTable.waitingState,	ECXEvents.VendorInputFileSubmitted,			   importingState	),
			new Transition( StateTable.waitingState,	ECXEvents.VendorRemitFileSubmitted,			   importingState	),
			new Transition(	StateTable.waitingState, 	ECXEvents.PosFileDataImported, 			   	importedState	),
			new Transition(	StateTable.waitingState, 	ECXEvents.StopFileDataImported, 			   	importedState	),
			new Transition( 		   importingState,	ECXEvents.VendorRemitFileProcessed,			   importedState	),
			new Transition( 		   importingState,	ECXEvents.dfFailedValidation,	StateTable.errorState		),
			new Transition( 		   importingState,	ECXEvents.SplitError,				StateTable.errorState		),
			new Transition( 		   importingState,	ECXEvents.ImportError,				StateTable.errorState		),
			new Transition( StateTable.waitingState,	ECXEvents.Error,				StateTable.errorState		),
		});
		StateTable receiveST = new StateTable();
		receiveST.addTransitions(transList);
		receiveST.setState(receiveST.getInitial());
		return receiveST;
	}
}