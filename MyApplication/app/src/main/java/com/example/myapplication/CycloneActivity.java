package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

public class CycloneActivity extends AppCompatActivity {

    GoogleMap gMap;
    FrameLayout map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclone);
    }

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cyclone);
////        Log.d("***************************************************","here 3 :");
//
//        map = findViewById(R.id.map);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        assert mapFragment != null;
//        mapFragment.getMapAsync(this);
//        Log.d("***************************************************","here 4 :");
//    }
//
//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        this.gMap = googleMap;
//        LatLng mapSL = new LatLng(7.0412, 80.1289);
//        Marker marker = this.gMap.addMarker(new MarkerOptions().position(mapSL).title("Marker in Kirindiwela"));
//        this.gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapSL, 10)); // Adjust the zoom level
//
//        // Set a marker click listener
//        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                showBottomSheetDialog(marker);
//                return true;
//            }
//        });
//    }
//
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
//                Intent intent = new Intent(CycloneActivity.this, LoadingActivity.class);
//                intent.putExtra("targetActivity", More_details.class);
//                startActivity(intent);
//                dialog.dismiss(); // Dismiss the dialog when navigating to another activity
//            }
//        });
//
//        dialog.show();
//    }
}