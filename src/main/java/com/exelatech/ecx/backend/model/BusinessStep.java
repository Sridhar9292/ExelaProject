package com.exelatech.ecx.backend.model;

import com.exelatech.ecx.backend.statetable.State;
import com.exelatech.ecx.backend.statetable.StateTable;

public class BusinessStep {
    public enum StepColor {grey, primary, success, info, warning, danger}
    private StepColor color=StepColor.grey;
    private State state = StateTable.waitingState;

    public BusinessStep(){
    }

    public StepColor getColor() {
        return color;
    }

    public void setColor(StepColor color) {
        this.color = color;
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public void setStateTransition(State state){
        setState(state);
        switch (state.getType()){
            case INITIAL:
                color = StepColor.grey;
                break;
            case PROCESSING:
                color = StepColor.primary;
                break;
            case ISSUE:
                color = StepColor.info;
                break;
            case WARNING:
                color = StepColor.warning;
                break;
            case FINAL:
                color = StepColor.success;
                break;
            case ERROR:
                color = StepColor.danger;
                break;
            case NULL:
            default:
                color = StepColor.grey;
                break;
        }

    }

	@Override
	public String toString() {
		return "BusinessStep [color=" + color + ", state=" + state + "]";
	}
    
}
