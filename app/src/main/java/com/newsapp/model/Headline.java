package com.newsapp.model;

import org.parceler.Parcel;

/**
 * Created by anujacharya on 2/9/16.
 */
@Parcel
public class Headline {

    String main;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "Headline{" +
                "main='" + main + '\'' +
                '}';
    }
}
