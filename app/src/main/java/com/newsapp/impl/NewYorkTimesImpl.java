package com.newsapp.impl;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.newsapp.interfaces.NewYorkTimes;

/**
 * Created by anujacharya on 2/8/16.
 */
public class NewYorkTimesImpl implements NewYorkTimes {

    @Override
    public void articleSearch(String query, Integer page, TextHttpResponseHandler textHttpResponseHandler) {

        AsyncHttpClient client = new AsyncHttpClient();
        try {

            RequestParams requestParams = new RequestParams();
            requestParams.put("api-key", API_KEY);
            requestParams.put("page", page);
            requestParams.put("q", query);

            client.get(ARTICLE_SEARCH, requestParams, textHttpResponseHandler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
