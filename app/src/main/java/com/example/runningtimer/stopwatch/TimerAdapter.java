package com.example.runningtimer.stopwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.stopwatch.models.Stopwatch;

import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerViewHolder> {
    private TimerPresenter presenter;
    List<Stopwatch> stopwatchList;
    Context context;

    public TimerAdapter(List<Stopwatch> stopwatchList, Context context, TimerPresenter presenter) {
        this.stopwatchList = stopwatchList;
        this.presenter = presenter;
        System.out.println("Presenter is null: " + (presenter == null ? "yes" : "no"));
        this.context = context;
    }

    @NonNull
    @Override
    public TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stopwatch_layout, parent, false);
        return new TimerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerViewHolder holder, int position) {

        holder.stopwatch = stopwatchList.get(position);
        holder.context = context;
        holder.setName();
        holder.setLapsLayout();

    }

    @Override
    public int getItemCount() {
        return stopwatchList.size();
    }
}
