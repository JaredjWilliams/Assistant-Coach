package com.example.runningtimer.ui.profiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.runningtimer.R;
import com.example.runningtimer.db.ProfileDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewProfilePopup extends AppCompatActivity {

    ActivityResultLauncher<Intent> pickImageLauncher;
    FloatingActionButton addProfileImageButton;
    ConstraintLayout saveProfileLayout;
    ProfileDatabaseHelper profileDb;
    Button saveButton, cancelButton;
    ProfilesPresenter presenter;
    ImageView profileImage;
    EditText nameField;
    Context context;

    Animation fadeIn = new AlphaAnimation(0, 1);
    Animation fadeOut = new AlphaAnimation(1, 0);

    public NewProfilePopup(Context context, Activity activity, ProfilesPresenter presenter, ActivityResultLauncher<Intent> pickImageLauncher) {
        this.pickImageLauncher = pickImageLauncher;
        this.presenter = presenter;
        this.context = context;

        profileDb = new ProfileDatabaseHelper(context);

        setupViews(activity);


        setupAddProfileImageListener();
        setCancelButtonListener();
        setSaveButtonListener();
        setEditTextListener();

        saveProfileLayout.bringToFront();
    }

    private void setupViews(Activity activity) {
        addProfileImageButton = activity.findViewById(R.id.add_profile_image);
        saveProfileLayout = activity.findViewById(R.id.save_profile_layout);
        cancelButton = activity.findViewById(R.id.cancel_button);
        profileImage = activity.findViewById(R.id.profile_image);
        saveButton = activity.findViewById(R.id.save_button);
        nameField = activity.findViewById(R.id.name_field);

        cancelButton.setBackgroundColor(ContextCompat.getColor(context, R.color.stop_button_color));

        fadeOut.setDuration(500);
        fadeIn.setDuration(500);

        setAnimationListener();
        setFadeOutListener();
    }

    private void setupAddProfileImageListener() {
        addProfileImageButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            pickImageLauncher.launch(intent);
        });
    }

    public void onActivityResultCallback(Uri selectedImage) {
        profileImage.setImageURI(selectedImage);
        addProfileImageButton.setVisibility(View.GONE);
    }

    private void setSaveButtonListener() {
        saveButton.setOnClickListener(view -> {

            String name = nameField.getText().toString();
            Drawable drawable = profileImage.getDrawable();

            presenter.addProfile(name, drawable);
        });
    }

    private void setCancelButtonListener() {
        cancelButton.setOnClickListener(view -> saveProfileLayout.startAnimation(fadeOut));
    }

    private void setEditTextListener() {
        nameField.setOnEditorActionListener((textView, i, event) -> {

            if (i == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                saveProfileLayout.setVisibility(View.GONE);

                InputMethodManager imm = (InputMethodManager) nameField.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nameField.getWindowToken(), 0);
                nameField.clearFocus();
            }

            return false;
        });
    }

    public void showPopUp() {
        saveProfileLayout.startAnimation(fadeIn);
    }

    public void closePopUp() { saveProfileLayout.startAnimation(fadeOut);}

    public void resetLayout() {
        nameField.setText("");
        profileImage.setImageResource(0);
        addProfileImageButton.setVisibility(View.VISIBLE);
    }


    private void setAnimationListener() {
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                saveProfileLayout.setVisibility(View.VISIBLE);
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
                saveProfileLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}
