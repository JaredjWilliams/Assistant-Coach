package com.example.runningtimer.ui.profiles;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.runningtimer.R;
import com.example.runningtimer.db.ProfileDatabaseHelper;
import com.example.runningtimer.ui.bottom_navigation.BottomNavigationMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfilesActivity extends AppCompatActivity {


    ConstraintLayout profilePopup;
    FloatingActionButton addProfileButton;
    ProfileDatabaseHelper profileDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileDb = new ProfileDatabaseHelper(this);

        setContentView(R.layout.activity_profiles);


        setUpViews();

        setUpAddProfileButtonClick();

        new BottomNavigationMenu(this, this, R.id.navigation_profiles);
    }

    private void setUpViews() {
        addProfileButton = findViewById(R.id.add_profile_button);
    }

    private void setUpAddProfileButtonClick() {
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewProfilePopup(ProfilesActivity.this, ProfilesActivity.this).showPopUp();
            }
        });
    }

}
