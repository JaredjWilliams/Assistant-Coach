package com.example.runningtimer.ui.profile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.presenters.ProfilePresenter;
import com.example.runningtimer.stopwatch.models.Profile;
import com.example.runningtimer.ui.bottom_navigation.BottomNavigationMenu;
import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity implements ProfileViewInterface {

    ProfilePresenter presenter;
    RacesAdapter adapter;
    Profile profile;
    TextView profileName;
    ImageView profilePicture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        presenter = new ProfilePresenter(this, this);


        setContentView(R.layout.activity_profile);

        setupViews();

        retrieveNameFromGson();

        new BottomNavigationMenu(this, this, 0);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.races_recycler_view);
        adapter = new RacesAdapter();
    }

    private void setupViews() {
        profileName = findViewById(R.id.athlete_name);
        profilePicture = findViewById(R.id.profile_picture);
    }

    @Override
    public void setProfileNameText(String name) {
        profileName.setText(name);
    }

    @Override
    public void setProfilePicture() {
        Drawable image = presenter.getDrawableFromByteArray();
        profilePicture.setImageDrawable(image);
    }

    private void retrieveNameFromGson() {
        String profileJSON = getIntent().getStringExtra("profileName");

        if (profileJSON != null) {
            String name = new Gson().fromJson(profileJSON, String.class);
            presenter.retrieveProfile(name);
        }
    }

}
