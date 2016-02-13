package com.newsapp.dialogs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    private Boolean sortOrderNewest;
    public static String TAG = "FILTER_DIALOG";

    FilterDialogListener filterDialogListener;

    public FilterDialog(){
        filterDialogListener = null;
    }

    public static FilterDialog newInstance(FilterDialogListener filterDialogListener) {
        FilterDialog frag = new FilterDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
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
        date_to.setOnClickListener(new View.OnClickListener() {

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
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

    private void setSortSpinner(final View filterView){
        sortSpinner = (Spinner) filterView.findViewById(R.id.sort_spinner);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //setting the value in the private variables
                String sortOrderStr = String.valueOf(sortSpinner.getSelectedItem());

                if(sortOrderStr.equalsIgnoreCase("oldest")){
                    sortOrderNewest = false;
                } else{
                    sortOrderNewest = true;
                }
//                        Toast.makeText(view.getContext(), String.valueOf(sortSpinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTheCategories(final View filterView) {
        if (((CheckBox) filterView.findViewById(R.id.cb_arts)).isEnabled()) {
            categories.add("arts");
        }
        if (((CheckBox) filterView.findViewById(R.id.cb_fashion)).isEnabled()) {
            categories.add("fashion");
        }
        if (((CheckBox) filterView.findViewById(R.id.cb_sport)).isEnabled()) {
            categories.add("sport");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View filterView = inflater.inflate(R.layout.layout_filter, container, false);

        setTheDateFrom(filterView);
        setTheDateTo(filterView);
        setSortSpinner(filterView);
        setTheCategories(filterView);

        save = (Button) filterView.findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateFrom = date_from.getText().toString();
                String dateTo = date_to.getText().toString();

                Filter filter = new Filter();
                filter.setDateFrom(dateFrom);
                filter.setDateTo(dateTo);
                filter.setSortNewest(sortOrderNewest);
                filter.setCategories(categories);

                // basically to sent the data to the activity set the interface
                filterDialogListener.onFinishFilterDialogDialog(filter);
                dismiss();

            }
        });
        return filterView;
    }


    private void updateLabel(EditText date, Calendar myCalendar) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
    }
}