package com.newsapp.dialogs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.newsapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by anujacharya on 2/13/16.
 */
public class FilterDialog extends DialogFragment {

    private EditText date_from;
    private EditText date_to;
    private Spinner sortSpinner;


    private String sortOrder;
    public static String TAG = "FILTER_DIALOG";

    public FilterDialog(){

    }
    public static FilterDialog newInstance() {
        FilterDialog frag = new FilterDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
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
                sortOrder = String.valueOf(sortSpinner.getSelectedItem());
//                        Toast.makeText(view.getContext(), String.valueOf(sortSpinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View filterView = inflater.inflate(R.layout.layout_filter, container, false);

        setTheDateFrom(filterView);
        setTheDateTo(filterView);
        setSortSpinner(filterView);

        return filterView;
    }


    private void updateLabel(EditText date, Calendar myCalendar) {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
    }
}