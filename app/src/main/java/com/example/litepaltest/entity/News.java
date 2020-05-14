package com.example.litepaltest.entity;

import org.litepal.crud.LitePalSupport;

public class News extends LitePalSupport {
    private String title;
    private String content;
    private String prictureUrl;

    public String getPrictureUrl() {
        return prictureUrl;
    }

    public void setPrictureUrl(String prictureUrl) {
        this.prictureUrl = prictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
