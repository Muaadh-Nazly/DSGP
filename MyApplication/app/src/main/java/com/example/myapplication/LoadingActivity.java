package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    private static final int LOADING_DURATION = 3200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Class<?> targetActivity = (Class<?>) getIntent().getSerializableExtra("targetActivity");
        startDelayedActivity(targetActivity);
    }

    private void startDelayedActivity(final Class<?> cls) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, cls);
                startActivity(intent);
                finish();
            }
        }, LOADING_DURATION);
    }
}
