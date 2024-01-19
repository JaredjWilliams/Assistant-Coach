package com.example.runningtimer.stopwatch.models;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Lap {

    private long time;
    private int lapCount;

    public Lap(long time, int lapCount) {
        this.time = time;
        this.lapCount = lapCount;
    }

    public String getTime() {
        return formatTime(time);
    }

    public int getLapCount() {
        return lapCount;
    }

    @Override
    public String toString() {
        return "Lap{" +
                "time=" + time +
                ", lapCount=" + lapCount +
                '}';
    }

    private String formatTime(long timeInMillis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(timeInMillis);

        return String.format(Locale.US,"%02d:%02d:%02d", hours, minutes, seconds);
    }
}
