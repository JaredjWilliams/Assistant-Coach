package com.example.runningtimer.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static String toLongMonthShortDayLongYear(String input) {
        String inputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
        String outputFormat = "MM/dd/yyyy";
        String outputDateString = "";

        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat);
            Date date = inputDateFormat.parse(input);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
            outputDateString = outputDateFormat.format(date);

            return outputDateString;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
