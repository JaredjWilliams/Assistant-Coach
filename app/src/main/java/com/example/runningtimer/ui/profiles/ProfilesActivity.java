package com.example.runningtimer.ui.profiles;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    private static final int PICK_IMAGE_REQUEST = 1;
    private ProfilesPresenter presenter;
    private ConstraintLayout profilePopup;
    private FloatingActionButton addProfileButton;
    private ProfileAdapter adapter;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private NewProfilePopup newProfilePopup;



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

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onActivityResultCallback);

        newProfilePopup = new NewProfilePopup(this, this, presenter, pickImageLauncher);

    }

    @Override
    public void updateNewProfilePopup() {
        newProfilePopup.closePopUp();
    }

    @Override
    public void resetNewProfilePopup() {
        newProfilePopup.resetLayout();
    }

    private void onActivityResultCallback(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            Uri selectedImage = result.getData().getData();

            newProfilePopup.onActivityResultCallback(selectedImage);
        }
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
        addProfileButton.setOnClickListener(view -> {
            newProfilePopup.showPopUp();
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
