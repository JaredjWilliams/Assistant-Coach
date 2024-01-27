package com.example.runningtimer.ui.profiles;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.runningtimer.R;
import com.example.runningtimer.db.ProfileDatabaseHelper;

public class NewProfilePopup extends ConstraintLayout {

    Animation fadeIn = new AlphaAnimation(0, 1);
    Animation fadeOut = new AlphaAnimation(1, 0);
    ProfileDatabaseHelper profileDb;
    ConstraintLayout saveProfileLayout;
    EditText nameField;
    Button saveButton, cancelButton;
    Context context;

    public NewProfilePopup(Context context, Activity activity) {
        super(context);
        this.context = context;

        profileDb = new ProfileDatabaseHelper(context);

        setupViews(activity);

        setEditTextListener();
        setSaveButtonListener();
        setCancelButtonListener();
    }

    private void setupViews(Activity activity) {
        nameField = activity.findViewById(R.id.name_field);
        saveProfileLayout = activity.findViewById(R.id.save_profile_layout);
        saveButton = activity.findViewById(R.id.save_button);
        cancelButton = activity.findViewById(R.id.cancel_button);

        cancelButton.setBackgroundColor(ContextCompat.getColor(context, R.color.stop_button_color));

        fadeIn.setDuration(1000);
        fadeOut.setDuration(1000);
        setAnimationListener();
        setFadeOutListener();
    }

    private void setSaveButtonListener() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString().trim().toLowerCase();
                profileDb.addProfile(name);
            }
        });
    }

    private void setCancelButtonListener() {
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileLayout.startAnimation(fadeOut);
            }
        });
    }

    private void setEditTextListener() {
        nameField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE) {
                    saveProfileLayout.setVisibility(View.GONE);
                }

                return false;
            }
        });
    }

    public void showPopUp() {
        saveProfileLayout.startAnimation(fadeIn);
    }


    private void setAnimationListener() {
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                saveProfileLayout.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void setFadeOutListener() {
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                saveProfileLayout.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}
