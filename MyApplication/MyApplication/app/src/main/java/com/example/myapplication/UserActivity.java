package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
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
import android.widget.Button;
import retrofit2.Retrofit;


/**
 * Act as the User Home Page in the Application
 */
public class UserActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    private String userId;

    String sending_application_User;

    CardView cycloneCard, landslideCard, floodCard,fullReportCard,aboutusCard,userCard;
    TextView greetingsOfTheDay;
    TextView currentLocation;


    double string_Latitude;
    double string_Longitude;
    String string_Address;
    String string_City;
    String string_Country;
    String string_District;

    TextView showMap;

    TextView country, city, district, address, latitude, longitude;
    Button getLocation;
    private TextView windSpeedTextView, rainfallTextView;
    //    private WeatherApi weatherApi;
    private Retrofit retrofit;
    private List<Address> addresses;


    static boolean isFirstTimeLoad;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);



        cycloneCard = findViewById(R.id.CycloneCard);
        landslideCard = findViewById(R.id.landslideCard);
        floodCard = findViewById(R.id.floodCard);
        fullReportCard = findViewById(R.id.fullreportCard);
        aboutusCard = findViewById(R.id.aboutusCard);
        userCard = findViewById(R.id.userCard);
//        changeLocationCardView = findViewById(R.id.changeLocationCardView);
        greetingsOfTheDay = findViewById(R.id.greetName);



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        greetingsOfTheDay.setText(getGreeting());


        Intent intent = getIntent();
        String selectedProvince = intent.getStringExtra("SELECTED_PROVINCE");
        String selectedDistrict = intent.getStringExtra("SELECTED_DISTRICT");
        String selectedCity = intent.getStringExtra("SELECTED_CITY");


        // Setting the user location or select user If user selected a location : Then it will display
//        if (selectedCity != null){
//            currentLocation.setText(selectedCity + " " + selectedDistrict );
//        }
//
//        else {
//            getLastLocation();
//        }
//

        //UserLocation();


        if (isFirstTimeLoad == false){
            Log.d("******************************","CAME 1");
            getLastLocation();
            UserLocation();
            isFirstTimeLoad = true;
        }


        else {
            Log.d("******************************","CAME 2");
            if (selectedCity != null){
                currentLocation.setText(selectedCity + " " + selectedDistrict );
//                UserLocation();
            }
            else {
                getLastLocation();
            }
        }
















        cycloneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CycloneActivity();
            }
        });


//        changeLocationCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SelectedLocation();
//            }
//        });


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

        fullReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportActivity();
            }
        });

        userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSettings();
            }
        });
        aboutusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUsActivity();
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

                    Log.d("********************************", String.valueOf(location));

                    if (location != null) {

                        Log.d("***********************************","success");

                        try {
                            Geocoder geocoder = new Geocoder(UserActivity.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses != null && !addresses.isEmpty()) {


                                string_Address = addresses.get(0).getAddressLine(0);
                                string_City = addresses.get(0).getLocality();
                                string_Country = addresses.get(0).getCountryName();
                                string_District = addresses.get(0).getSubAdminArea();


                                string_Latitude = location.getLatitude();
                                string_Longitude = location.getLongitude();


                                DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1-serctivity-a5951.asia-southeast1.firebasedatabase.app").getReference().child(userId);
                                database2.child("Address").setValue(string_Address);
                                database2.child("City").setValue(string_City);
                                database2.child("Country").setValue(string_Country);
                                database2.child("Longitude").setValue(string_Longitude);
                                database2.child("Latitude").setValue(string_Latitude);
                                database2.child("District").setValue(string_District);


                                Log.d("*****************************","WHY + " + string_City);
                                Log.d("*****************************","WHY + " + string_District);

                                //currentLocation.setText(string_City + "  " + string_District);






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


    public void CycloneActivity() {

        Intent intent = new Intent(this, CycloneActivity.class);
        startActivity(intent);
    }

    public void LandslideActivity() {

        Intent intent = new Intent(this, LandslideActivity.class);
        startActivity(intent);
    }


    public void FloodActivity() {

        Intent intent = new Intent(this, FloodActivity.class);
        startActivity(intent);
    }

    public void ReportActivity() {

        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    public void AboutUsActivity() {

        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }
    public void UserSettings() {

        Intent intent = new Intent(this, UserSettings.class);
        startActivity(intent);
    }



    public void SelectedLocation() {
        Intent intent = new Intent(this, SelectedLocation.class);
        startActivity(intent);
    }


    public void UserLocation(){
        Intent intent = new Intent(this, UserLocation.class);
        startActivity(intent);
    }


}