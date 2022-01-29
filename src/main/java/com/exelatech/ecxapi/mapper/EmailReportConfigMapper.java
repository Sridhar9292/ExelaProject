package com.exelatech.ecxapi.mapper;

import java.util.List;

import com.exelatech.ecxapi.model.EmailReportConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailReportConfigMapper extends GenericMapper<EmailReportConfig, String> {
    List<EmailReportConfig> getAll(String subaccountCode);

    EmailReportConfig get(String subaccountCode, String emailReportType);

    List<EmailReportConfig> search(String subaccountCode, String searchTerm);

    EmailReportConfig exists(String subaccountCode, String emailReportType);

    int remove(EmailReportConfig emailReportConfig);

    int removeBySubaccountCode(String subaccountCode);

}
