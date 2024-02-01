package com.example.runningtimer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.runningtimer.stopwatch.models.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private List<Profile> listOfProfiles = new ArrayList<>();
    private static final String DATABASE_NAME = "Profiles.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "profiles";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ATHLETE_NAME = "athlete_name";
    private static final String COLUMN_PROFILE_PICTURE = "profile_picture";

    public ProfileDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ATHLETE_NAME + " TEXT," +
                COLUMN_PROFILE_PICTURE + " BLOB);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addProfile(String name, byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ATHLETE_NAME, capitalizeName(name));
        cv.put(COLUMN_PROFILE_PICTURE, imageBytes);



        try {
            long result = db.insert(TABLE_NAME, null, cv);
            onDataInsert(result);
        } catch (SQLException e) {
            e.printStackTrace();
            onDataInsert(-1);
        }


    }

    private void onDataInsert(long result) {
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String query = "Select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public List<Profile> readAllDataTest() {
        String query = "Select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return createListProfiles(cursor);
    }

    public String capitalizeName(String name) {
        return Arrays.stream(name.split(" "))
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public void deleteData() {
        String query = "Delete from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(query);
    }

    private List<Profile> createListProfiles(Cursor cursor) {
        listOfProfiles.clear();

        while (cursor.moveToNext()) {
            Profile profile = new Profile(cursor.getString(1), cursor.getBlob(2));
            listOfProfiles.add(profile);
        }

        return listOfProfiles;
    }
}
