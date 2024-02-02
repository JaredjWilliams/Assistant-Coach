package com.example.runningtimer.presenters;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.runningtimer.db.ProfileDatabaseHelper;
import com.example.runningtimer.stopwatch.models.Profile;
import com.example.runningtimer.ui.profile.ProfileViewInterface;
import com.example.runningtimer.utility.ByteArrayToDrawable;

public class ProfilePresenter {

    public ProfileDatabaseHelper profileDb;
    Context context;
    ProfileViewInterface view;
    Profile profile;

    public ProfilePresenter(Context context, ProfileViewInterface view) {
        this.context = context;
        this.view = view;

        profileDb = new ProfileDatabaseHelper(context);
    }

    public void updateView() {
        view.setProfileNameText(profile.getName());
        view.setProfilePicture();
    }

    public void retrieveProfile(String name) {
        profile = profileDb.retrieveProfileFor(name);
        updateView();
    }

    public Drawable getDrawableFromByteArray() {
        return ByteArrayToDrawable.byteArrayToDrawable(
                profile.getProfilePicture(), context
        );
    }




}
