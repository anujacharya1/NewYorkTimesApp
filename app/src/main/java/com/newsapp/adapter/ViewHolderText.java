package com.newsapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.newsapp.R;

/**
 * Created by anujacharya on 2/12/16.
 */
public class ViewHolderText extends RecyclerView.ViewHolder {
    private TextView label1;
    private TextView descriptionAlone;

    public ViewHolderText(View v) {
        super(v);
        label1 = (TextView) v.findViewById(R.id.headlineAlone);
        descriptionAlone = (TextView) v.findViewById(R.id.descriptionAlone);
    }

    public TextView getLabel1() {
        return label1;
    }

    public void setLabel1(TextView label1) {
        this.label1 = label1;
    }

    public TextView getDescriptionAlone() {
        return descriptionAlone;
    }

    public void setDescriptionAlone(TextView descriptionAlone) {
        this.descriptionAlone = descriptionAlone;
    }
}
