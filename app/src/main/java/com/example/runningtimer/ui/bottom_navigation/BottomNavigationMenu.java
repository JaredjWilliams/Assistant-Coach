package com.example.runningtimer.ui.bottom_navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.runningtimer.R;
import com.example.runningtimer.ui.profiles.ProfilesActivity;
import com.example.runningtimer.ui.timers.TimersActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationMenu {

    private final BottomNavigationView bottomNavigationView;
    private final int currentSelectionId;
    private final Context context;

    public BottomNavigationMenu(Context context, Activity activity, int currentSelectionId) {
        this.currentSelectionId = currentSelectionId;
        this.context = context;
        bottomNavigationView = activity.findViewById(R.id.nav_view);
        bottomNavigationView.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_500));
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
        setBottomNavListener();
    }

    public void setBottomNavListener() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (isItemTheSameAsCurrent(item)) {
                    return true;
                } else if (isItemId(R.id.navigation_timer, item))  {
                    start(TimersActivity.class);
                } else if (isItemId(R.id.navigation_profiles, item)) {
                    start(ProfilesActivity.class);
                }

                return false;
            }
        });
    }

    private boolean isItemTheSameAsCurrent(MenuItem item) {
        return item.getItemId() == currentSelectionId;
    }

    private boolean isItemId(int id, MenuItem item) {
        return id == item.getItemId();
    }

    private void start(Class activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }
}
