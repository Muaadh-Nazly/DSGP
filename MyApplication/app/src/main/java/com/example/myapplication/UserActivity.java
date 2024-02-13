package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserActivity extends AppCompatActivity  {

    CardView cycloneCard,landslideCard,floodCard;
    TextView currentDateTime,currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        cycloneCard = findViewById(R.id.CycloneCard);
        landslideCard = findViewById(R.id.LandslideCard);
        floodCard = findViewById(R.id.FloodCard);


        // These are Displaying Top of The each User Home Page
        currentDateTime = findViewById(R.id.currentDateTime);
        currentDateTime.setText(getGreeting());


        UserLocationService userLocationService = new UserLocationService();
        userLocationService.getStringCity();

        cycloneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the RegisterActivity activity when Get Started is clicked
                CycloneActivity();
            }
        });


        floodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the RegisterActivity activity when Get Started is clicked
                FloodActivity();
            }
        });

        landslideCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the RegisterActivity activity when Get Started is clicked
                LandslideActivity();
            }
        });


    }


    public void CycloneActivity(){
        Intent intent = new Intent(this, CycloneActivity.class);
        startActivity(intent);
    }

    public void FloodActivity(){
        Intent intent = new Intent(this, FloodActivity.class);
        startActivity(intent);
    }

    public void LandslideActivity(){
        Intent intent = new Intent(this, LandslideActivity.class);
        startActivity(intent);
    }


    private String getGreeting () {

        // Get the current time
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        int hour = Integer.parseInt(hourFormat.format(currentTime.getTime()));


        if (hour >= 0 && hour < 12) {
            return "Good Morning User ";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon User ";
        } else {
            return "Good Evening User ";
        }

    }



}


/*

//        Log.d("LOCATION",userLocationService.getStringCity());
//        currentLocation.setText(userLocationService.getStringCity());
//        Log.d("LOCATION",currentLocation.toString());


////        intent.putExtra("string_City", cityValue); // cityValue is the actual city name
//        Object location = getIntent().getStringExtra("string_City");
//        if (location != null) {
//            currentLocation.setText((CharSequence) location);
//        } else {
//            currentLocation.setText("Location not available"); // Fallback text
//        }
//
//
//
//
//        Intent intent = getIntent();
//
//
//        if (intent != null) {
//            String city = intent.getStringExtra("string_City");
//
//            // Set the retrieved values to TextViews
//            currentLocation.setText(city);
//        }
 */


