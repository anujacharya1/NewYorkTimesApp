package com.newsapp.impl;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.newsapp.interfaces.NewYorkTimes;
import com.newsapp.model.Filter;
import com.newsapp.utils.NewsAppUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by anujacharya on 2/8/16.
 */
public class NewYorkTimesImpl implements NewYorkTimes {


    @Override
    public void articleSearch(Filter filter, Integer page, TextHttpResponseHandler textHttpResponseHandler) {

        AsyncHttpClient client = new AsyncHttpClient();
        try {
            //fq=filter-field:

            Log.i("INFO", "articleSearch= " + filter);

            RequestParams requestParams = new RequestParams();
            requestParams.put("api-key", API_KEY);
            requestParams.put("page", page);

            if(filter.getQuery()!=null && !filter.getQuery().isEmpty()){
                requestParams.put("q", filter.getQuery());
            }

            if(filter.getCategories()!=null && filter.getCategories().size()>0){
                requestParams.put("fq=section_name.contains:", android.text.TextUtils.join(",", filter.getCategories()));
            }
            if(filter.getDateFrom()!=null && !filter.getDateFrom().isEmpty()){
                String newYorkTimesDate = NewsAppUtil.getTheDate(filter.getDateFrom());
                requestParams.put("begin_date", newYorkTimesDate);
            }
            if(filter.getDateTo()!=null && !filter.getDateTo().isEmpty()){
                String newYorkTimesDate = NewsAppUtil.getTheDate(filter.getDateTo());
                requestParams.put("end_date",newYorkTimesDate);
            }
            if(filter.getSort()!=null && !filter.getSort().isEmpty()){
                requestParams.put("sort",filter.getSort());
            }

            Log.i("INFO", "request= " + ARTICLE_SEARCH+requestParams);

            client.get(ARTICLE_SEARCH, requestParams, textHttpResponseHandler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
