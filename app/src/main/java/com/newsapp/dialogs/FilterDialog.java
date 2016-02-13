package com.newsapp.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.newsapp.R;

import java.util.ArrayList;

/**
 * Created by anujacharya on 2/13/16.
 */
public class FilterDialog extends DialogFragment {


    public static String TAG = "FILTER_DIALOG";

    public FilterDialog(){

    }
    public static FilterDialog newInstance() {
        FilterDialog frag = new FilterDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view1 = inflater.inflate(R.layout.layout_filter, container, false);



        return view1;
    }

}