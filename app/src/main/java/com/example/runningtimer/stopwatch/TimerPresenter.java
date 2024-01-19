package com.example.runningtimer.stopwatch;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runningtimer.stopwatch.models.Stopwatch;

import java.util.List;

public class TimerPresenter {

    private TimerViewHolderInterface viewHolder;
    TimerViewInterface view;

    public TimerPresenter(TimerActivity view) {
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

}
