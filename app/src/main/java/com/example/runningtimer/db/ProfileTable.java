package com.example.runningtimer.db;

import android.database.sqlite.SQLiteDatabase;

public class ProfileTable {

    private static final String TABLE_NAME = "profiles";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ATHLETE_NAME = "athlete_name";
    private static final String COLUMN_PROFILE_PICTURE = "profile_picture";

    protected void createProfileTable(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ATHLETE_NAME + " TEXT," +
                COLUMN_PROFILE_PICTURE + " BLOB);";

        db.execSQL(query);
    }
}
