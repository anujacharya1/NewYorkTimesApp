package com.newsapp.model;

import java.util.List;

/**
 * Created by anujacharya on 2/13/16.
 */
public class Filter {

    String dateFrom;
    String dateTo;
    String sort;
    List<String> categories;
    String query;

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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", sort='" + sort + '\'' +
                ", categories=" + categories +
                ", query='" + query + '\'' +
                '}';
    }
}
