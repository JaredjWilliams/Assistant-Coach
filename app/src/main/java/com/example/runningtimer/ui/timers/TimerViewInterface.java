package com.example.runningtimer.ui.timers;

import com.example.runningtimer.stopwatch.models.Stopwatch;

import java.util.List;

public interface TimerViewInterface {

    void updateAdapter(List<Stopwatch> stopwatchList);

    void setStartAllButtonText();

    void setStopAllButtonText();
}
