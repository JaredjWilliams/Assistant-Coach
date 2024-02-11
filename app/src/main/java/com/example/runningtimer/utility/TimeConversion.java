package com.example.runningtimer.utility;

public class TimeConversion {

    public static String convertToMinSeconds(String input) {
        if (input.charAt(0) == '0') {
            return input.substring(3);
        }

        return input;
    }
}
