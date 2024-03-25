package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap gMap;
    FrameLayout map;

    double account_user_latitue;
    double account_user_longitude;
    String account_user_city;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        map = findViewById(R.id.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();


        DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1-serctivity-a5951.asia-southeast1.firebasedatabase.app/").getReference().child(userId);

        database2.child("Longitude").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    account_user_longitude = (double) dataSnapshot.getValue(); // Assuming it's a string
                    System.out.println("*******************************Rainfall data: " + account_user_longitude);
                } else {
                    System.out.println("*********************************longitude data not found");
                }
            } else {
                System.out.println("******************************************Failed to read Rainfall data");
            }
        });

        database2.child("Latitude").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    account_user_latitue = (double) dataSnapshot.getValue(); // Assuming it's a string
                    System.out.println("*******************************Rainfall data: " + account_user_latitue);
                } else {
                    System.out.println("*********************************longitude data not found");
                }
            } else {
                System.out.println("******************************************Failed to read Rainfall data");
            }
        });


        database2.child("City").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    account_user_city = (String) dataSnapshot.getValue(); // Assuming it's a string
                    System.out.println("*******************************Rainfall data: " + account_user_city);
                } else {
                    System.out.println("*********************************longitude data not found");
                }
            } else {
                System.out.println("******************************************Failed to read Rainfall data");
            }
        });


        LatLng mapSL = new LatLng(account_user_latitue, account_user_longitude);

        Marker marker = this.gMap.addMarker(new MarkerOptions().position(mapSL).title("Marker in " + account_user_latitue));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapSL, 10)); // Adjust the zoom level

        // Set a marker click listener
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                showBottomSheetDialog(marker);
                return true;
            }
        });
    }

//    private void showBottomSheetDialog(Marker marker) {
//        // Inflate the view for the bottom sheet dialog
//        View view = LayoutInflater.from(this).inflate(R.layout.custom_info_window, null);
//
//        // Find and set the TextView to display the marker title
//        TextView titleTextView = view.findViewById(R.id.markerTitleTextView);
//        titleTextView.setText(marker.getTitle());
//
//        // Create and show the bottom sheet dialog
//        BottomSheetDialog dialog = new BottomSheetDialog(this);
//        dialog.setContentView(view);
//
//        // Handle button click
//        Button moreDetailsButton = view.findViewById(R.id.moreDetailsButton);
//        moreDetailsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start another activity to load another XML page
//                Intent intent = new Intent(MapActivity.this, LoadingActivity.class);
//                intent.putExtra("targetActivity", More_details.class);
//                startActivity(intent);
//                dialog.dismiss(); // Dismiss the dialog when navigating to another activity
//            }
//        });
//
//        dialog.show();
//    }
}