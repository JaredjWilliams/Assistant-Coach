package com.example.runningtimer.ui.profiles;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningtimer.R;
import com.example.runningtimer.stopwatch.models.Profile;
import com.example.runningtimer.ui.bottom_navigation.BottomNavigationMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProfilesActivity extends AppCompatActivity implements ProfilesViewInterface {


    private ProfilesPresenter presenter;
    private ConstraintLayout profilePopup;
    private FloatingActionButton addProfileButton;
    private ProfileAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profiles);
        presenter = new ProfilesPresenter(this, this);
        presenter.update();

        setUpViews();

        setUpAddProfileButtonClick();

        setUpRecyclerView();

        new BottomNavigationMenu(this, this, R.id.navigation_profiles);
    }

    private void setUpViews() {
        addProfileButton = findViewById(R.id.add_profile_button);
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.profiles_recycler_view);
        adapter = new ProfileAdapter(presenter.getListOfProfiles(), this, presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpAddProfileButtonClick() {
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewProfilePopup(ProfilesActivity.this, ProfilesActivity.this, presenter).showPopUp();
                presenter.deleteTable();
            }
        });
    }

    @Override
    public void updateAdapter(List<Profile> profileList) {
        adapter.notifyItemInserted(profileList.size() - 1);
    }

    private List<Profile> profileList() {
        return presenter.getListOfProfiles();
    }

}
