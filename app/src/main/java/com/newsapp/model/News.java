package com.newsapp.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anujacharya on 2/8/16.
 */
@Parcel
public class News{

    @SerializedName("headline")
    Headline headline;

    @SerializedName("multimedia")
    List<Multimedia> multimedia;

    @SerializedName("lead_paragraph")
    String leadParagraph;

    @SerializedName("pub_date")
    String pubDate;

    @SerializedName("section_name")
    String sectionName;

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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public String toString() {
        return "News{" +
                "headline=" + headline +
                ", multimedia=" + multimedia +
                ", leadParagraph='" + leadParagraph + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
