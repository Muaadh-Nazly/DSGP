package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    // Set the duration for which the loading screen will be shown
    private static final int LOADING_DURATION = 3200; // in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Get the target activity class from the intent extras
        Class<?> targetActivity = (Class<?>) getIntent().getSerializableExtra("targetActivity");

        // Call method to start the desired activity after the loading duration
        startDelayedActivity(targetActivity);
    }

    // Method to start any specified activity after the loading duration
    private void startDelayedActivity(final Class<?> cls) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the specified activity after the loading duration
                Intent intent = new Intent(LoadingActivity.this, cls);
                startActivity(intent);
                finish(); // Finish the loading activity to prevent going back to it
            }
        }, LOADING_DURATION);
    }
}