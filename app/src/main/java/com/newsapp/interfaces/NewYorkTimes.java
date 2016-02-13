package com.newsapp.interfaces;

import com.loopj.android.http.TextHttpResponseHandler;
import com.newsapp.model.Filter;

/**
 * Created by anujacharya on 2/8/16.
 */
public interface NewYorkTimes {

    String API_KEY = "9296c425e14ea5e4ee44bb5754936ea9:7:74339256";
    String ARTICLE_SEARCH = "http://api.nytimes.com/svc/search/v2/articlesearch.json?";

    String SUCCESS_OK = "OK";

    void articleSearch(Filter filter, Integer page, TextHttpResponseHandler textHttpResponseHandler);
}
