package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ErrorCodes implements Serializable {
    private static final long serialVersionUID = 1943861946564182319L;
    private String errorCode;
    private String errorDesc;
    private String errorCause;
    private String errorResolution;
}
