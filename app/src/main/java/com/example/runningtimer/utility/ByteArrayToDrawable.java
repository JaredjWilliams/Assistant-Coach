package com.example.runningtimer.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ByteArrayToDrawable {


    public ByteArrayToDrawable() {

    }

    public static Drawable byteArrayToDrawable(byte[] imageInBytes, Context context) {
        return new BitmapDrawable(context.getResources(), decodeByteArray(imageInBytes));
    }

    public static Bitmap decodeByteArray(byte[] imageInBytes) {
        return BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
    }
}
