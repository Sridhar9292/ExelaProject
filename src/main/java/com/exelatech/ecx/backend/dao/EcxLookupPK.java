package com.exelatech.ecx.backend.dao;

import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class EcxLookupPK implements Serializable {
    /**
         *
         */
    private static final long serialVersionUID = 1L;

    @Column(name = "TYPE")
    private String Type;

    @Column(name = "SUB_TYPE")
    private String SubType;

    @Column(name = "NAME")
    private String Name;

    @Column(name = "VALUE")
    private String Value;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSubType() {
        return SubType;
    }

    public void setSubType(String subType) {
        SubType = subType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public EcxLookupPK() {
    }
}