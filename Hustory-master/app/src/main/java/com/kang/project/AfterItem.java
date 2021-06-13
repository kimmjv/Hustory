package com.kang.project;

import android.graphics.drawable.Drawable;

public class AfterItem {
    private Drawable iconDrawable;
    private String ProfessorStr;
    private String SummaryStr;
    private String DateStr;
    private String WayStr;
    private String PlaceStr;

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getProfessorStr() {
        return ProfessorStr;
    }

    public void setProfessorStr(String professorStr) {
        ProfessorStr = professorStr;
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
}
