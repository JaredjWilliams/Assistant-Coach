package com.example.runningtimer.presenters;

import static org.mockito.Mockito.mock;

import android.content.Context;
import android.content.res.Resources;

import com.example.runningtimer.db.ProfileDatabaseHelper;
import com.example.runningtimer.stopwatch.models.Profile;
import com.example.runningtimer.ui.profile.ProfileViewInterface;
import com.example.runningtimer.utility.ByteArrayToDrawable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ProfilePresenterTests {

    @Mock ProfileDatabaseHelper profileDb;
    @Mock Context context;
    @Mock ProfileViewInterface view;
    @Mock Resources resources;

    ByteArrayToDrawable mockByteArrayToDrawable = mock(ByteArrayToDrawable.class);

    ProfilePresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        presenter = new ProfilePresenter(context, view);
        presenter.profileDb = profileDb;
    }

    @Test
    public void testRetrieveProfile() {
        String name = "Testing";

        presenter.retrieveProfile(name);

        Mockito.verify(profileDb).retrieveProfileFor(name);
    }

    @Test
    public void testUpdateView() {

        Profile profile = new Profile("Testing", new byte[0]);

        presenter.profile = profile;
        presenter.updateView();

        Mockito.verify(view).setProfileNameText("Testing");
        Mockito.verify(view).setProfilePicture();
    }
}
