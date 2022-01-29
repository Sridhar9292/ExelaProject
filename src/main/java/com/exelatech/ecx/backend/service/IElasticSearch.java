package com.exelatech.ecx.backend.service;

import java.util.ArrayList;

import com.exelatech.ecx.backend.domain.Setting;

public interface IElasticSearch {
    
    void Setting(Setting setting);
    <T extends IKey>  void BuildBulkStatment(ArrayList<T> data);
    void Bulk();

}