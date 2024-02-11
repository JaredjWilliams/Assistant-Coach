package com.example.runningtimer.stopwatch.models.race;

import com.example.runningtimer.utility.DateConverter;

public class Race {

    private String raceName;
    private String raceTime;
    private String raceDate;
    private String athleteName;
    private String raceDistance;


    public Race(String athleteName, String raceName, String raceDistance, String raceDate, String raceTime) {
        this.athleteName = athleteName;
        this.raceName = raceName;
        this.raceDistance = raceDistance;
        this.raceDate = raceDate;
        this.raceTime = raceTime;
    }

    public String getRaceName() {
        return raceName;
    }

    public String getRaceTime() {
        return raceTime;
    }

    public String getRaceDate() {
        return DateConverter.toLongMonthShortDayLongYear(raceDate);
    }

    public String getAthleteName() {
        return athleteName;
    }

    public String getRaceDistance() {
        return raceDistance;
    }

    @Override
    public String toString() {
        return "Race{" +
                "raceName='" + raceName + '\'' +
                ", raceTime='" + raceTime + '\'' +
                ", raceDate='" + raceDate + '\'' +
                ", athleteName='" + athleteName + '\'' +
                ", raceDistance='" + raceDistance + '\'' +
                '}';
    }
}
