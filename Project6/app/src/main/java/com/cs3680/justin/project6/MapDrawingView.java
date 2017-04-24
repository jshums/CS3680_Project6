package com.cs3680.justin.project6;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.List;

public class MapDrawingView extends View implements View.OnTouchListener {
    float nextX, nextY;
    int canvasHeight;

    Bitmap mForestBitmap;
    Bitmap mMountainBitmap;
    Bitmap mPlainBitmap;
    Bitmap mWaterBitmap;
    Bitmap mPlayerBitmap;
    Bitmap mOutBitmap;
    Bitmap mScaledMountainBitmap;
    Bitmap mScaledPlainBitmap;
    Bitmap mScaledForestBitmap;
    Bitmap mScaledWaterBitmap;
    Bitmap mScaledPlayerBitmap;
    Bitmap mScaledOutBitmap;

    Context context;

    Point playerPos  = new Point(1,5);
    Point mapOffset = new Point (1,0);

    boolean drawPlayer = false;
    boolean drawMap = false;

    String [][] imageMapping = new String[][] {
            { "M", "M", "M", ".", ".", ".", ".", ".", ".", "M", "M", "M" },
            { "M", "M", "f", "f", ".", ".", ".", ".", "f", "f", "M", "M" },
            { "M", "f", "f", ".", ".", "~", "~", ".", ".", "f", "f", "M" },
            { "f", "f", ".", ".", "~", "~", "~", "~", ".", ".", "f", "f" },
            { ".", ".", ".", "~", "~", "~", "~", "~", "~", ".", ".", "." },
            { ".", ".", "~", "~", "~", "~", "~", "~", "~", "~", ".", "." },
            { ".", ".", "~", "~", "~", "~", "~", "~", "~", "~", ".", "." },
            { ".", ".", ".", "~", "~", "~", "~", "~", "~", ".", ".", "." },
            { "f", "f", ".", ".", "~", "~", "~", "~", ".", ".", "f", "f" },
            { "M", "f", "f", ".", ".", "~", "~", ".", ".", "f", "f", "M" },
            { "M", "M", "f", "f", ".", "~", "~", ".", "f", "f", "M", "M" },
            { "M", "M", "M", ".", ".", "~", "~", ".", ".", "M", "M", "M" }
    };

    public MapDrawingView(Context context) {
        super(context);
        this.context = context;
        setup();
    }

    public MapDrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        setup();
    }

    private void setup() {
        nextX = 0.0f;
        nextY = 0.0f;
        setOnTouchListener(this);
        drawMap = true;
        drawPlayer = true;

        new FetchItemsTask().execute();
    }


    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.i("CS3680", "Touch event " + event.getX() + "," + event.getY());
            nextX = event.getX();
            nextY = event.getY();

            int viewWidth, viewHeight;

            viewHeight = v.getHeight();
            viewWidth = v.getWidth();

            if (event.getY() <= v.getHeight() * .25) {
                Log.i("CS3680", "Top 25% was touched");
                if ((playerPos.y - mapOffset.y) - 1 >= 0) {
                    playerPos.set(playerPos.x, playerPos.y - 1);
                    drawPlayer = true;
                    invalidate();
                    Log.i("CS3680", "Moved Up -- new position: " + playerPos.x + "," + playerPos.y);
                }
            } else if (event.getY() > viewHeight * .25 && event.getY() < viewHeight * .75 &&
                    event.getX() < viewWidth * .5) {
                Log.i("CS3680", "Left side touched");
                if ((playerPos.x - mapOffset.x) - 1 >= 0) {
                    playerPos.set(playerPos.x - 1, playerPos.y);
                    drawPlayer = true;
                    invalidate();
                    Log.i("CS3680", "Moved Left -- new position: " + playerPos.x + "," + playerPos.y);
                }
            } else if (event.getY() > viewHeight * .25 && event.getY() < viewHeight * .75 &&
                    event.getX() > viewWidth * .5) {
                Log.i("CS3680", "Right side touched");
                if ((playerPos.x - mapOffset.x) + 1 < 6) {
                    playerPos.set(playerPos.x + 1, playerPos.y);
                    drawPlayer = true;
                    invalidate();
                    Log.i("CS3680", "Moved Right -- new position: " + playerPos.x + "," + playerPos.y);
                }
            } else if (event.getY() >= viewHeight * .75) {
                Log.i("CS3680", "Bottom 25% was touched");
                if ((playerPos.y - mapOffset.y) + 1 < 8) {
                    playerPos.set(playerPos.x, playerPos.y + 1);
                    drawPlayer = true;
                    invalidate();
                    Log.i("CS3680", "Moved Down -- new position: " + playerPos.x + "," + playerPos.y);
                }
            }
        }
        return true;
    }


    public void onDraw(Canvas canvas) {
        canvasHeight = canvas.getHeight();

        if (drawMap) {
            for (int i = mapOffset.x; i < imageMapping.length ; i++) {
                for (int j = mapOffset.y; j < imageMapping[i].length; j++) {
                    String mapValue;
                    mapValue = imageMapping[j][i];

                    switch (mapValue) {
                        case "M":
                            canvas.drawBitmap(mScaledMountainBitmap, ((i - mapOffset.x) * mScaledMountainBitmap
                                    .getWidth()), ((j - mapOffset.y) * mScaledMountainBitmap.getHeight()) , null);
                            break;
                        case ".":
                            canvas.drawBitmap(mScaledPlainBitmap, ((i - mapOffset.x) * mScaledPlainBitmap
                                    .getWidth()), ((j - mapOffset.y) * mScaledPlainBitmap.getHeight()), null);
                            break;
                        case "f":
                            canvas.drawBitmap(mScaledForestBitmap, ((i - mapOffset.x) * mScaledForestBitmap
                                    .getWidth()), ((j - mapOffset.y) * mScaledForestBitmap.getHeight()), null);
                            break;
                        case "~":
                            canvas.drawBitmap(mScaledWaterBitmap, ((i - mapOffset.x) * mScaledWaterBitmap
                                    .getWidth()), ((j - mapOffset.y) * mScaledWaterBitmap.getHeight()), null);
                            break;
                    }
                }
            }
        }

        if (drawPlayer) {
            canvas.drawBitmap(mScaledPlayerBitmap, (playerPos.x - mapOffset.x)
                            * mScaledPlayerBitmap.getWidth(),
                    (playerPos.y - mapOffset.y) * mScaledPlayerBitmap.getHeight(),null);
            drawPlayer = false;
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<Bitmap>> {
        @Override
        protected List<Bitmap> doInBackground(Void... params) {
            return new MapImageFetcher(context).fetchItems();
        }

        @Override
        protected void onPostExecute (List<Bitmap> bitmaps) {

            mForestBitmap = bitmaps.get(0);
            mMountainBitmap = bitmaps.get(1);
            mPlainBitmap = bitmaps.get(2);
            mWaterBitmap = bitmaps.get(3);
            mPlayerBitmap = bitmaps.get(4);
            mOutBitmap = bitmaps.get(5);

            float scaleFactor;
            int mapTileNumber = 6;
            int tilePixelCount = mForestBitmap.getWidth();
            int tileDisPixelCount = mapTileNumber * tilePixelCount;
            float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

            scaleFactor = screenWidth / tileDisPixelCount;

            mScaledMountainBitmap = Bitmap.createScaledBitmap(mMountainBitmap,
                    (int)(mMountainBitmap.getWidth() * scaleFactor),
                    (int)(mMountainBitmap.getHeight() * scaleFactor), true);
            mScaledPlainBitmap = Bitmap.createScaledBitmap(mPlainBitmap,
                    (int)(mPlainBitmap.getWidth() * scaleFactor),
                    (int)(mPlainBitmap.getHeight() * scaleFactor), true);
            mScaledForestBitmap = Bitmap.createScaledBitmap(mForestBitmap,
                    (int)(mForestBitmap.getWidth() * scaleFactor),
                    (int)(mForestBitmap.getHeight() * scaleFactor), true);
            mScaledWaterBitmap = Bitmap.createScaledBitmap(mWaterBitmap,
                    (int)(mWaterBitmap.getWidth() * scaleFactor),
                    (int)(mWaterBitmap.getHeight() * scaleFactor), true);
            mScaledPlayerBitmap = Bitmap.createScaledBitmap(mPlayerBitmap,
                    (int)(mPlayerBitmap.getWidth() * scaleFactor),
                    (int)(mPlayerBitmap.getHeight() * scaleFactor), true);
            mScaledOutBitmap = Bitmap.createScaledBitmap(mOutBitmap,
                    (int)(mOutBitmap.getWidth() * scaleFactor),
                    (int)(mOutBitmap.getHeight() * scaleFactor), true);
        }
    }
}