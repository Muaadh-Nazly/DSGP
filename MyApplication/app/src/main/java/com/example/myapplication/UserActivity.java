package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.Manifest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserActivity extends AppCompatActivity  {

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    private String userId;

    String sending_application_User;

    CardView cycloneCard,landslideCard,floodCard;
    TextView greetingsOfTheDay;
    TextView currentLocation;


    double string_Latitude;
    double string_Longitude;
    String string_Address;
    String string_City;
    String string_Country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        cycloneCard = findViewById(R.id.CycloneCard);
        landslideCard = findViewById(R.id.LandslideCard);
        floodCard = findViewById(R.id.FloodCard);

        greetingsOfTheDay = findViewById(R.id.greetingsOfTheDay);
        currentLocation = findViewById(R.id.currentLocation);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        greetingsOfTheDay.setText(getGreeting());

        getLastLocation();

        cycloneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CycloneActivity();
            }
        });



        cycloneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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






    private void getLastLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println();

        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(UserActivity.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses != null && !addresses.isEmpty()) {



                                string_Address = addresses.get(0).getAddressLine(0);
                                string_City = addresses.get(0).getLocality();
                                string_Country = addresses.get(0).getCountryName();

                                string_Latitude = location.getLatitude();
                                string_Longitude = location.getLongitude();


                                DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1-serctivity-a5951.asia-southeast1.firebasedatabase.app").getReference().child(userId);
                                database2.child("Address").setValue(string_Address);
                                database2.child("City").setValue(string_City);
                                database2.child("Country").setValue(string_Country);
                                database2.child("Longitude").setValue(string_Longitude);
                                database2.child("Latitude").setValue(string_Latitude);

                                currentLocation.setText(string_City + ", " + string_Country);


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }


        private String getGreeting() {

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



    public void CycloneActivity(){

        Intent intent = new Intent(this, CycloneActivity.class);
        startActivity(intent);

    }

    public void LandslideActivity(){

        Intent intent = new Intent(this, LandslideActivity.class);
        startActivity(intent);

    }


    public void FloodActivity(){

        Intent intent = new Intent(this, FloodActivity.class);
        startActivity(intent);

    }


    public void mapActivity(){
        Intent intent = new Intent(this, FloodActivity.class);
        startActivity(intent);
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




//        Log.d("The variable's type is: ", "Now here");
//
//        Intent serviceIntent = new Intent(this, UserLocationService.class);
//        ContextCompat.startForegroundService(this, serviceIntent);


//
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://natural-disaster-predict-1-serctivity-a5951.asia-southeast1.firebasedatabase.app");
//        DatabaseReference myRef = database.getReference();
//        myRef.setValue("Hello,Man!");


//
//
//        // These are Displaying Top of The each User Home Page
//        currentDateTime = findViewById(R.id.currentDateTime);
//        currentDateTime.setText(getGreeting());
//
//


//        String userIdUpdate = userId.replace(".", ",");
// Unique user ID
//        UserLocationService userLocationService = new UserLocationService();
//        userLocationService.getStringCity();


//        Intent intent = new Intent(this, UserLocationService.class);
//        startActivityForResult(intent, "string_City"); // SOME_REQUEST_CODE is an integer constant

//    FirebaseAuth google_Account_Firebase_Authenticator_User_Instance;
//    FirebaseUser application_User;

// Adding a nested child reference and setting its value
//                                database2.child("child1").child("nestedChild").setValue("nestedValue");


//    FirebaseDatabase database = FirebaseDatabase.getInstance("https://natural-disaster-predict-1-serctivity-a5951.asia-southeast1.firebasedatabase.app");
//    DatabaseReference myReference = database.getReference(userId);
//                                myReference.setValue(string_City);

//                                Log.d("*************************************","Am I here");
//
//                                database2.child("Address").setValue(string_Address);
//                                database2.child("City").setValue(string_City);
//                                database2.child("Country").setValue(string_Country);
//                                database2.child("Longitude").setValue(string_Longitude);
//                                database2.child("Latitude").setValue(string_Latitude);
//




//                                Log.d("****************Now what is it*",userId);
// Assuming databaseRef is your existing reference

/**
 *
 */

//        cycloneCard.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            // Navigate to the RegisterActivity activity when Get Started is clicked
//            CycloneActivity();
//        }
//    });
//
//
//        floodCard.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            // Navigate to the RegisterActivity activity when Get Started is clicked
//            FloodActivity();
//        }
//    });
//
//        landslideCard.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            // Navigate to the RegisterActivity activity when Get Started is clicked
//            LandslideActivity();
//        }
//    });
//
//
//}
//
//
//    public void CycloneActivity(){
//        Intent intent = new Intent(this, CycloneActivity.class);
//        startActivity(intent);
//    }
//
//    public void FloodActivity(){
//        Intent intent = new Intent(this, FloodActivity.class);
//        startActivity(intent);
//    }
//
//    public void LandslideActivity(){
//        Intent intent = new Intent(this, LandslideActivity.class);
//        startActivity(intent);
//    }
//
//
//    private String getGreeting () {
//
//        // Get the current time
//        Calendar currentTime = Calendar.getInstance();
//        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
//        int hour = Integer.parseInt(hourFormat.format(currentTime.getTime()));
//
//
//        if (hour >= 0 && hour < 12) {
//            return "Good Morning User ";
//        } else if (hour >= 12 && hour < 17) {
//            return "Good Afternoon User ";
//        } else {
//            return "Good Evening User ";
//        }
//
//    }
