package com.exelatech.ecx.backend.service;

public interface IMapping<T> {
    void RequestData();
    void Mapping(T DataToMap);
    
}