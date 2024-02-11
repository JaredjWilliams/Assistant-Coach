package com.example.runningtimer.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.presenters.ProfilePresenter;
import com.example.runningtimer.stopwatch.models.race.Race;

import java.util.List;

public class RacesAdapter extends RecyclerView.Adapter<RaceViewHolder> {

    private ProfilePresenter presenter;
    private List<Race> races;
    private Context context;

    public RacesAdapter(List<Race> races, Context context, ProfilePresenter presenter) {
        this.races = races;
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.race_layout, parent, false);
        return new RaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RaceViewHolder holder, int position) {
        holder.race = races.get(position);
        holder.setRaceDateText();
        holder.setRaceDistanceText();
        holder.setRaceNameText();
        holder.setRaceTimeText();
    }

    @Override
    public int getItemCount() {
        return races.size();
    }
}
