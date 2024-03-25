package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class UserLocationPermissionActivity extends AppCompatActivity {


    private final static int REQUEST_CODE = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button user_Location_access;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location_permission);

        user_Location_access = findViewById(R.id.user_Location_access);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        user_Location_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();

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
            UserActivity();

        } else {
            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
        }

    }


    public void UserActivity(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }



}