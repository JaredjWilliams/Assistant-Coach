package com.example.runningtimer.ui.profiles;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.runningtimer.db.ProfileDatabaseHelper;
import com.example.runningtimer.stopwatch.models.Profile;

import java.util.ArrayList;

public class ProfilesPresenter {

    private ProfileDatabaseHelper profileDb;
    private ArrayList<Profile> listOfProfiles = new ArrayList<>();
    private Context context;
    private ProfilesViewInterface view;

    public ProfilesPresenter(Context context, ProfilesViewInterface view) {
        this.context = context;
        profileDb = new ProfileDatabaseHelper(context);
        this.view = view;
    }

    public void update() {
        storeDataInArrays();
    }

    private void storeDataInArrays() {
        Cursor cursor = profileDb.readAllData();

        if (cursor.getCount() == 0) {
            makeToast("No data available");
        } else {
            createListProfiles(cursor);
        }
    }

    public void addProfile(String name) {

        if (isAlreadyCreated(name)) {
            makeToast("Profile already exists");
            return;
        }
        profileDb.addProfile(name);
        update();
        view.updateAdapter(listOfProfiles);
    }

    private void createListProfiles(Cursor cursor) {
        listOfProfiles.clear();

        while (cursor.moveToNext()) {
            Profile profile = new Profile(cursor.getString(1));
            listOfProfiles.add(profile);
        }
    }

    private void makeToast(String text) {
        Toast.makeText(context, text , Toast.LENGTH_SHORT).show();
    }

    public boolean isAlreadyCreated(String name) {

        boolean isCreated = false;

        for (Profile p : listOfProfiles) {
            if (p.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Profile> getListOfProfiles() {
        return listOfProfiles;
    }

    public void deleteTable() {
        profileDb.deleteData();
    }
}
