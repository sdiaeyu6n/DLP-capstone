package com.emanuelef.remote_capture.model;

public class AppListItem {
    private String AppLebel;
    private String AppUid;
    public boolean Checked;

    public String getAppLebel() {
        return AppLebel;
    }

    public void setAppLebel(String appLebel) {
        AppLebel = appLebel;
    }

    public String getAppUid() {
        return AppUid;
    }

    public void setAppUid(String appUid) {
        AppUid = appUid;
    }

    public AppListItem(String AppLebel, String AppUid, boolean Checked){
        this.AppLebel = AppLebel;
        this.AppUid = AppUid;
        this.Checked = Checked;
    }

    public boolean isChecked(){
        return Checked;
    }

}
