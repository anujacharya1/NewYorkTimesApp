package com.newsapp.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by anujacharya on 2/13/16.
 */
public class NewsAppUtil {


    public static String getTheDate(String date){

        StringBuilder dateResponse = new StringBuilder();
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        try {
            Date fromDate = sdf.parse(date);
            Calendar myCal = new GregorianCalendar();
            myCal.setTime(fromDate);

            int day = myCal.get(Calendar.DAY_OF_MONTH);
            String dayStr = new String();

            if(day <10){
                dayStr = "0"+day;
            }

            int month = myCal.get(Calendar.MONTH) + 1;
            String monthStr = new String();

            if(month <10){
                monthStr = "0"+month;
            }
            else{
                monthStr = String.valueOf(month);
            }
            dateResponse.append(myCal.get(Calendar.YEAR));
            dateResponse.append(monthStr);
            dateResponse.append(dayStr);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateResponse.toString();
    }


}
