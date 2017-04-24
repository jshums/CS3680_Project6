package com.cs3680.justin.project6;

import android.support.v4.app.Fragment;

public class AdventureMapActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment () {
        return AdventureMapFragment.newInstance();
    }


}