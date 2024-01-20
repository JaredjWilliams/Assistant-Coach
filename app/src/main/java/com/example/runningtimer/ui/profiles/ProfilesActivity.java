package com.example.runningtimer.ui.profiles;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runningtimer.R;
import com.example.runningtimer.ui.bottom_navigation.BottomNavigationMenu;

public class ProfilesActivity extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profiles);

        new BottomNavigationMenu(this, this, R.id.navigation_profiles);
    }
}
