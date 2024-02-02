package com.example.runningtimer.stopwatch.models;

public class Profile {

    private String name;
    private byte[] profilePicture;

    public Profile(String name, byte[] profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public byte[] getProfilePicture() {
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
