package com.example.runningtimer.ui.profiles;

import com.example.runningtimer.stopwatch.models.Profile;

import java.util.List;

public interface ProfilesViewInterface {


    void updateNewProfilePopup();

    void resetNewProfilePopup();

    void updateAdapter(List<Profile> profileList);
}
