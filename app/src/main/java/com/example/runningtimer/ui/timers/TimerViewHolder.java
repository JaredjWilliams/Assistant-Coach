package com.example.runningtimer.ui.timers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.presenters.TimerPresenter;
import com.example.runningtimer.stopwatch.models.Lap;
import com.example.runningtimer.stopwatch.models.Stopwatch;

public class TimerViewHolder extends RecyclerView.ViewHolder implements TimerViewHolderInterface {

    LinearLayout stopwatchLayout;
    TimerPresenter presenter;
    GridLayout laps;
    Button startButton;
    Button stopButton;
    Button lapButton;
    TextView textView;
    EditText name;
    Stopwatch stopwatch;
    Context context;
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public TimerViewHolder(@NonNull View itemView) {
        super(itemView);

        setupViews(itemView);

        startUpdatingUITask();
    }

    public void setupViews(View itemView) {
        stopwatchLayout = itemView.findViewById(R.id.stopwatch_layout);

        startButton = itemView.findViewById(R.id.start_button);
        stopButton = itemView.findViewById(R.id.stop_button);
        lapButton = itemView.findViewById(R.id.lap_button);

        laps = itemView.findViewById(R.id.laps_layout);
        textView = itemView.findViewById(R.id.timer_number);
        name = itemView.findViewById(R.id.athlete_name);

        setupStartButtonClick();
        setupStopButtonClick();
        setupLapButtonClick();

        setEditListener(name);
    }

    public void setName() {
        name.setText(stopwatch.getName());
    }

    public void setStopwatchName(String name) {
        stopwatch.setName(name);
    }

    public void setLapsLayout() {
        for (Lap lap : stopwatch.getLaps()) {
            System.out.println("Lap in set: " + lap);
            TextView textview = new TextView(context);
            String lapText = "" + lap.getLapCount() + ": " + lap.getTime();
            textview.setText(lapText);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;

            laps.addView(textview);
        }
    }

    private void setupStartButtonClick() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopwatch.startTimer();
            }
        });
    }

    private void setupStopButtonClick() {
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopwatch.stopTimer();
            }
        });
    }

    private void setupLapButtonClick() {

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopwatch.lapTimer();
                Lap lastLap = stopwatch.getLastLap();
                TextView textview = new TextView(context);
                String lapText = "<b>" + lastLap.getLapCount() + ":</b> " + lastLap.getTime();
                textview.setText(Html.fromHtml(lapText, Html.FROM_HTML_MODE_LEGACY));


                params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                textview.setPadding(5, 5, 5, 5);

                laps.addView(textview);
            }
        });
    }

    private void updateUI() {
        textView.setText(stopwatch.getElapsedTime());
    }

    private void startUpdatingUITask() {
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                updateUI();
                uiHandler.postDelayed(this, 1000);
            }
        }, 0);
    }

    @Override
    public String getNameChange() {
        return name.getText().toString();
    }

    private void onEnterPressed(EditText editText) {
        setStopwatchName(editText.getText().toString());
    }

    public void setEditListener(EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    onEnterPressed(editText);

                    InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    editText.clearFocus();

                    return true;
                }
                return false;
            }
        });
    }
}
