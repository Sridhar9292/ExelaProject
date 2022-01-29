package com.exelatech.ecxapi.model;

import com.exelatech.ecxapi.statetable.State;
import com.exelatech.ecxapi.statetable.StateTable;

public class DataFeedWorkflow {
	private BusinessStep receiveStep = new BusinessStep();
	private BusinessStep importStep = new BusinessStep();
	private BusinessStep exportStep = new BusinessStep();
	private BusinessStep sendStep = new BusinessStep();

	public BusinessStep getReceiveStep() {
		return receiveStep;
	}

	public void setReceiveStep(BusinessStep receiveStep) {
		this.receiveStep = receiveStep;
	}

	public BusinessStep getExportStep() {
		return exportStep;
	}

	public void setExportStep(BusinessStep exportStep) {
		this.exportStep = exportStep;
	}

	public BusinessStep getImportStep() {
		return importStep;
	}

	public void setImportStep(BusinessStep importStep) {
		this.importStep = importStep;
	}

	public BusinessStep getSendStep() {
		return sendStep;
	}

	public void setSendStep(BusinessStep sendStep) {
		this.sendStep = sendStep;
	}

	public boolean isStateChanged(StateTable receiveStateTable, StateTable importStateTable,
			StateTable exportStateTable, StateTable sendStateTable, ECXEvent event) {
		boolean stateChanged = false;
		State currentReceiveState = getReceiveStep().getState();
		State currentImportState = getImportStep().getState();
		State currentExportState = getExportStep() != null ? getExportStep().getState() : null;

		State nextReceiveState = receiveStateTable.nextState(currentReceiveState, event);
		State nextImportState = importStateTable.nextState(currentImportState, event);
		State nextExportState = null;
		if (currentExportState != null) {
			nextExportState = exportStateTable.nextState(currentExportState, event);
		}
		State nextSendState = null;
		if (currentReceiveState.getType() != nextReceiveState.getType()
				|| currentImportState.getType() != nextImportState.getType()
				|| (currentExportState != null && currentExportState.getType() != nextExportState.getType())) {
			getReceiveStep().setStateTransition(nextReceiveState);
			getImportStep().setStateTransition(nextImportState);
			if (getExportStep() != null) {
				getExportStep().setStateTransition(nextExportState);
			}
			stateChanged = true;
		}
		return stateChanged;
	}

	@Override
	public String toString() {
		return "DataFeedWorkflow [receiveStep=" + receiveStep + ", importStep=" + importStep + ", exportStep="
				+ exportStep + ", sendStep=" + sendStep + "]";
	}

}
