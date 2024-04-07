package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WhichLocation extends AppCompatActivity {

    Button currentLocationRequestButton;
    Button selectedLoocationRequestButton;
    String disasterType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_location);

        selectedLoocationRequestButton = findViewById(R.id.selectedLoocationRequestButton);
        currentLocationRequestButton = findViewById(R.id.currentLocationRequestButton);


        Intent intent = getIntent();
        disasterType = intent.getStringExtra("DISASTER");
        Log.d("*****************************************", "Disaster type: " + disasterType);


        selectedLoocationRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedLocation();}
        });


        currentLocationRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationDetails();}
        });

    }


    public void SelectedLocation(){
        Intent intent = new Intent(this,SelectedLocation.class);
        Log.d("*************************************","before selected Location");
        intent.putExtra("DISASTER", String.valueOf(disasterType));
        startActivity(intent);

    }

    public void LocationDetails() {
        Intent intent = new Intent(this, LocationDetails.class);
        intent.putExtra("DISASTER", String.valueOf(disasterType));
        startActivity(intent);
    }


}
