package com.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anujacharya on 2/8/16.
 */
public class News implements Serializable{

    Headline headline;

    @SerializedName("multimedia")
    List<Multimedia> multimedia;

    @SerializedName("lead_paragraph")
    String leadParagraph;

    @SerializedName("pub_date")
    String pubDate;

    @SerializedName("web_url")
    String webUrl;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    @Override
    public String toString() {
        return "News{" +
                "headline=" + headline +
                ", multimedia=" + multimedia +
                ", leadParagraph='" + leadParagraph + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
