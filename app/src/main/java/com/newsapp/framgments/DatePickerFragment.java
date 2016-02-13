//package com.newsapp.framgments;
//
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
//
//import com.newsapp.dialogs.FilterDialog;
//
//import java.util.Calendar;
//
///**
// * Created by anujacharya on 1/12/16.
// */
//public class DatePickerFragment extends DialogFragment {
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the current date as the default date in the picker
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//
//
//        return new DatePickerDialog(year, month, day);
//    }
//
//}