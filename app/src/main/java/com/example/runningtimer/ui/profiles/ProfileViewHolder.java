package com.example.runningtimer.ui.profiles;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.stopwatch.models.Profile;

public class ProfileViewHolder extends RecyclerView.ViewHolder {

    Profile profile;
    TextView profileName;
    ConstraintLayout profileLayout;

    public ProfileViewHolder(@NonNull View itemView) {
        super(itemView);

        setupViews(itemView);

    }

    public void setupViews(View itemView) {
        System.out.println("We work");
        profileLayout = itemView.findViewById(R.id.profile_layout);
        profileName = itemView.findViewById(R.id.profile_name);
    }

    public void setProfileName() {
        profileName.setText(profile.getName());
    }


}
