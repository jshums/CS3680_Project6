package com.cs3680.justin.project6;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class MapImageFetcher extends Application {

    private  static final String TAG = "MapImageFetcher";

    Context context;

    public MapImageFetcher(Context mContexxt) {
        context = mContexxt;
    }

    public List<Bitmap> fetchItems () {
        List<Bitmap> bitmaps = new ArrayList<>();

        Bitmap outBitmap;
        Bitmap playerBitmap;
        Bitmap waterBitmap;
        Bitmap plainBitmap;
        Bitmap mountainBitmap;
        Bitmap forestBitmap;

        try {
            forestBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.forest);
            mountainBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mountain);
            plainBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plain);
            waterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.water);
            playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.person);
            outBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.out);

            bitmaps.add(forestBitmap);
            bitmaps.add(mountainBitmap);
            bitmaps.add(plainBitmap);
            bitmaps.add(waterBitmap);
            bitmaps.add(playerBitmap);
            bitmaps.add(outBitmap);
        } catch (NullPointerException pe){
            Log.e(TAG, "Failed to locate resource", pe);
        }

        return  bitmaps;
    }
}
