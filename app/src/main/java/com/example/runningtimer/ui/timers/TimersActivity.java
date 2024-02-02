package com.example.runningtimer.ui.timers;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.presenters.TimerPresenter;
import com.example.runningtimer.stopwatch.models.Stopwatch;
import com.example.runningtimer.ui.bottom_navigation.BottomNavigationMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TimersActivity extends AppCompatActivity implements TimerViewInterface {

    private final TimerPresenter presenter = new TimerPresenter(this, this);
    private FloatingActionButton addTimerButton, startStopAllTimersButton;
    private TimerAdapter adapter;
    private EditText raceDistance, raceName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timers);

        ActionBar actionBar = getSupportActionBar();
        ConstraintLayout customToolbar = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.timers_action_bar_layout, null);

        actionBar.setCustomView(customToolbar);
        actionBar.setDisplayShowCustomEnabled(true);
        setupViews();

        presenter.startUpdatingUITask();
        presenter.update();

        new BottomNavigationMenu(this, this, R.id.navigation_timer);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stopwatch_recycler_view);
        adapter = new TimerAdapter(stopwatchList(), getApplication(), presenter, recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupViews() {
        addTimerButton = findViewById(R.id.add_timer_button);
        startStopAllTimersButton = findViewById(R.id.start_stop_all_timers_button);
        raceDistance = findViewById(R.id.current_race_distance);
        raceName = findViewById(R.id.current_race_name);

        setupRecyclerView();

        setupNewTimerButton();
        setupStartAllButton();
        setStartAllButtonColor();
        setupRaceDistanceListener();
        setupRaceNameListener();
    }

    private void setupRaceDistanceListener() {
        raceDistance.setOnEditorActionListener((textView, i, event) -> {
            if (i == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                onRaceDistanceEnterPressed();

                InputMethodManager imm = (InputMethodManager) raceDistance.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(raceDistance.getWindowToken(), 0);

                raceDistance.clearFocus();

                return true;
            }
            return false;
        });
    }

    private void setupRaceNameListener() {
        raceName.setOnEditorActionListener((textView, i, event) -> {
            if (i == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                onRaceNameEnterPressed();
                InputMethodManager imm = (InputMethodManager) raceName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(raceName.getWindowToken(), 0);

                raceName.clearFocus();

                return true;
            }
            return false;
        });
    }

    private void onRaceNameEnterPressed() {
        presenter.setRaceNameForStopwatches(raceName.getText().toString().trim());
    }

    private void onRaceDistanceEnterPressed() {
        presenter.setRaceDistanceForStopwatches(raceDistance.getText().toString().trim());
    }

    private void setupNewTimerButton() {
        addTimerButton.setOnClickListener(v -> presenter.addTimer());
    }

    private void setupStartAllButton() {
        startStopAllTimersButton.setOnClickListener(v -> presenter.startStopAllTimers());
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
    public void updateAdapter() {
        adapter.updateViews();
    }

    @Override
    public void setStartAllButtonColor() {
        int startColor = ContextCompat.getColor(this, R.color.start_button_color);
        ColorStateList colorStateList = ColorStateList.valueOf(startColor);
        startStopAllTimersButton.setBackgroundTintList(colorStateList);
    }

    @Override
    public void setStopAllButtonColor() {
        int stopColor = ContextCompat.getColor(this, R.color.stop_button_color);
        ColorStateList colorStateList = ColorStateList.valueOf(stopColor);
        startStopAllTimersButton.setBackgroundTintList(colorStateList);
    }

    private List<Stopwatch> stopwatchList() {
        return presenter.stopwatchList;
    }
}
