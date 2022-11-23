package com.emanuelef.remote_capture.model;

import java.sql.Date;

public class GetInfo_DTO {
    private String appName;
    private String dateNTime;
    private String leakType;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDateNTime() {
        return dateNTime;
    }

    public void setDateNTime(String dateNTime) {
        this.dateNTime = dateNTime;
    }

    public String getLeakType() {
        return leakType;
    }

    public void setLeakType(String leakType) {
        this.leakType = leakType;
    }
}
