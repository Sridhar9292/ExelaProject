package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.PrintAppCodes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrintAppCodesMapper extends GenericMapper<PrintAppCodes, String> {

    List<PrintAppCodes> getAll(String appCodeType);

    List<PrintAppCodes> getAllAppcode();

    PrintAppCodes get(String appCodeType, String appCode);

    int remove(String appCodeType, String appCode);

    List<PrintAppCodes> search(String appCodeType, String searchTerm);

    PrintAppCodes exists(String appCodeType, String appCode);
}
