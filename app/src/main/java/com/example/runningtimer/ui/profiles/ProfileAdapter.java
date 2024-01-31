package com.example.runningtimer.ui.profiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.stopwatch.models.Profile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileViewHolder> {

    private ProfilesPresenter presenter;
    private List<Profile> profiles;
    private Context context;

    public ProfileAdapter(List<Profile> profiles, Context context, ProfilesPresenter presenter) {
        this.profiles = profiles;
        this.context = context;
        this.presenter = presenter;

        System.out.println(profiles.toString());
    }
    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_layout, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        holder.profile = profiles.get(position);
        holder.setProfileName();
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}
