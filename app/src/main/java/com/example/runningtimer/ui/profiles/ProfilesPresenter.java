package com.example.runningtimer.ui.profiles;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.example.runningtimer.db.ProfileDatabaseHelper;
import com.example.runningtimer.stopwatch.models.Profile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProfilesPresenter {

    private ProfileDatabaseHelper profileDb;
    private ArrayList<Profile> listOfProfiles = new ArrayList<>();
    private ArrayList<byte[]> listOfImages = new ArrayList<>();
    private Context context;
    private ProfilesViewInterface view;

    public ProfilesPresenter(Context context, ProfilesViewInterface view) {
        this.context = context;
        this.view = view;

        profileDb = new ProfileDatabaseHelper(context);
    }

    public void update() {
        storeDataInArrays();
    }

    private byte[] getBitmapAsByteArray(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    private void storeDataInArrays() {
        Cursor cursor = profileDb.readAllData();

        if (cursor.getCount() == 0) {
            makeToast("No data available");
        } else {
            createListProfiles(cursor);
        }
    }

    public void addProfile(String name, Drawable image) {
        BitmapDrawable drawable = (BitmapDrawable) image;
        String cleanedName = name.trim().toLowerCase();
        byte[] imageToBytes = getBitmapAsByteArray(drawable.getBitmap());

        if (isAlreadyCreated(cleanedName)) {
            makeToast("Profile already exists");
            return;
        }
        profileDb.addProfile(cleanedName, imageToBytes);
        update();

        view.updateAdapter(listOfProfiles);
        view.updateNewProfilePopup();
        view.resetNewProfilePopup();
    }

    private void createListProfiles(Cursor cursor) {
        listOfProfiles.clear();

        while (cursor.moveToNext()) {
            Profile profile = new Profile(cursor.getString(1), cursor.getBlob(2));
            listOfProfiles.add(profile);
        }

        System.out.println(listOfProfiles);
    }

    private void makeToast(String text) {
        Toast.makeText(context, text , Toast.LENGTH_SHORT).show();
    }

    public boolean isAlreadyCreated(String name) {

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

}
