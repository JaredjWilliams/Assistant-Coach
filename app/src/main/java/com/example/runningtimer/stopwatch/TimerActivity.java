package com.example.runningtimer.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.runningtimer.R;
import com.example.runningtimer.stopwatch.models.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class TimerActivity extends AppCompatActivity implements TimerViewInterface {



    private List<Stopwatch> stopwatchList = new ArrayList<Stopwatch>();
    private TimerPresenter presenter = new TimerPresenter(this);
    private Button addTimerButton;
    private Button startAllButton;
    TimerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timer);

        setupViews();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stopwatch_recycler_view);
        adapter = new TimerAdapter(stopwatchList, getApplication(), presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupViews() {
        addTimerButton = findViewById(R.id.add_timer_button);
        startAllButton = findViewById(R.id.start_all_button);

        setupRecyclerView();

        setupNewTimerButton();
        setupStartAllButton();
    }

    private void setupNewTimerButton() {
        addTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimer();
            }
        });
    }

    private void setupStartAllButton() {
        startAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopAllTimers();
            }
        });
    }

    private void addTimer() {
        stopwatchList.add(new Stopwatch());
        adapter.notifyItemInserted(stopwatchList.size() - 1);
    }

    private void startStopAllTimers() {

        if (presenter.areAllTimersStarted(stopwatchList)) {
            for (Stopwatch stopwatch : stopwatchList) {
                stopwatch.stopTimer();
            }
            setStopAllButtonText();
        } else {
            for (Stopwatch stopwatch : stopwatchList) {
                stopwatch.startTimer();
            }
            setStartAllButtonText();
        }
    }

    private void setStartAllButtonText() {
        startAllButton.setText("Start All Timers");
    }

    private void setStopAllButtonText() {
        startAllButton.setText("Stop All Timers");
    }
}
