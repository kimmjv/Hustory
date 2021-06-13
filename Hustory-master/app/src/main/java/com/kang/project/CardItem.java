package com.kang.project;

import android.graphics.drawable.Drawable;

public class CardItem {
    private Drawable iconDrawable;
    private String NameStr;
    private String VersionStr;

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

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

}
