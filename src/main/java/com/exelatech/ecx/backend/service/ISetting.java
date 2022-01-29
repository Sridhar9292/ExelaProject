package com.exelatech.ecx.backend.service;

import com.exelatech.ecx.backend.domain.Setting;

public interface ISetting {
    void Load() throws Exception;
    Setting GetSetting(String Name);
}