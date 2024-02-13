


package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserLocationService extends AppCompatActivity {


    FusedLocationProviderClient fusedLocationProviderClient;
    TextView string_Address, string_City, string_Country, string_Longitude, string_Latitude;
    Button getLocation;

    private boolean getLocationFirst = true;

    private final static int REQUEST_CODE = 100;



    public String getStringAddress() {
        return string_Address.getText().toString();
    }

    public String getStringCity() {
        return string_City.getText().toString();
    }

    public String getStringCountry() {
        return string_Country.getText().toString();
    }


    public String getStringLongitude() {
        return string_Address.getText().toString();
    }

    public String getStringLatitude() {
        return string_City.getText().toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);

        string_Address = findViewById(R.id.address);
        string_City = findViewById(R.id.city);
        string_Country = findViewById(R.id.country);
        string_Longitude = findViewById(R.id.location);
//        string_Latitude = findViewById(R.id.latitude);

        getLocation = findViewById(R.id.get_location_btn);

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getLocationFirst) {

                    // First click will trigger this
                    getLastLocation();
                    getLocationFirst = false; // Toggle the flag


                } else if(getLocationFirst != true){

                    // Second click will trigger this
                    getLocationFirst = true;// Reset the flag if you want the next click to get the location again
                    Log.d("stop", "Right over here now");
                    UserActivity();

                }

            }
        });
    }


    private void askPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
        }

    }


    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission();
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses != null && !addresses.isEmpty()) {

                                string_Latitude.setText("Latitude :" + location.getLatitude());
                                string_Longitude.setText("Longitude: " + location.getLongitude());
                                string_Address.setText("Address: " + addresses.get(0).getAddressLine(0));
                                string_City.setText("City: " + addresses.get(0).getLocality());
                                string_Country.setText("Country: " + addresses.get(0).getCountryName());

                                Intent intent = new Intent(UserLocationService.this, UserActivity.class);
                                intent.putExtra("latitude", string_Latitude.getText().toString());
                                intent.putExtra("longitude", string_Longitude.getText().toString());
                                intent.putExtra("string_Address", string_Address.getText().toString());
                                intent.putExtra("string_City", string_City.getText().toString());
                                intent.putExtra("country", string_Country.getText().toString());
                                startActivity(intent);


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void UserActivity(){
        Intent intent = new Intent(this, com.example.myapplication.UserActivity.class);
        System.out.println(string_Latitude);
        startActivity(intent);
        finish();

    }


}
