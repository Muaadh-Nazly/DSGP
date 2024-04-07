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
import android.Manifest;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Act as the User Home Page in the Application
 */
public class UserActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    private String userId;

    CardView cycloneCard, landslideCard, floodCard,fullReportCard,aboutusCard,userCard;
    TextView currentLocation;


    double string_Latitude;
    double string_Longitude;
    String string_Address;
    String string_City;
    String string_Country;
    String string_District;


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
        currentLocation = findViewById(R.id.currentLocation);



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();

        getLastLocation();
        currentLocation.setText(string_City  + " " + string_District);



        cycloneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CycloneActivity();
            }
        });

        floodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { FloodActivity();
            }
        });

        landslideCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { LandslideActivity();}
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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

                                currentLocation.setText(string_City + "  " + string_District);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }



    public void CycloneActivity() {

        Intent intent = new Intent(this, WhichLocation.class);
        intent.putExtra("DISASTER", "cyclone");
        startActivity(intent);
    }

    public void LandslideActivity() {

        Intent intent = new Intent(this, WhichLocation.class);
        intent.putExtra("DISASTER", "landslide");
        startActivity(intent);
    }

    public void FloodActivity() {
        Log.d("**********************************************","did I even came here for FLOOD ACTIVITY"  );

        Intent intent = new Intent(this, WhichLocation.class);
        intent.putExtra("DISASTER", "flood");
        startActivity(intent);
    }

    public void ReportActivity() {

        Intent intent = new Intent(this, WhichLocation.class);
        intent.putExtra("DISASTER", "full-report");
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



}