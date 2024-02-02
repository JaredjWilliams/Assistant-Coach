package com.example.runningtimer.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProfileDatabaseHelperTests {

    ProfileDatabaseHelper profileDb;
    @Mock SQLiteOpenHelper openHelper;
    @Mock Context context;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        profileDb = new ProfileDatabaseHelper(context);
    }

    @Test
    public void testRetrieveProfile() {

    }

}
