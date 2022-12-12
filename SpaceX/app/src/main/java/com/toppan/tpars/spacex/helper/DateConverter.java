package com.toppan.tpars.spacex.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    public static String DATE_FORMAT = "yyyy-MM-dd hh:mm";
    public static String DATE_UTC_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'";


    public static String convertDateToString(Date date, String format) {
        if (date == null) return "";
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date convertStringToDate(String strDate, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
