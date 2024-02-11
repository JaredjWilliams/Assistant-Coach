package com.example.runningtimer.utility;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CapitalizeName {

    public static String capitalizeName(String name) {
        return Arrays.stream(name.split(" "))
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
