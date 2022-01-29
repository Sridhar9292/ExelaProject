package com.exelatech.ecxapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Vendor implements Serializable {
    private static final long serialVersionUID = -5481118751262255746L;
    private String vendorCode;
    private String enabled;

    public Vendor(String vendorCode){
        this.vendorCode = vendorCode;
    }
}
