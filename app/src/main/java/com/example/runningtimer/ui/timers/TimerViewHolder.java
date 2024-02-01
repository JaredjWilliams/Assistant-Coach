package com.example.runningtimer.ui.timers;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.presenters.TimerPresenter;
import com.example.runningtimer.stopwatch.models.Profile;
import com.example.runningtimer.stopwatch.models.Stopwatch;

public class TimerViewHolder extends RecyclerView.ViewHolder implements TimerViewHolderInterface {

    ConstraintLayout stopwatchLayout;
    TimerPresenter presenter;
    GridLayout laps;
    Button startButton, lapButton;
    TextView timeText;
    EditText name;
    Stopwatch stopwatch;
    Context context;
    ImageView athletePicture;
    ImageButton deleteTimerButton;
    TranslateAnimation slideIn = new TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_PARENT, 1.0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0.0f
    );
    TranslateAnimation slideOut = new TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
            TranslateAnimation.RELATIVE_TO_PARENT, -1.0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0.0f
    );

    public TimerViewHolder(@NonNull View itemView) {
        super(itemView);

        setupViews(itemView);
    }

    public void setupViews(View itemView) {
        stopwatchLayout = itemView.findViewById(R.id.stopwatch_layout);

        startButton = itemView.findViewById(R.id.start_button);
        lapButton = itemView.findViewById(R.id.lap_button);

        laps = itemView.findViewById(R.id.laps_layout);
        timeText = itemView.findViewById(R.id.timer_number);
        name = itemView.findViewById(R.id.athlete_name);
        athletePicture = itemView.findViewById(R.id.athlete_picture);
        deleteTimerButton = itemView.findViewById(R.id.delete_timer_button);

        setupStartButtonClick();
        setupLapButtonClick();
        setDeleteTimerButtonClick();

        setEditListener(name);

        setStartStopButtonUI();

        slideIn.setDuration(500);
        slideOut.setDuration(500);
    }


    public void performSlideIn() {
        stopwatchLayout.startAnimation(slideIn);
    }

    public void performSlideOut() {
        stopwatchLayout.startAnimation(slideOut);
    }


    public void setName() {
        name.setText(stopwatch.getName());
    }

    public void setStopwatchName(String name) {
        stopwatch.setName(name);
    }

    public void setLapsLayout() {

    }

    public void setDeleteTimerButtonClick() {
        deleteTimerButton.setOnClickListener(view -> {
            performSlideOut();
            presenter.delete(stopwatch);

        });
    }

    public void setStartStopButtonUI() {

        if (stopwatch == null) {
            return;
        }

        int buttonColor = ContextCompat.getColor(context, stopwatch.isStarted() ? R.color.stop_button_color : R.color.start_button_color);
        startButton.setBackgroundColor(buttonColor);
        startButton.setText(stopwatch.isStarted() ? "Stop" : "Start");
    }

    private void setupStartButtonClick() {
        startButton.setOnClickListener(v -> {
            presenter.startStopTimer(stopwatch);
            setStartStopButtonUI();
        });
    }

    private void setupLapButtonClick() {

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        lapButton.setOnClickListener(v -> {

//                stopwatch.lapTimer();
//                Lap lastLap = stopwatch.getLastLap();
//                TextView textview = new TextView(context);
//                String lapText = "<b>" + lastLap.getLapCount() + ":</b> " + lastLap.getTime();
//                textview.setText(Html.fromHtml(lapText, Html.FROM_HTML_MODE_LEGACY));
//
//
//                params.width = GridLayout.LayoutParams.WRAP_CONTENT;
//                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
//                textview.setPadding(5, 5, 5, 5);
//
//                laps.addView(textview);
        });
    }

    public void updateUI() {
        updateTimeText();
        setStartStopButtonUI();
    }

    private void updateTimeText() {
        timeText.setText(stopwatch.getElapsedTime());
    }

    @Override
    public String getNameChange() {
        return name.getText().toString();
    }

    private void onEnterPressed(EditText editText) {
        setStopwatchName(editText.getText().toString());
    }

    public void setEditListener(EditText editText) {
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                onEnterPressed(editText);

                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                editText.clearFocus();

                setProfileImage(editText.getText().toString());


                return true;
            }
            return false;
        });
    }

    private void setProfileImage(String name) {
        for (Profile profile : presenter.listOfProfiles) {
            if (profile.getName().equals(name)) {
                athletePicture.setImageDrawable(profile.getProfilePicture());
            }
        }
    }
}
