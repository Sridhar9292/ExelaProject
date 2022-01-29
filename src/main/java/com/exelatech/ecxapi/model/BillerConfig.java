package com.exelatech.ecxapi.model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BillerConfig implements Serializable {
    private static final long serialVersionUID = -5618044913295373173L;
    private BillerConfigId id;
    private String bcBillerCode;
    private String bcBillerAlias;
    private String bcBillerSite;
    private String bcBillerName;
    private String bcAccountingEnabled;
    private Date entryDate;
    private Date modifyDate;
    private String userAbbrev;
    private String bcDelivery;
    private String billerEnabled;
    private List<Vendor> vendors = new ArrayList<>();


    public boolean isWuenabled(){
        boolean enabled = false;
        for(Vendor v : vendors){
            if(v.getVendorCode().equals("WU")){
                enabled = v.getEnabled().equals("TRUE")?true:false;
                break;
            }
        }
        return enabled;
    }

    public boolean isRppsenabled(){
        boolean enabled = false;
        for(Vendor v : vendors){
            if(v.getVendorCode().equals("RPPS")){
                enabled = v.getEnabled().equals("TRUE")?true:false;
                break;
            }
        }
        return enabled;
    }

	public boolean isFiservenabled(){
        boolean enabled = false;
        for(Vendor v : vendors){
            if(v.getVendorCode().equals("FISERV")){
                enabled = v.getEnabled().equals("TRUE")?true:false;
                break;
            }
        }
        return enabled;
    }
    public boolean isFisenabled(){
        boolean enabled = false;
        for(Vendor v : vendors){
            if(v.getVendorCode().equals("FIS")){
                enabled = v.getEnabled().equals("TRUE")?true:false;
                break;
            }
        }
        return enabled;
    }
    public boolean isOpcenabled(){
        boolean enabled = false;
        for(Vendor v : vendors){
            if(v.getVendorCode().equals("OPC")){
                enabled = v.getEnabled().equals("TRUE")?true:false;
                break;
            }
        }
        return enabled;

    }
    public boolean isAcienabled(){
        boolean enabled = false;
        for(Vendor v : vendors){
            if(v.getVendorCode().equals("ACI")){
                enabled = v.getEnabled().equals("TRUE")?true:false;
                break;
            }
        }
        return enabled;
    }

    public boolean isWuunavailable(){
        return !vendors.contains(new Vendor("WU"));
    }
    public boolean isRppsunavailable(){
        return !vendors.contains(new Vendor("RPPS"));
    }
    public boolean isFiservunavailable(){
        return !vendors.contains(new Vendor("FISERV"));
    }
    public boolean isFisunavailable(){
        return !vendors.contains(new Vendor("FIS"));
    }
    public boolean isOpcunavailable(){
        return !vendors.contains(new Vendor("OPC"));
    }
    public boolean isAciunavailable(){
        return !vendors.contains(new Vendor("ACI"));
    }
}
