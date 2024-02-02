package com.example.runningtimer.ui.profiles;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.stopwatch.models.Profile;
import com.example.runningtimer.ui.profile.ProfileActivity;
import com.google.gson.Gson;

public class ProfileViewHolder extends RecyclerView.ViewHolder {

    Profile profile;
    TextView profileName;
    Context context;
    ConstraintLayout profileLayout;

    public ProfileViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        setupViews(itemView);

        setProfileClickListener();

    }

    public void setupViews(View itemView) {
        profileLayout = itemView.findViewById(R.id.profile_layout);
        profileName = itemView.findViewById(R.id.profile_name);
    }

    public void setProfileName() {
        profileName.setText(profile.getName());
    }

    public void setProfileClickListener() {
        profileLayout.setOnClickListener(view -> {

            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("profileName", nameToGSON(profile.getName()));
            context.startActivity(intent);

        });
    }

    private String nameToGSON(String name) {
        Gson gson = new Gson();
        return gson.toJson(name);
    }


}
