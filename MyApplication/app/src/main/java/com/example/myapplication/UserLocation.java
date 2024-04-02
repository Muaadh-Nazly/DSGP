//package com.example.myapplication;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.cardview.widget.CardView;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//import retrofit2.Retrofit;
//
//public class UserLocation extends Context {
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    private final static int REQUEST_CODE = 100;
//    private String userId;
//
//
//    TextView currentLocation;
//
//
//    double string_Latitude;
//    double string_Longitude;
//    String string_Address;
//    String string_City;
//    String string_Country;
//    String string_District;
//
//
//
//
//
//    private void getLastLocation() {
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println();
//
//        } else {
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//
//                    Log.d("********************************", String.valueOf(location));
//
//                    if (location != null) {
//
//                        Log.d("***********************************","success");
//
//                        try {
//                            Geocoder geocoder = new Geocoder(UserActivity.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            if (addresses != null && !addresses.isEmpty()) {
//
//
//                                string_Address = addresses.get(0).getAddressLine(0);
//                                string_City = addresses.get(0).getLocality();
//                                string_Country = addresses.get(0).getCountryName();
//                                string_District = addresses.get(0).getSubAdminArea();
//
//
//                                string_Latitude = location.getLatitude();
//                                string_Longitude = location.getLongitude();
//
//
//                                DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1-serctivity-a5951.asia-southeast1.firebasedatabase.app").getReference().child(userId);
//                                database2.child("Address").setValue(string_Address);
//                                database2.child("City").setValue(string_City);
//                                database2.child("Country").setValue(string_Country);
//                                database2.child("Longitude").setValue(string_Longitude);
//                                database2.child("Latitude").setValue(string_Latitude);
//                                database2.child("District").setValue(string_District);
//
//
//                                Log.d("*****************************","WHY + " + string_City);
//                                Log.d("*****************************","WHY + " + string_District);
//
//                                currentLocation.setText(string_City + "  " + string_District);
//
//
//
//
//
//
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//
//
//
//
//
//
//
//
