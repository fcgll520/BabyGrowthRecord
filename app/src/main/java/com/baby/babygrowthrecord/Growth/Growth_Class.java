package com.baby.babygrowthrecord.Growth;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Growth_Class {
    private long id;
    private String year;
    private String week;
    private String duration;
    private String content;
    private String img_first;
    private String img_second;

    public Growth_Class(long id, String year, String week, String duration, String content, String img_first) {
        this.id = id;
        this.year = year;
        this.week = week;
        this.duration = duration;
        this.content = content;
        this.img_first = img_first;
    }

    public Growth_Class(long id, String year, String week, String duration, String content, String img_first, String img_second) {
        this.id = id;
        this.year = year;
        this.week = week;
        this.duration = duration;
        this.content = content;
        this.img_first = img_first;
        this.img_second = img_second;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_first() {
        return img_first;
    }

    public void setImg_first(String img_first) {
        this.img_first = img_first;
    }

    public String getImg_second() {
        return img_second;
    }

    public void setImg_second(String img_second) {
        this.img_second = img_second;
    }
}
