package com.exelatech.ecx.backend.util;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.exelatech.ecx.backend.dao.DpsJob;
import com.exelatech.ecx.backend.domain.MoveItc;
import com.exelatech.ecx.backend.domain.Proftpd;
import com.exelatech.ecx.backend.domain.RpdsInput;
import com.exelatech.ecx.backend.domain.SenderReceiver;
import com.exelatech.ecx.backend.domain.SplunkResult;
import com.exelatech.ecx.backend.domain.T2App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtl<T> {

    /**
     * 
     * @param jsonString Json to string
     * @param typeClass  Object type
     * @return An object filled with the json string
     */
    public static <T> T JsonStrToObject(String jsonString, Class<T> typeClass) {
        Gson gson = new Gson();
        T res = gson.fromJson(jsonString, typeClass);
        return res;
    }

    /**
     * Static metod that convert a splunk string result from profpd datasource to a
     * SplunkResult<Proftpd>
     * 
     * @param splunkString
     * @return
     */
    public static SplunkResult<Proftpd> SplProftpdStrToObj(String splunkString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<SplunkResult<Proftpd>>() {
        }.getType();
        SplunkResult<Proftpd> res = gson.fromJson(splunkString, listType);
        return res;
    }

    /**
     * Static metod that convert a splunk string result from profpd datasource to a
     * SplunkResult<Proftpd>
     * 
     * @param splunkString
     * @return
     */
    public static SplunkResult<RpdsInput> SplRpdsInputStrToObj(String splunkString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<SplunkResult<RpdsInput>>() {
        }.getType();
        SplunkResult<RpdsInput> res = gson.fromJson(splunkString, listType);
        return res;
    }

    /**
     * Static metod that convert a splunk string result from profpd datasource to a
     * SplunkResult<Proftpd>
     * 
     * @param splunkString
     * @return
     */
    public static SplunkResult<SenderReceiver> SplSenderReceiverStrToObj(String splunkString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<SplunkResult<SenderReceiver>>() {
        }.getType();
        SplunkResult<SenderReceiver> res = gson.fromJson(splunkString, listType);
        return res;
    }

    /**
     * Static metod that convert a splunk string result from profpd datasource to a
     * SplunkResult<Proftpd>
     * 
     * @param splunkString
     * @return
     */
    public static SplunkResult<MoveItc> SplMoveItStrToObj(String splunkString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<SplunkResult<MoveItc>>() {
        }.getType();
        SplunkResult<MoveItc> res = gson.fromJson(splunkString, listType);
        return res;
    }

    /**
     * Static metod that convert a splunk string result from profpd datasource to a
     * SplunkResult<Proftpd>
     * 
     * @param splunkString
     * @return
     */
    public static SplunkResult<T2App> SplT2ApptrToObj(String splunkString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<SplunkResult<T2App>>() {
        }.getType();
        SplunkResult<T2App> res = gson.fromJson(splunkString, listType);
        return res;
    }

    /**
     * Static metod that convert a splunk string result from profpd datasource to a
     * SplunkResult<Proftpd>
     * 
     * @param splunkString
     * @return
     */
    public static ArrayList<Proftpd> ProftpdStrToObj(String splunkString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Proftpd>>() {
        }.getType();
        ArrayList<Proftpd> res = gson.fromJson(splunkString, listType);
        return res;
    }

    /**
     * Static metod that convert a splunk string result from profpd datasource to a
     * SplunkResult<Proftpd>
     * 
     * @param splunkString
     * @return
     */
    public static ArrayList<DpsJob> DpsJobStrToObj(String splunkString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<DpsJob>>() {
        }.getType();
        ArrayList<DpsJob> res = gson.fromJson(splunkString, listType);
        return res;
    }

    public static String ToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);

    }

    public JsonUtl() {
    }
}