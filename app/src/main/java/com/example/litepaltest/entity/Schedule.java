package com.example.litepaltest.entity;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Schedule extends LitePalSupport {
    private int id;
    private String name;
    private String content;
    private String date;
    private int finished;

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
