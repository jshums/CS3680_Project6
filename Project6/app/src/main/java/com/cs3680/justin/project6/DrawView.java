package com.cs3680.justin.project6;

/**
 * Created by justi on 4/1/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View implements View.OnTouchListener {
    float nextX, nextY;
    Bitmap bitmap;

    public DrawView(Context context) {
        super(context);
        setup();
    }

    public DrawView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setup();
    }

    private void setup() {
        nextX = 50.0f;
        nextY = 50.0f;
        setOnTouchListener(this);
        // IMAGES SHOULD BE LOADED IN AN AsyncTask
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.forest);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.i("CS3680", "Touch event " + event.getX() + "," + event.getY());
            nextX = event.getX();
            nextY = event.getY();
            invalidate();
        }
        return true;
    }

    public void onDraw(Canvas canvas) {
        // SHOULD CHECK TO SEE IF BITMAPS HAVE BEEN LOADED BEFORE DRAWING

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                canvas.drawBitmap(bitmap, nextX + (i * 60), nextY + (j * 60), null);
            }
        }
    }
}