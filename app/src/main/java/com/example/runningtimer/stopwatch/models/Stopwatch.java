package com.example.runningtimer.stopwatch.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Stopwatch {

    private String name;
    private long totalElapsedTime;
    private long startTime;
    private long totalLapTime = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> timerHandle;
    private List<Lap> laps = new ArrayList<Lap>();
    private int lapCounter = 0;
    private boolean isStarted;

    public Stopwatch() {
        this.name = "";
    }

    public void startTimer() {
        if (timerHandle == null || timerHandle.isCancelled()) {
            isStarted = true;
            startTime = System.currentTimeMillis();
            timerHandle = scheduler.scheduleAtFixedRate(this::updateElapseTime, 0, 1000, TimeUnit.MILLISECONDS);
        }
    }
    public void stopTimer() {
        if (timerHandle != null && !timerHandle.isCancelled()) {
            isStarted = false;
            timerHandle.cancel(true);
        }
    }
    public void lapTimer() {
        long lapTime = totalElapsedTime - totalLapTime;
        totalLapTime = totalElapsedTime;
        lapCounter++;
        Lap lap = new Lap(lapTime, lapCounter);

        laps.add(lap);
        System.out.println(lap);
    }

    private void updateElapseTime() {
        long currentTime = System.currentTimeMillis();
        totalElapsedTime += currentTime - startTime;
        startTime = currentTime;
    }

    private String formatTime(long timeInMillis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(timeInMillis);

        return String.format(Locale.US,"%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getElapsedTime() {
        return formatTime(totalElapsedTime);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public List<Lap> getLaps() {
        return laps;
    }

    public Lap getLastLap() {
        return laps.get(laps.size() - 1);
    }
}
