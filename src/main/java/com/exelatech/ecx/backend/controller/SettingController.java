package com.exelatech.ecx.backend.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.service.ISetting;
import com.exelatech.ecx.backend.util.JsonUtl;

public class SettingController implements ISetting {
    static Logger log = Logger.getLogger(TransferController.class.getName());
    private Setting[] _setting;

    @Override
    public void Load() throws Exception {
        
        // File file = new File("C:\\Users\\FMartinez\\Desktop\\FABIAN - WorkSpace\\PIR\\splunktransfer\\src\\main\\resources\\setting.json");
        File file = new File("setting.json");

        if(!file.exists()){
            file = new File(URLDecoder.decode(getClass().getClassLoader().getResource(".").getPath() + "setting.json", "utf-8"));
        }
        try {
            log.info("Loading config file");

            _setting = JsonUtl.JsonStrToObject(ReadFile(file), Setting[].class);
        } catch (Exception e) {
            log.fatal("Error loading configuration file");
            throw new Exception("Error loading configuration file");
        }
    }

    @Override
    public Setting GetSetting(String Name) {
        for (int index = 0; index < _setting.length; index++) {
            Setting _sett = _setting[index];
            if (_sett.getKeyName().equals(Name))
                return _sett;
        }
        return null;
    }

    private String ReadFile(File file) throws IOException {
        if (file == null)
            return null;

        try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {

            String line, lines = "";
            while ((line = br.readLine()) != null) {
                lines += line;
            }
            return lines;
        }
    }

    public SettingController() {
    }

}