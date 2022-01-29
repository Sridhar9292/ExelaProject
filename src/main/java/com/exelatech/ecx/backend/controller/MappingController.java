package com.exelatech.ecx.backend.controller;

import com.exelatech.ecx.backend.service.IMapping;

public class MappingController <T>{

   private IMapping<T> _mapping;

   public MappingController(IMapping<T> mapping){
       _mapping = mapping;
    }

    void RequestData(){
        _mapping.RequestData();

    }
    void Mapping(T DataToMap){
       _mapping.Mapping(DataToMap); 
    }

    public MappingController() {
    }


    
}