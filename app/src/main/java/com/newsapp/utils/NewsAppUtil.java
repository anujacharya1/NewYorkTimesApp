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

            int month = myCal.get(Calendar.MONTH) + 1;
            int day = myCal.get(Calendar.DAY_OF_MONTH);

            if(month<10){
                String monthStr = "0"+month;
                dateResponse.append(monthStr);
            }
            else{
                dateResponse.append(month);
            }

            if(day<10){
                String dayStr = "0"+day;
                dateResponse.append(dayStr);
            }
            else{
                dateResponse.append(day);
            }
            dateResponse.append(myCal.get(Calendar.YEAR));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateResponse.toString();
    }


}
