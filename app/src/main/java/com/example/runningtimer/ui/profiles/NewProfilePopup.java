package com.example.runningtimer.ui.profiles;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class NewProfilePopup extends PopupWindow {

    PopupWindow popUp;
    boolean click = true;

    public NewProfilePopup(Context context) {
        onAddButtonPressed(context);
    }

    public void onAddButtonPressed(Context context) {
        LinearLayout layout = new LinearLayout(context);
        LinearLayout mainLayout = new LinearLayout(context);
        TextView tv = new TextView(context);
        Button but = new Button(context);
        but.setText("Click Me");
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                    popUp.update(50, 50, 300, 80);
                    click = false;
                } else {
                    popUp.dismiss();
                    click = true;
                }
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("Hi this is a sample text for popup window");
        layout.addView(tv, params);
        popUp.setContentView(layout);
        // popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
        mainLayout.addView(but, params);
    }
}
