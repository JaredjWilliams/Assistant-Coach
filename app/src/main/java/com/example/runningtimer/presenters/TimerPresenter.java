package com.example.runningtimer.presenters;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runningtimer.ui.timers.TimerViewHolderInterface;
import com.example.runningtimer.ui.timers.TimerViewInterface;
import com.example.runningtimer.stopwatch.models.Stopwatch;
import com.example.runningtimer.ui.timers.TimersActivity;

import java.util.ArrayList;
import java.util.List;

public class TimerPresenter {

    public List<Stopwatch> stopwatchList = new ArrayList<Stopwatch>();
    private TimerViewHolderInterface viewHolder;
    TimerViewInterface view;

    public TimerPresenter(TimersActivity view) {
        this.view = view;
    }

    public boolean areAllTimersStarted(List<Stopwatch> stopwatchList) {

        boolean areStarted = true;

        for (Stopwatch stopwatch : stopwatchList) {
            if (!stopwatch.isStarted()) {
                return false;
            }
        }

        return areStarted;
    }

    public void setTimerViewHolder(TimerViewHolderInterface viewHolder) {
        this.viewHolder = viewHolder;
    }

    private void onEnterPressed(EditText editText) {
        viewHolder.setStopwatchName(editText.getText().toString());
    }

    public void setEditListener(EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onEnterPressed(editText);
                    return true;
                }
                return false;
            }
        });
    }

    public void startStopAllTimers() {
        if (areAllTimersStarted(stopwatchList)) {
            stopTimers(stopwatchList);
            view.setStartAllButtonText();
        } else {
            startTimers(stopwatchList);
            view.setStopAllButtonText();
        }
    }

    private void stopTimers(List<Stopwatch> stopwatchList) {
        for (Stopwatch stopwatch : stopwatchList) {
            stopwatch.stopTimer();
        }
    }

    private void startTimers(List<Stopwatch> stopwatchList) {
        for (Stopwatch stopwatch : stopwatchList) {
            stopwatch.startTimer();
        }
    }

    public void addTimer() {
        stopwatchList.add(new Stopwatch());
        view.updateAdapter(stopwatchList);
    }

}
