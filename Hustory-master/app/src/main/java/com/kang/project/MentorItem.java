package com.kang.project;

import android.graphics.drawable.Drawable;

public class MentorItem {
    private String NameStr;
    private String VersionStr;
    private Boolean CheckBool;

    public String getNameStr() {
        return NameStr;
    }

    public void setNameStr(String nameStr) {
        NameStr = nameStr;
    }

    public String getVersionStr() {
        return VersionStr;
    }

    public void setVersionStr(String versionStr) {
        VersionStr = versionStr;
    }

    public Boolean getCheckBool() {
        return CheckBool;
    }

    public void setCheckBool(Boolean checkBool) {
        CheckBool = checkBool;
    }
}
