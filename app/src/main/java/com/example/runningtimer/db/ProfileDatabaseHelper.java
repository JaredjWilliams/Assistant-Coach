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
import com.example.runningtimer.stopwatch.models.race.Race;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private List<Profile> listOfProfiles = new ArrayList<>();
    private RaceTable raceTable;
    private ProfileTable profileTable;
    private static final String DATABASE_NAME = "Profiles.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "profiles";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ATHLETE_NAME = "athlete_name";
    private static final String COLUMN_PROFILE_PICTURE = "profile_picture";

    private static final String RACE_TABLE_NAME = "races";
    private static final String COLUMN_RACE_TIME = "time";
    private static final String COLUMN_DATE_OF_RACE = "date_of_race";
    private static final String COLUMN_RACE_NAME = "race_name";
    private static final String COLUMN_RACE_DISTANCE = "race_distance";

    public ProfileDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        profileTable.createProfileTable(sqLiteDatabase);
        raceTable.createRaceTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RACE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addRace(String name, String time, String raceName, String raceDistance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ATHLETE_NAME, capitalizeName(name));
        cv.put(COLUMN_RACE_NAME, raceName);
        cv.put(COLUMN_RACE_DISTANCE, raceDistance);
        cv.put(COLUMN_DATE_OF_RACE, LocalDateTime.now().toString());
        cv.put(COLUMN_RACE_TIME, time);

        try {
            long result = db.insert(RACE_TABLE_NAME, null, cv);
            onDataInsert(result);
        } catch (SQLException e) {
            e.printStackTrace();
            onDataInsert(-1);
        }
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

    public Profile retrieveProfileFor(String NAME) {
        String query = " SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ATHLETE_NAME +
                " LIKE '" + NAME + "';";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        try {
            cursor.moveToFirst();
            return new Profile(cursor.getString(1), cursor.getBlob(2));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Profile("Something went wrong :( ", new byte[] {});
        }
    }

    public List<Race> retrieveRacesFor(String NAME) {
        String query = " SELECT * FROM " + RACE_TABLE_NAME +
                " WHERE " + COLUMN_ATHLETE_NAME +
                " LIKE '" + NAME + "';";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        try {
            List<Race> raceList = new ArrayList<>();
            while (cursor.moveToNext()) {
                raceList.add(new Race(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            }

            return raceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
