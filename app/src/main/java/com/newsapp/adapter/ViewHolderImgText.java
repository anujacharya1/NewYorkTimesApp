package com.newsapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsapp.R;

/**
 * Created by anujacharya on 2/12/16.
 */
public class ViewHolderImgText extends RecyclerView.ViewHolder {

    private ImageView imgView;
    private TextView textView;

    public ViewHolderImgText(View v) {
        super(v);
        imgView = (ImageView) v.findViewById(R.id.newsArticleImg);
        textView = (TextView) v.findViewById(R.id.newsArticleText);
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
