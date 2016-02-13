package com.newsapp.model;

import java.io.Serializable;

/**
 * Created by anujacharya on 2/8/16.
 */
public class Multimedia implements Serializable{
    static final String NEWYORK_TIMES = "http://www.nytimes.com/";

    String subtype;
    String url;

    String width;
    String height;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getUrl() {
        return NEWYORK_TIMES+url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Multimedia{" +
                "subtype='" + subtype + '\'' +
                ", url='" + url + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
