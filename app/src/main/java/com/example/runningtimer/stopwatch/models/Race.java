package com.example.runningtimer.stopwatch.models;

public class Race {

    private String raceName;
    private String raceTime;
    private String raceDate;
    private String athleteName;


    public Race(String raceName, String raceTime, String raceDate, String athleteName) {
        this.raceName = raceName;
        this.raceTime = raceTime;
        this.raceDate = raceDate;
        this.athleteName = athleteName;
    }
}
