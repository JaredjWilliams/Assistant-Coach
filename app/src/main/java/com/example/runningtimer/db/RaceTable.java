package com.example.runningtimer.db;

import static com.example.runningtimer.db.TableConstants.COLUMN_ATHLETE_NAME;
import static com.example.runningtimer.db.TableConstants.COLUMN_ID;

import android.database.sqlite.SQLiteDatabase;

public class RaceTable {

    private static final String RACE_TABLE_NAME = "races";
    private static final String COLUMN_RACE_TIME = "time";
    private static final String COLUMN_DATE_OF_RACE = "date_of_race";
    private static final String COLUMN_RACE_NAME = "race_name";
    private static final String COLUMN_RACE_DISTANCE = "race_distance";

    public void createRaceTable(SQLiteDatabase db) {
        String query = "CREATE TABLE " + RACE_TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ATHLETE_NAME + " TEXT," +
                COLUMN_RACE_NAME + " TEXT," +
                COLUMN_RACE_DISTANCE + " TEXT," +
                COLUMN_DATE_OF_RACE + " TEXT," +
                COLUMN_RACE_TIME + " TEXT);";

        db.execSQL(query);
    }
}
