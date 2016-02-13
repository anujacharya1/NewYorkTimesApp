package com.newsapp.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;
import com.newsapp.adapter.EndlessRecyclerViewScrollListener;
import com.newsapp.adapter.NewsAdapter;
import com.newsapp.impl.NewYorkTimesImpl;
import com.newsapp.model.News;
import com.newsapp.R;

import cz.msebera.android.httpclient.Header;


public class NewsActivity extends AppCompatActivity{

    List<News> newsList;

    // Recycler View components
    private RecyclerView mRecyclerView;
    NewsAdapter newsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupTheView();

    }

    private void setupTheView(){

        // get the view of recycler
        mRecyclerView = (RecyclerView) findViewById(R.id.nwsRecycler);

        // set properties of recycler
        mRecyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);

            }
        });
        mRecyclerView.setAdapter(newsAdapter);


    }

    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void customLoadMoreDataFromApi(int page) {


        new NewYorkTimesImpl().articleSearch("sample", page, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("ERROR", "res=" + res + " statusCode=" + statusCode + " message=" + t.getMessage());
            }



            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                List<News> newsListLocal = parseTheResponse(responseString);
                newsList.addAll(newsListLocal);
                int curSize = newsAdapter.getItemCount();
                newsAdapter.notifyItemRangeInserted(curSize, newsList.size() - 1);

            }
        });
        // For efficiency purposes, notify the adapter of only the elements that got changed
        // curSize will equal to the index of the first element inserted because the list is 0-indexed

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                callApi();
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                // overridePendingTransition(R.animator.anim_left, R.animator.anim_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private List<News> parseTheResponse(String responseString){

        Gson gson = new GsonBuilder().create();

        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(responseString).getAsJsonObject();

        JsonObject response = obj.getAsJsonObject("response");

        JsonArray docs = response.getAsJsonArray("docs");

        Type newsType = new TypeToken<List<News>>() {}.getType();
        List<News> newsList =  gson.fromJson(docs, newsType);
        return newsList;
    }



    private void callApi() {

        new NewYorkTimesImpl().articleSearch("sample", 0, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("ERROR", "res=" + res + " statusCode=" + statusCode + " message=" + t.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                List<News> newsListLocal = parseTheResponse(responseString);

                for(int i=0 ; i<newsListLocal.size(); i++){
                    newsList.add(i, newsListLocal.get(i));
                    newsAdapter.notifyItemInserted(i);
                }

            }
        });
    }

}
