package com.example.chernenkovit.a;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    //Default date formatter.
    private static final DateFormat DEFAULT_DATE_FORMAT = SimpleDateFormat.getTimeInstance(
            SimpleDateFormat.SHORT);

    //format timestamp for human reading
    public static String getFormattedTimestamp(long timestamp) {
        if (timestamp == 0) {
            return "";
        } else {
            final Date date = new Date(timestamp);
            return DEFAULT_DATE_FORMAT.format(date);
        }
    }
}
