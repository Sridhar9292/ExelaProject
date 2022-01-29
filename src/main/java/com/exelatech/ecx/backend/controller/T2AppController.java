package com.exelatech.ecx.backend.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.domain.SplunkResult;
import com.exelatech.ecx.backend.domain.T2App;
import com.exelatech.ecx.backend.service.IProdFTPD;
import com.exelatech.ecx.backend.service.ISplunkProcessing;
import com.exelatech.ecx.backend.util.JsonUtl;

public class T2AppController implements ISplunkProcessing {
    static Logger log = Logger.getLogger(T2AppController.class.getName());

    private SplunkResult<T2App> data;
    Setting _setting;

    public SplunkResult<T2App> getData() {
        return data;
    }

    public void setData(SplunkResult<T2App> data) {
        this.data = data;
    }

    @Override
    public void Setting(com.exelatech.ecx.backend.domain.Setting setting) {
        this._setting = setting;

    }

    @Override
    public void RequestData() throws Exception {
        if (!this._setting.getQuery().contains("NO RESULT")) {
            SplunkController t2app = new SplunkController(this._setting);
            t2app.getJobId();
            String pp = t2app.getResults();
            data = JsonUtl.SplT2ApptrToObj(pp);
            log.info(data.getResults().size() + " records were retrived for T2APP");

            SetTimeEpoch();
        } else {
            data = new SplunkResult<T2App>();
        }
    }

    @Override
    public void DataTransformation() {

    }

    @Override
    public void DataMapping() {

    }

    @Override
    public void SetTimeEpoch() {

    }

    @Override
    public <T extends IProdFTPD> void SetQueryFilter(ArrayList<T> data) {
        StringBuilder strBuilder = new StringBuilder("");
        for (int index = 0; index < data.size(); index++) {
            T Item = data.get(index);
            if (Item.GetSource().contains("T2_APP")) {
                if (strBuilder.length() > 0)
                    strBuilder.append(" OR ");
                strBuilder.append(" ".concat(Item.GetFileNameWithOutExtension()).concat(" "));
            }
        }
        String splunkQuery = _setting.getQuery();
        if (strBuilder.toString().length() > 0)
            _setting.setQuery(splunkQuery.replace("changeme", strBuilder.toString()));
        else
            _setting.setQuery(splunkQuery.replace("changeme", "********NO RESULT*********"));

    }

    public T2AppController() {
    }

}