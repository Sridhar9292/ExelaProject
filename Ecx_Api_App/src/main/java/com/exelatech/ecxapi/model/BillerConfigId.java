package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;


@Data
public class BillerConfigId implements Serializable {
    private static final long serialVersionUID = 1795850819228819925L;
    private String clientCode;
    private String clientLobCode;
}
