package com.newsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.newsapp.model.Multimedia;
import com.newsapp.model.News;

import java.util.List;

import com.newsapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by anujacharya on 2/10/16.
 */
public class NewsAdapter extends
        RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> newsList;

    private Context context;

    private int MAX_CHAR_HEADLINE = 60;
    private int MAX_CHAR_DESC = 30;

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
            if(m.getSubtype()!=null && m.getSubtype().equalsIgnoreCase("wide")){
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
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        NewsAdapter.ViewHolder viewHolder;
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

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
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

    private String splitStringHeadline(String inputString){
        int maxLength = (inputString.length() < MAX_CHAR_HEADLINE)?inputString.length():MAX_CHAR_HEADLINE;
        inputString = inputString.substring(0, maxLength);
        return inputString;
    }

    private String splitStringDesc(String inputString){
        int maxLength = (inputString.length() < MAX_CHAR_DESC)?inputString.length():MAX_CHAR_DESC;
        inputString = inputString.substring(0, maxLength);
        return inputString;
    }

    private void configureViewHolder1(ViewHolderText vh1, int position) {
        News news = newsList.get(position);
        if (news != null) {

            String headline = news.getHeadline().getMain();
            headline = splitStringHeadline(headline);
            headline+="...";

            vh1.getLabel1().setText(Html.fromHtml("<b><font size='1' color='#236B8E'>" + headline + "</font></b>"));

            if(news.getLeadParagraph()!=null){
                String description = splitStringDesc(news.getLeadParagraph());
                description+="...";
                vh1.getDescriptionAlone().setText(description);
            }
        }
    }

    private void configureViewHolder2(ViewHolderImgText vh2, int position) {
        News news = newsList.get(position);


        String headline = news.getHeadline().getMain();
        headline = splitStringHeadline(headline);
        headline+="...";

        vh2.getTextView().setText(Html.fromHtml("<b><font size='1' color='#236B8E'>" + headline + "</font></b>"));

        if(news.getLeadParagraph()!=null){
            String description = splitStringDesc(news.getLeadParagraph());
            description+="...";
            vh2.getDescription().setText(description);
        }

        List<Multimedia> multimedias = news.getMultimedia();

        Multimedia thumbnail = null;
        for(Multimedia m : multimedias){
            if(m.getSubtype().equalsIgnoreCase("wide")){
                thumbnail = m;
                break;
            }
        }

        Glide.with(context)
                .load(thumbnail != null ? thumbnail.getUrl() : "")
                .into(vh2.getImgView());
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return this.newsList.size();
    }


    /*

    The below portion took very long time for me to figure out. First I have to change the signature

    FROM
    public class NewsAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    TO existing

     */

    // Define listener member variable
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(final View itemView) {
            super(itemView);
            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}
