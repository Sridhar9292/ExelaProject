package com.exelatech.ecx.backend.controller;

import org.apache.log4j.Logger;

import com.exelatech.ecx.backend.domain.SenderReceiver;
import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.domain.SplunkResult;
import com.exelatech.ecx.backend.service.IRequest;
import com.exelatech.ecx.backend.util.JsonUtl;

public class SenderReceiverController implements IRequest {
    static Logger log = Logger.getLogger(SenderReceiverController.class.getName());
    
    private SplunkResult<SenderReceiver> data;
    Setting _setting;
    
    public SplunkResult<SenderReceiver> getData() {
        return data;
    }

    public void setData(SplunkResult<SenderReceiver> data) {
        this.data = data;
    }

    @Override
    public void Setting(com.exelatech.ecx.backend.domain.Setting setting) {
      _setting = setting;
    }

    @Override
    public void RequestData() throws Exception {
      SplunkController proFTPD = new SplunkController(this._setting);
        proFTPD.getJobId();
        String pp = proFTPD.getResults();
        data = JsonUtl.SplSenderReceiverStrToObj(pp);
        log.info(data.getResults().size() + " records were retrived for SENDER_RECEIVER");

    }
    
}