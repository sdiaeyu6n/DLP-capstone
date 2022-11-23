package com.emanuelef.remote_capture.model;

import java.sql.Timestamp;

public class GetInfo_DTO {
    private String appName;
    private Timestamp dateNTime;
    private String leakType;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Timestamp getDateNTime() {
        return dateNTime;
    }

    public void setDateNTime(Timestamp dateNTime) {
        this.dateNTime = dateNTime;
    }

    public String getLeakType() {
        return leakType;
    }

    public void setLeakType(String leakType) {
        this.leakType = leakType;
    }
}
