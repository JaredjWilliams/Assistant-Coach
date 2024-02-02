package com.example.runningtimer.presenters;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.example.runningtimer.db.ProfileDatabaseHelper;
import com.example.runningtimer.stopwatch.models.Profile;
import com.example.runningtimer.stopwatch.models.Stopwatch;
import com.example.runningtimer.ui.timers.TimerViewHolderInterface;
import com.example.runningtimer.ui.timers.TimerViewInterface;
import com.example.runningtimer.ui.timers.TimersActivity;

import java.util.ArrayList;
import java.util.List;

public class TimerPresenter {

    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private ProfileDatabaseHelper profileDb;
    public List<Stopwatch> stopwatchList = new ArrayList<Stopwatch>();
    public List<Profile> listOfProfiles = new ArrayList<>();
    private TimerViewHolderInterface viewHolder;
    TimerViewInterface view;
    private Context context;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            view.updateAdapter();
            uiHandler.postDelayed(this, 500);
        }
    };

    public TimerPresenter(TimersActivity view, Context context) {
        this.view = view;
        this.context = context;
        profileDb = new ProfileDatabaseHelper(context);

    }

    public void update() {
        storeDataInArrays();
    }

    private void storeDataInArrays() {
        Cursor cursor = profileDb.readAllData();

        if (cursor.getCount() == 0) {
            makeToast("No data available");
        } else {
            createListProfiles(cursor);
        }
    }

    private void makeToast(String text) {
        Toast.makeText(context, text , Toast.LENGTH_SHORT).show();
    }

    private void createListProfiles(Cursor cursor) {
        listOfProfiles.clear();

        while (cursor.moveToNext()) {
            Profile profile = new Profile(cursor.getString(1), cursor.getBlob(2));
            listOfProfiles.add(profile);
        }
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
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onEnterPressed(editText);
                return true;
            }
            return false;
        });
    }

    public void startStopAllTimers() {
        if (areAllTimersStarted(stopwatchList)) {
            stopTimers(stopwatchList);
            view.setStartAllButtonColor();
        } else {
            startTimers(stopwatchList);
            view.setStopAllButtonColor();
        }
    }

    private void stopTimers(List<Stopwatch> stopwatchList) {
        for (Stopwatch stopwatch : stopwatchList) {
            stopwatch.stopTimer();
        }
        startUpdatingUITask();
    }

    private void startTimers(List<Stopwatch> stopwatchList) {
        for (Stopwatch stopwatch : stopwatchList) {
            stopwatch.startTimer();
        }
        startUpdatingUITask();
    }

    public void addTimer() {
        stopwatchList.add(new Stopwatch());
        view.updateAdapter(stopwatchList);
        startUpdatingUITask();
    }

    public void startStopTimer(Stopwatch stopwatch) {
        if (stopwatch.isStarted()) {
            stopwatch.stopTimer();
        } else {
            stopwatch.startTimer();
        }

        startUpdatingUITask();
    }

    public void delete(Stopwatch stopwatch) {
        int position = stopwatchList.indexOf(stopwatch);

        stopwatchList.remove(stopwatch);
        view.updateAdapter(position);
        startUpdatingUITask();
    }

    public void startUpdatingUITask() {
        if (isOneTimerStarted()) {
            uiHandler.postDelayed(runnable, 0);
        } else {
            uiHandler.removeCallbacksAndMessages(null);
        }

        view.updateAdapter();
    }

    private boolean isOneTimerStarted() {

        for (Stopwatch stopwatch : stopwatchList) {
            if (stopwatch.isStarted()) {
                return true;
            }
        }

        return false;
    }

    public void saveTime(Stopwatch stopwatch, String name) {
        profileDb.addRace(name, stopwatch.getElapsedTime(), stopwatch.getRaceName(), stopwatch.getRaceDistance());
    }

    public void setRaceNameForStopwatches(String raceName) {
        for (Stopwatch stopwatch : stopwatchList) {
            stopwatch.setRaceName(raceName);
        }
    }

    public void setRaceDistanceForStopwatches(String distance) {
        for (Stopwatch stopwatch : stopwatchList) {
            stopwatch.setRaceDistance(distance);
        }
    }
}
