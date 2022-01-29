package com.exelatech.ecx.backend.service;


public interface ISplunk {
    
    String getJobId() throws Exception;
    String getResults() throws Exception;   
}