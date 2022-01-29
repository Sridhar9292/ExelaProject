package com.exelatech.ecx.backend.service;

import java.util.ArrayList;

import com.exelatech.ecx.backend.domain.Setting;

public interface ISplunkProcessing {

    void Setting(Setting setting);
    void RequestData() throws Exception;
    void DataTransformation();
    void DataMapping();
    void SetTimeEpoch();
    <T extends IProdFTPD>  void SetQueryFilter(ArrayList<T> data);


    

}