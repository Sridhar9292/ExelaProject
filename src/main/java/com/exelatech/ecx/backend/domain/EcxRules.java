package com.exelatech.ecx.backend.domain;

import java.util.ArrayList;

public class EcxRules {
    private String Ignore;
    private ArrayList<String> Source;
    private String DefaultAppCode;

    public String getIgnore() {
        return Ignore;
    }

    public void setIgnore(String ignore) {
        Ignore = ignore;
    }

    public ArrayList<String> getSource() {
        return Source;
    }

    public void setSource(ArrayList<String> source) {
        Source = source;
    }

    public String getDefaultAppCode() {
        return DefaultAppCode;
    }

    public void setDefaultAppCode(String defaultAppCode) {
        DefaultAppCode = defaultAppCode;
    }

    public EcxRules(String ignore, ArrayList<String> source, String defaultAppCode) {
        Ignore = ignore;
        Source = source;
        DefaultAppCode = defaultAppCode;
    }

    public EcxRules() {
    }
}