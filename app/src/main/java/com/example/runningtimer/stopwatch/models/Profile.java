package com.example.runningtimer.stopwatch.models;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Profile {

    private String name;
    private Drawable profilePicture;

    public Profile(String name, byte[] profilePicture) {
        this.name = name;
        this.profilePicture = byteArrayToDrawable(profilePicture);
    }

    private Drawable byteArrayToDrawable(byte[] imageInBytes) {
        return new BitmapDrawable(Resources.getSystem(),
                BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length));
    }

    public Drawable getProfilePicture() {
        return profilePicture;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", profilePicture=" + profilePicture +
                '}';
    }
}
