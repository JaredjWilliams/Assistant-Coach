package com.example.runningtimer.ui.timers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.presenters.TimerPresenter;
import com.example.runningtimer.stopwatch.models.Stopwatch;
import com.example.runningtimer.ui.bottom_navigation.BottomNavigationMenu;

import java.util.List;

public class TimersActivity extends AppCompatActivity implements TimerViewInterface {

    private final TimerPresenter presenter = new TimerPresenter(this);
    private Button addTimerButton, startAllButton;
    private TimerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timers);

        setupViews();

        new BottomNavigationMenu(this, this, R.id.navigation_timer);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stopwatch_recycler_view);
        adapter = new TimerAdapter(stopwatchList(), getApplication(), presenter);
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
                presenter.addTimer();
            }
        });
    }

    private void setupStartAllButton() {
        startAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startStopAllTimers();
            }
        });
    }

    @Override
    public void updateAdapter(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void updateAdapter(List<Stopwatch> stopwatchList) {
        adapter.notifyItemInserted(stopwatchList.size() - 1);
    }

    @Override
    public void setStartAllButtonText() {
        startAllButton.setText("Start All Timers");
    }

    @Override
    public void setStopAllButtonText() {
        startAllButton.setText("Stop All Timers");
    }

    private List<Stopwatch> stopwatchList() {
        return presenter.stopwatchList;
    }
}
