package com.exelatech.ecx.backend.domain;

public class Setting {
    private String KeyName;
    private String Query;
    private String Url;
    private String User;
    private String Password;
    private String Index;
    private String Type;
    private int DpsMoveIntervalDays;
    private int retries;
    private int SplunkPauseOnSecs;
    private int ExecutionIntervalOnSecs;
    private int JobActivityInterval;
    private int DpsMonitorInterval;

    public String getKeyName() {
        return KeyName;
    }

    public int getExecutionIntervalOnSecs() {
        return ExecutionIntervalOnSecs;
    }

    public void setExecutionIntervalOnSecs(int executionIntervalOnSecs) {
        this.ExecutionIntervalOnSecs = executionIntervalOnSecs;
    }

    public int getSplunkPauseOnSecs() {
        return SplunkPauseOnSecs;
    }

    public void setSplunkPauseOnSecs(int splunkPauseOnSecs) {
        this.SplunkPauseOnSecs = splunkPauseOnSecs;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getDpsMoveIntervalDays() {
        return DpsMoveIntervalDays;
    }

    public void setDpsMoveIntervalDays(int dpsMoveIntervalDays) {
        this.DpsMoveIntervalDays = dpsMoveIntervalDays;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        this.Index = index;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        this.User = user;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        this.Url = url;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        this.Query = query;
    }

    public void setKeyName(String keyName) {
        this.KeyName = keyName;
    }

    public Setting() {
    }

    public Setting(String keyName, String query, String url, String user, String password, String index, String type,
            int dpsMoveIntervalDays, int retries, int splunkPauseOnSecs, int executionIntervalOnSecs) {
        KeyName = keyName;
        Query = query;
        Url = url;
        User = user;
        Password = password;
        Index = index;
        Type = type;
        DpsMoveIntervalDays = dpsMoveIntervalDays;
        this.retries = retries;
        SplunkPauseOnSecs = splunkPauseOnSecs;
        ExecutionIntervalOnSecs = executionIntervalOnSecs;
    }

    public int getJobActivityInterval() {
        return JobActivityInterval;
    }

    public void setJobActivityInterval(int jobActivityInterval) {
        JobActivityInterval = jobActivityInterval;
    }

    public int getDpsMonitorInterval() {
        return DpsMonitorInterval;
    }

    public void setDpsMonitorInterval(int dpsMonitorInterval) {
        DpsMonitorInterval = dpsMonitorInterval;
    }

}