package com.newsapp.model;

/**
 * Created by anujacharya on 2/9/16.
 */
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
