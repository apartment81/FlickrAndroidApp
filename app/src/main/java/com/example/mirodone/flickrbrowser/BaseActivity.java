package com.example.mirodone.flickrbrowser;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {

    static final String FLICKR_QUERY = "FLICKR_QUERY";
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";
    private static final String TAG = "BaseActivity";

    void activateToolbar(boolean enableHome) {
        Log.d(TAG, "ActivateToolbar: starts");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {

            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);

            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }

}
