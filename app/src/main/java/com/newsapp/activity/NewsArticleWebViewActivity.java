package com.newsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.newsapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anujacharya on 2/13/16.
 */
public class NewsArticleWebViewActivity extends AppCompatActivity {

    @Bind(R.id.newsArticleWebview)
    WebView myWebView;

    @Bind(R.id.progBar)
    ProgressBar progressBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //share button

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);


        MenuItem item = menu.findItem(R.id.menu_item_share);
        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        // get reference to WebView
        // pass in the URL currently being used by the WebView
        shareIntent.putExtra(Intent.EXTRA_TEXT, myWebView.getUrl());

        miShare.setShareIntent(shareIntent);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_newsview);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        String webUrl = getIntent().getStringExtra("webUrl");


        //Add progress bar for webview
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(progress);
                if(progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });

        //Make webview stay in app rather than launching browser
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

        //Load article URL
        myWebView.loadUrl(webUrl);


//
//        myWebView = (WebView) findViewById(R.id.newsArticleWebview);
//        // Configure related browser settings
//        myWebView.getSettings().setLoadsImagesAutomatically(true);
//        myWebView.getSettings().setJavaScriptEnabled(true);
//        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        // Configure the client to use when opening URLs
//        myWebView.setWebViewClient(new MyBrowser());
//        myWebView.loadUrl(webUrl);


    }

//    // Manages the behavior when URLs are loaded
//    private class MyBrowser extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
