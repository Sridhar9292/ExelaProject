package com.exelatech.ecx.backend.service;

import java.util.ArrayList;

import com.exelatech.ecx.backend.domain.MoveItc;
import com.exelatech.ecx.backend.domain.RpdsInput;
import com.exelatech.ecx.backend.domain.T2App;

public interface ICompletable {
    void CompleteFromMoveIt(ArrayList<MoveItc> data);
    void CompleteFromT2App(ArrayList<T2App> data);   
    void CompleteFromRpdsInput(ArrayList<RpdsInput> data);   
}