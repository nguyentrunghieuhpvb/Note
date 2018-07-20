package com.example.hieunt.note.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManager {

    public static String getDateTomorow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        return tomorrowAsString;
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        return tomorrowAsString;
    }

    public static String getDateNextWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        return tomorrowAsString;
    }
}
