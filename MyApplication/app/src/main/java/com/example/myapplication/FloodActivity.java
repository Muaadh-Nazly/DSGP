package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FloodActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Error: Map fragment not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sri Lanka and move the camera
        LatLng sriLanka = new LatLng(7.8731, 80.7718); // Sri Lanka's coordinates
        mMap.addMarker(new MarkerOptions().position(sriLanka).title("Sri Lanka"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sriLanka, 7)); // Zoom level 7

        // Add markers for natural disaster warnings (example)
        LatLng floodLocation = new LatLng(7.8731, 80.7718); // Example flood location
        mMap.addMarker(new MarkerOptions().position(floodLocation).title("Flood Warning").snippet("Danger zone!"));
        // You can add more markers or overlays as needed
    }
}
