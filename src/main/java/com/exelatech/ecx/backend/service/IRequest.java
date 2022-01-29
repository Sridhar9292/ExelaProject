package com.exelatech.ecx.backend.service;
import com.exelatech.ecx.backend.domain.Setting;

public interface IRequest {
    void Setting(Setting setting);
    void RequestData() throws Exception;
}