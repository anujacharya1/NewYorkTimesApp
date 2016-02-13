package com.newsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newsapp.model.Multimedia;
import com.newsapp.model.News;

import java.util.List;

import com.newsapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by anujacharya on 2/10/16.
 */
public class NewsAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> newsList;

    private Context context;

    private final int TEXT = 0, IMG = 1;


    // Pass in the contact array into the constructor
    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;

    }

    @Override
    public int getItemViewType(int position) {

        News news = newsList.get(position);
        //get the thumbnail one from the multimedia list
        List<Multimedia> multimedias = news.getMultimedia();

        Multimedia thumbnail = null;
        for(Multimedia m : multimedias){
            if(m.getSubtype().equalsIgnoreCase("wide")){
                thumbnail = m;
                break;
            }
        }

        //check if it has image OR imge+text
        if (thumbnail == null) {
            return TEXT;
        } else{
            return IMG;
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TEXT:
                View v1 = inflater.inflate(R.layout.layout_text, parent, false);
                viewHolder = new ViewHolderText(v1);
                break;
            case IMG:
                View v2 = inflater.inflate(R.layout.news_article, parent, false);
                viewHolder = new ViewHolderImgText(v2);
                break;
            default:
//                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case TEXT:
                ViewHolderText vh1 = (ViewHolderText) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case IMG:
                ViewHolderImgText vh2 = (ViewHolderImgText) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            default:
//                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
//                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private void configureViewHolder1(ViewHolderText vh1, int position) {
        News news = newsList.get(position);
        if (news != null) {
            vh1.getLabel1().setText(news.getHeadline().getMain());
        }
    }

    private void configureViewHolder2(ViewHolderImgText vh2, int position) {
        News news = newsList.get(position);

        vh2.getTextView().setText(news.getHeadline().getMain());

        List<Multimedia> multimedias = news.getMultimedia();

        Multimedia thumbnail = null;
        for(Multimedia m : multimedias){
            if(m.getSubtype().equalsIgnoreCase("wide")){
                thumbnail = m;
                break;
            }
        }

        Picasso.with(context)
                .load(thumbnail!=null?thumbnail.getUrl():"")
                .resize(600, 400)
                .into(vh2.getImgView());
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return this.newsList.size();
    }



}
