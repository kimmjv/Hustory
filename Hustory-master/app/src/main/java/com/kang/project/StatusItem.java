package com.kang.project;

import android.graphics.drawable.Drawable;

public class StatusItem {
    private Drawable iconDrawable;
    private String StudentStr;
    private String SummaryStr;
    private String DateStr;
    private String WayStr;
    private String PlaceStr;
    private String allowStr;

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getStudentStr() {
        return StudentStr;
    }

    public void setStudentStr(String studentStr) {
        StudentStr = studentStr;
    }

    public String getSummaryStr() {
        return SummaryStr;
    }

    public void setSummaryStr(String summaryStr) {
        SummaryStr = summaryStr;
    }

    public String getDateStr() {
        return DateStr;
    }

    public void setDateStr(String dateStr) {
        DateStr = dateStr;
    }

    public String getWayStr() {
        return WayStr;
    }

    public void setWayStr(String wayStr) {
        WayStr = wayStr;
    }

    public String getPlaceStr() {
        return PlaceStr;
    }

    public void setPlaceStr(String placeStr) {
        PlaceStr = placeStr;
    }

    public String getAllowStr() {
        return allowStr;
    }

    public void setAllowStr(String allowStr) {
        this.allowStr = allowStr;
    }
}
