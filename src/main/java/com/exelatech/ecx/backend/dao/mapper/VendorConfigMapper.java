package com.exelatech.ecx.backend.dao.mapper;

import com.exelatech.ecx.backend.model.VendorConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VendorConfigMapper extends GenericMapper<VendorConfig, String> {
    VendorConfig getVendorHoliday(@Param("vendorCode") String vendorCode, @Param("holidayDate") String holidayDate);
    List<VendorConfig> getAllVendorHoliday(@Param("holidayDate") String holidayDate);
    VendorConfig getHoliday(@Param("holidayDate") String holidayDate);
    
}
