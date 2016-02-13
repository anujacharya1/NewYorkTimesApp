package com.newsapp.model;

import java.util.List;

/**
 * Created by anujacharya on 2/13/16.
 */
public class Filter {

    String dateFrom;
    String dateTo;
    Boolean sortNewest;
    List<String> categories;

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Boolean getSortNewest() {
        return sortNewest;
    }

    public void setSortNewest(Boolean sortNewest) {
        this.sortNewest = sortNewest;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", sortNewest=" + sortNewest +
                ", categories=" + categories +
                '}';
    }
}
