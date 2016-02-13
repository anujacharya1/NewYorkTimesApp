package com.newsapp.dialogs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.newsapp.R;
import com.newsapp.model.Filter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by anujacharya on 2/13/16.
 */
public class FilterDialog extends DialogFragment {

    private EditText date_from;
    private EditText date_to;
    private Spinner sortSpinner;
    private List<String> categories = new ArrayList<>();
    Button save;
    Filter filter;

    private String sort;
    public static String TAG = "FILTER_DIALOG";

    FilterDialogListener filterDialogListener;

    public FilterDialog(){
        filterDialogListener = null;
    }

    public static FilterDialog newInstance(Filter filter, FilterDialogListener filterDialogListener) {
        FilterDialog frag = new FilterDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        frag.setFilter(filter);
        frag.setFilterDialogListener(filterDialogListener);
        return frag;
    }

    public interface FilterDialogListener {
        void onFinishFilterDialogDialog(Filter filter);
    }


    public void setFilterDialogListener(FilterDialogListener filterDialogListener) {
        this.filterDialogListener = filterDialogListener;
    }

    private void setTheDateFrom(final View filterView){
        final Calendar myCalendar = Calendar.getInstance();

        date_from = (EditText) filterView.findViewById(R.id.et_date_from);
        date_from.setTextIsSelectable(true);

        String previouslySetDate = filter.getDateFrom();

        if(previouslySetDate==null){
            updateLabel(date_from, Calendar.getInstance());
        }
        else{
            date_from.setText(previouslySetDate);
        }

        //set the current date to the from date

        date_from.setOnClickListener(new View.OnClickListener() {

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(date_from, myCalendar);
                }
            };

            @Override
            public void onClick(View v) {
                new DatePickerDialog(filterView.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    private void setTheDateTo(final View filterView){
        final Calendar myCalendar = Calendar.getInstance();

        date_to = (EditText) filterView.findViewById(R.id.et_date_to);
        date_to.setTextIsSelectable(true);

        // set the date to the previous set one
        String previouslySetDate = filter.getDateTo();
        if(previouslySetDate==null){
            updateLabel(date_to, Calendar.getInstance());
        }
        else{
            date_to.setText(previouslySetDate);
        }

        date_to.setOnClickListener(new View.OnClickListener() {

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(date_to, myCalendar);
                }
            };

            @Override
            public void onClick(View v) {
                new DatePickerDialog(filterView.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    //spinner helper to set the value in the spinner
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }


    private void setSortSpinner(final View filterView){
        sortSpinner = (Spinner) filterView.findViewById(R.id.sort_spinner);

        if(filter.getSort()!=null && !filter.getSort().isEmpty()){
            sortSpinner.setSelection(getIndex(sortSpinner, filter.getSort()));
        }

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //setting the value in the private variables
                sort = String.valueOf(sortSpinner.getSelectedItem()).toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCategories(final View filterView){
        List<String> categories = filter.getCategories();

        ((CheckBox) filterView.findViewById(R.id.cb_arts)).setChecked(false);
        ((CheckBox) filterView.findViewById(R.id.cb_fashion)).setChecked(false);
        ((CheckBox) filterView.findViewById(R.id.cb_sport)).setChecked(false);

        if(categories!=null && categories.size()>0){
            for(String s : categories){
                if(s.equalsIgnoreCase("arts")){
                    ((CheckBox) filterView.findViewById(R.id.cb_arts)).setChecked(true);
                }
                if(s.equalsIgnoreCase("fashion")){
                    ((CheckBox) filterView.findViewById(R.id.cb_fashion)).setChecked(true);
                }
                if(s.equalsIgnoreCase("sport")){
                    ((CheckBox) filterView.findViewById(R.id.cb_sport)).setChecked(true);
                }
            }
        }

    }

    private void checkTheCategories(final View filterView) {
        if (((CheckBox) filterView.findViewById(R.id.cb_arts)).isChecked()) {
            categories.add("arts");
        }
        if (((CheckBox) filterView.findViewById(R.id.cb_fashion)).isChecked()) {
            categories.add("fashion");
        }
        if (((CheckBox) filterView.findViewById(R.id.cb_sport)).isChecked()) {
            categories.add("sport");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        final View filterView = inflater.inflate(R.layout.layout_filter, container, false);

        setTheDateFrom(filterView);
        setTheDateTo(filterView);
        setSortSpinner(filterView);
        setCategories(filterView);

        save = (Button) filterView.findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkTheCategories(filterView);

                String dateFrom = date_from.getText().toString();

                //set the default value to the current date and time

                Log.i(TAG, "++++++++++++++dateFrom=" + dateFrom);
                String dateTo = date_to.getText().toString();

                Filter filter = new Filter();
                filter.setDateFrom(dateFrom);
                filter.setDateTo(dateTo);
                filter.setSort(sort);
                filter.setCategories(categories);

                // basically to sent the data to the activity set the interface
                filterDialogListener.onFinishFilterDialogDialog(filter);
                dismiss();

            }
        });
        return filterView;
    }

    private void updateLabel(EditText date, Calendar myCalendar) {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}