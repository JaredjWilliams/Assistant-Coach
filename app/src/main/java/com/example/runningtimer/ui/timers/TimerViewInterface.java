package com.example.runningtimer.ui.timers;

import com.example.runningtimer.stopwatch.models.Stopwatch;

import java.util.List;

public interface TimerViewInterface {

    void updateAdapter(int position);

    void updateAdapter(List<Stopwatch> stopwatchList);

    void updateAdapter();

    void setStartAllButtonText();

    void setStopAllButtonText();
}
