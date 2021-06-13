package com.kang.project.question;

import androidx.annotation.NonNull;

public class QuestionItem {
    private String q_Num;
    private String id;
    private String q_title;
    private String q_content;
    private String q_date;
    private String q_time;
    private String q_count;
    private String q_like;
    private String q_dislike;

    public QuestionItem(String q_Num, String id, String q_title, String q_content, String q_date, String q_time, String q_count, String q_like, String q_dislike) {
        this.q_Num = q_Num;
        this.id = id;
        this.q_title = q_title;
        this.q_content = q_content;
        this.q_date = q_date;
        this.q_time = q_time;
        this.q_count = q_count;
        this.q_like = q_like;
        this.q_dislike = q_dislike;
    }

    public QuestionItem(String q_title, String q_content, String q_date, String q_count) {
        this.q_title = q_title;
        this.q_content = q_content;
        this.q_date = q_date;
        this.q_count = q_count;
    }

    public String getQ_Num() {
        return q_Num;
    }

    public void setQ_Num(String q_Num) {
        this.q_Num = q_Num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQ_title() {
        return q_title;
    }

    public void setQ_title(String q_title) {
        this.q_title = q_title;
    }

    public String getQ_content() {
        return q_content;
    }

    public void setQ_content(String q_content) {
        this.q_content = q_content;
    }

    public String getQ_date() {
        return q_date;
    }

    public void setQ_date(String q_date) {
        this.q_date = q_date;
    }

    public String getQ_time() {
        return q_time;
    }

    public void setQ_time(String q_time) {
        this.q_time = q_time;
    }

    public String getQ_count() {
        return q_count;
    }

    public void setQ_count(String q_count) {
        this.q_count = q_count;
    }

    public String getQ_like() {
        return q_like;
    }

    public void setQ_like(String q_like) {
        this.q_like = q_like;
    }

    public String getQ_dislike() {
        return q_dislike;
    }

    public void setQ_dislike(String q_dislike) {
        this.q_dislike = q_dislike;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getQ_Num() + "\n" +
                this.getQ_Num() + "\n" +
                this.getQ_title() + "\n" +
                this.getQ_content() + "\n" +
                this.getQ_date() + "\n" +
                this.getQ_time() + "\n" +
                this.getQ_count() + "\n" +
                this.getQ_like() + "\n" +
                this.getQ_dislike();
    }
}
