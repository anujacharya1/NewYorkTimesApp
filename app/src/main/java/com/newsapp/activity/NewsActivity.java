package com.newsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
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
import com.newsapp.dialogs.FilterDialog;
import com.newsapp.impl.NewYorkTimesImpl;
import com.newsapp.model.Filter;
import com.newsapp.model.News;
import com.newsapp.R;

import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;


public class NewsActivity extends AppCompatActivity{

    List<News> newsList;

    // Recycler View components
    private RecyclerView mRecyclerView;
    NewsAdapter newsAdapter;

    //store the value on this for filtering the result
    // this are the default and will be overriden by the dialog framgment
    private Filter filter;

    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setLogo(R.drawable.nwslogo);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        setupTheView();

        if(!isNetworkAvailable() || !isOnline()){
            Toast.makeText(this, "INTERNET NOT AVAIALBLE", Toast.LENGTH_SHORT).show();
        }
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

        //end less scroller
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int pageList, int totalItemsCount) {
                page = pageList;
                customLoadMoreDataFromApi(page);
            }
        });
        mRecyclerView.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                News news = newsList.get(position);

                Intent i = new Intent(NewsActivity.this, NewsArticleWebViewActivity.class);
                i.putExtra("news", Parcels.wrap(news));
                startActivity(i);
            }
        });
    }

    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void customLoadMoreDataFromApi(int page) {


        new NewYorkTimesImpl().articleSearch(filter, page, new TextHttpResponseHandler() {
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

                if (filter == null) {
                    filter = new Filter();

                }
                filter.setQuery(query);
                customLoadMoreDataFromApi(page);
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

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_filter:

                //show the dialog framgment
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();

                if(filter==null){
                    filter = new Filter();
                }

                // get the value from the dialog
                DialogFragment newFragment = FilterDialog.newInstance(filter, new FilterDialog.FilterDialogListener() {
                    @Override
                    public void onFinishFilterDialogDialog(Filter filterDialogResponse) {

                        if(filter==null){
                            filter = new Filter();
                        }

                        if(!filterDialogResponse.getDateFrom().isEmpty()){
                            filter.setDateFrom(filterDialogResponse.getDateFrom());
                        }
                        if(!filterDialogResponse.getDateTo().isEmpty()){
                            filter.setDateTo(filterDialogResponse.getDateTo());
                        }
                        filter.setCategories(filterDialogResponse.getCategories());
                        filter.setSort(filterDialogResponse.getSort());

                        Log.i("INFO", "onOptionsItemSelected= " + filter);

                        //also call the network call to refresh the content
                        page = 0;

                        //only call the api if the query is present
                        // use case where the person is going to set the filter first without query
                        customLoadMoreDataFromApi(page);


                    }
                });

                newFragment.show(ft, "FILTER_DIALOG");

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
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


    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }


}
