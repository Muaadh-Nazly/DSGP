package com.example.myapplication;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean isGetStartedLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started);

        Button getStartedButton = findViewById(R.id.get_started);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isGetStartedLoaded) {
                    // Load activity_main.xml dynamically
                    setContentView(R.layout.main);
                    isGetStartedLoaded = true;

                    ImageView imageView = findViewById(R.id.imageView);
                    ImageView imageView2 = findViewById(R.id.imageView2);
                    ImageView imageView3 = findViewById(R.id.imageView3);

                    imageView.setImageResource(R.drawable.facebook);
                    imageView2.setImageResource(R.drawable.twitter);
                    imageView3.setImageResource(R.drawable.g_logo);

                    // Set up loginButton click listener in main.xml
                    Button loginButton = findViewById(R.id.loginButton);
                    loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Your login logic here
                            EditText username = findViewById(R.id.username);
                            EditText password = findViewById(R.id.password);
                            if (username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                                // Login successful, load the ClothingActivity
                                loadActivity();
                            } else {
                                Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void loadActivity() {
        setContentView(R.layout.activity_main);

        ImageView imageView_1 = findViewById(R.id.image_1);
        ImageView imageView_2 = findViewById(R.id.image_2);
        ImageView imageView_3 = findViewById(R.id.image_3);
        ImageView imageView_4 = findViewById(R.id.image_4);
        ImageView imageView_5 = findViewById(R.id.image_5);
        ImageView imageView_6 = findViewById(R.id.image_6);

        imageView_1.setImageResource(R.drawable.flood);
        imageView_2.setImageResource(R.drawable.landslide);
        imageView_3.setImageResource(R.drawable.cyclone);
        imageView_4.setImageResource(R.drawable.fullreport);
        imageView_5.setImageResource(R.drawable.aboutus);
        imageView_6.setImageResource(R.drawable.images);

        TextView greetNameTextView = findViewById(R.id.greetName);

        // Get the current time
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        int hour = Integer.parseInt(hourFormat.format(currentTime.getTime()));

        // Set greeting based on the time of the day
        String greeting = getGreeting(hour);
        greetNameTextView.setText(greeting);

        CardView FloodCard = findViewById(R.id.floodCard);
        CardView LandslideCard = findViewById(R.id.landslideCard);
        CardView cycloneCard = findViewById(R.id.CycloneCard);
        CardView ReportCard = findViewById(R.id.fullreportCard);
        CardView AboutUsCard = findViewById(R.id.aboutusCard);
        CardView UserCard = findViewById(R.id.userCard);


        FloodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
                intent.putExtra("targetActivity", LandslideActivity.class);
                startActivity(intent);
            }
        });

        LandslideCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
                intent.putExtra("targetActivity", LandslideActivity.class);
                startActivity(intent);
            }
        });

        cycloneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
                intent.putExtra("targetActivity", CycloneActivity.class);
                startActivity(intent);
            }
        });

        ReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
                intent.putExtra("targetActivity",ReportActivity.class);
                startActivity(intent);
            }
        });

        AboutUsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
                intent.putExtra("targetActivity",AboutUsActivity.class);
                startActivity(intent);
            }
        });

        UserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
                intent.putExtra("targetActivity",UserActivity.class);
                startActivity(intent);
            }
        });

    }

    private String getGreeting(int hour) {
        if (hour >= 0 && hour < 12) {
            return "Good Morning User ";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon User ";
        } else {
            return "Good Evening User ";
        }
    }
}
