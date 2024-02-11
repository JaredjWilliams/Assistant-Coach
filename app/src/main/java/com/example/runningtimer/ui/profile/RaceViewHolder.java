package com.example.runningtimer.ui.profile;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.stopwatch.models.race.Race;

public class RaceViewHolder extends RecyclerView.ViewHolder {

    TextView raceNameText, raceDistanceText, raceDateText, raceTimeText;
    Race race;


    public RaceViewHolder(@NonNull View itemView) {
        super(itemView);

        setupViews();
    }

    private void setupViews() {
        raceNameText = itemView.findViewById(R.id.race_name);
        raceDistanceText = itemView.findViewById(R.id.race_distance);
        raceDateText = itemView.findViewById(R.id.race_date);
        raceTimeText = itemView.findViewById(R.id.race_time);
    }

    public void setRaceTimeText() {
        raceTimeText.setText(race.getRaceTime());
    }

    public void setRaceDateText() {
        raceDateText.setText(race.getRaceDate());
    }

    public void setRaceDistanceText() {
        raceDistanceText.setText(race.getRaceDistance());
    }

    public void setRaceNameText() {
        raceNameText.setText(race.getRaceName());
        System.out.println(race);
    }


}
