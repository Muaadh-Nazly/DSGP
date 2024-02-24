package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
//import java.util.logging.Handler;
import android.os.Handler;
import android.os.Looper;



import java.io.IOException;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;










//
//public class UserLocationService extends AppCompatActivity {
//
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    String string_Address, string_City, string_Country;
//    Double string_Longitude, string_Latitude;
//
//
//    private final static int REQUEST_CODE = 100;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//
//        // Initialize FusedLocationProviderClient
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        getLastLocation();
//
//    }
//
//
//    public void getLastLocation() {
//
//        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//
//
//        if (ContextCompat.checkSelfPermission(UserLocationPermissionActivity.class, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println();
//
//        } else {
//
//                @Override
//                public void onSuccess(Location location) {
//
//                    Log.d("TEST***","on the methood");
//
//                    if (location != null) {
//                        try {
//                            Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            if (addresses != null && !addresses.isEmpty()) {
//
//                                Log.d("TEST***","before asking");
//
//
//                                string_Latitude = location.getLatitude();
//                                string_Longitude = location.getLongitude();
//                                string_Address = addresses.get(0).getAddressLine(0);
//                                string_City =  addresses.get(0).getLocality();
//                                string_Country =  addresses.get(0).getCountryName();
//
//
//                                Log.d("The variable's type is: " , String.valueOf(string_Latitude));
//                                Log.d("The variable's type is: " , String.valueOf(string_Longitude));
//                                Log.d("The variable's type is: " , string_Address);
//                                Log.d("The variable's type is: " , string_City);
//                                Log.d("The variable's type is: " , string_Country);
//
//
//
//
//                            }
//                            else {
//                                Log.d("TEST***","Address is null");
//                                }
//
//                            }
//                         catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//
//}


/**
 *
 */

//
//public class UserLocationService extends Service {
//
//    private final int INTERVAL = 300000; // 5 minutes in milliseconds
//    private Handler handler = new Handler();
//    private FusedLocationProviderClient fusedLocationProviderClient;
//
//
//
//    double string_Latitude;
//    double string_Longitude;
//    String string_Address;
//    String string_City;
//
//    String string_Country;
//
//
//
//
//    private Runnable locationRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Log.d("******************************************************************", "Location: ");
//            getLastLocation();
//            handler.postDelayed(this, INTERVAL);
//        }
//    };
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.d("******************************************************************", "Location: ");
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//    }
//
//    private void getLastLocation() {
//        Log.d("UserLocationService", "Location: ");
//
//
//
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d("UserLocationService", "Location: ");
//
//        }
//        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
//                    if (location != null) {
//                        // Process the location (e.g., log it, store it, or send it to a server)
////                Log.d("UserLocationService", "Location: " + location.toString());
////                Log.d("UserLocationService", "Location: ");
//
//
//                        try {
//                            Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            if (addresses != null && !addresses.isEmpty()) {
//
//                                Log.d("*****************************************************", "before asking");
//
//
//                                string_Latitude = location.getLatitude();
//                                string_Longitude = location.getLongitude();
//                                string_Address = addresses.get(0).getAddressLine(0);
//                                string_City = addresses.get(0).getLocality();
//                                string_Country = addresses.get(0).getCountryName();
//
//
//                                Log.d("The variable's type is: ", String.valueOf(string_Latitude));
//                                Log.d("The variable's type is: ", String.valueOf(string_Longitude));
//                                Log.d("The variable's type is: ", string_Address);
//                                Log.d("The variable's type is: ", string_City);
//                                Log.d("The variable's type is: ", string_Country);
//
//
//                            } else {
//                                Log.d("********************************************************", "Address is null");
//                            }
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//        );
//
//
//    }
//
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        handler.post(locationRunnable); // Start the periodic location updates
//        return START_STICKY; // Restart service if it gets terminated
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        handler.removeCallbacks(locationRunnable); // Stop the location updates when the service is destroyed
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null; // We don't provide binding, so return null
//    }
//}

















//
//
//
//
//
//
//
//public class UserLocationService extends AppCompatActivity {
//
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    TextView string_Address, string_City, string_Country, string_Longitude, string_Latitude;
//    Button getLocation;
//
//    private boolean getLocationFirst = true;
//
//    private final static int REQUEST_CODE = 100;
//
//
//    public String getStringAddress() {
//        return string_Address.getText().toString();
//    }
//
//    public String getStringCity() {
//        return string_City.getText().toString();
//    }
//
//    public String getStringCountry() {
//        return string_Country.getText().toString();
//    }
//
//
//    public String getStringLongitude() {
//        return string_Address.getText().toString();
//    }
//
//    public String getStringLatitude() {
//        return string_City.getText().toString();
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_location);
//
//        string_Address = findViewById(R.id.address);
//        string_City = findViewById(R.id.city);
//        string_Country = findViewById(R.id.country);
//        string_Longitude = findViewById(R.id.location);
////        string_Latitude = findViewById(R.id.latitude);
//
//        getLocation = findViewById(R.id.get_location_btn);
//
//        // Initialize FusedLocationProviderClient
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        getLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (getLocationFirst) {
//
//                    // First click will trigger this
//                    getLastLocation();
//                    getLocationFirst = false; // Toggle the flag
//
//
//                } else if(getLocationFirst != true){
//
//                    // Second click will trigger this
//                    getLocationFirst = true;// Reset the flag if you want the next click to get the location again
//                    Log.d("stop", "Right over here now");
//                    UserActivity();
//
//                }
//
//            }
//        });
//    }
//
//
//    private void askPermission() {
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getLastLocation();
//        } else {
//            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//
//    private void getLastLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            askPermission();
//        } else {
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null) {
//                        try {
//                            Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            if (addresses != null && !addresses.isEmpty()) {
//
//                                string_Latitude.setText("Latitude :" + location.getLatitude());
//                                string_Longitude.setText("Longitude: " + location.getLongitude());
//                                string_Address.setText("Address: " + addresses.get(0).getAddressLine(0));
//                                string_City.setText("City: " + addresses.get(0).getLocality());
//                                string_Country.setText("Country: " + addresses.get(0).getCountryName());
//
//
//                                Log.d("Location",string_Latitude);
//                                string_Longitude.setText("Longitude: " + location.getLongitude());
//                                string_Address.setText("Address: " + addresses.get(0).getAddressLine(0));
//                                string_City.setText("City: " + addresses.get(0).getLocality());
//                                string_Country.setText("Country: " + addresses.get(0).getCountryName());
//
//                                Intent intent = new Intent(UserLocationService.this, UserActivity.class);
//                                intent.putExtra("latitude", string_Latitude.getText().toString());
//                                intent.putExtra("longitude", string_Longitude.getText().toString());
//                                intent.putExtra("string_Address", string_Address.getText().toString());
//                                intent.putExtra("string_City", string_City.getText().toString());
//                                intent.putExtra("country", string_Country.getText().toString());
//                                startActivity(intent);
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
//    public void UserActivity(){
//        Intent intent = new Intent(this, com.example.myapplication.UserActivity.class);
//        System.out.println(string_Latitude);
//        startActivity(intent);
//        finish();
//
//    }
//}
//





//
//                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                                if (user != null) {
//                                    String uid = user.getUid(); // Get the unique user ID
//
//                                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//                                    database.child("users").child(uid).child("location").setValue(new UserLocation(location.getLatitude(), location.getLongitude()))
//                                            .addOnSuccessListener(aVoid -> Log.d("Firebase", "Location successfully written!"))
//                                            .addOnFailureListener(e -> Log.w("Firebase", "Error writing location", e));
//                                }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getLastLocation();
//        } else {
//            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
//        }
//
//    }



//
//public class UserLocationService extends AppCompatActivity {
//
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    String string_Address, string_City, string_Country;
//    Double string_Longitude, string_Latitude;
//
//
//
//    private final static int REQUEST_CODE = 100;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        super.onCreate(savedInstanceState);
//
//
//        // Initialize FusedLocationProviderClient
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        Log.d("string_City", "**********************");
//
//        getLastLocation();
//
//    }
//
//
//
//    private void askPermission() {
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getLastLocation();
//        } else {
//            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//
//    private void getLastLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            askPermission();
//        } else {
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null) {
//                        try {
//                            Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            if (addresses != null && !addresses.isEmpty()) {
//
//                                string_Latitude = location.getLatitude();
//                                string_Longitude = location.getLongitude();
//                                string_Address = addresses.get(0).getAddressLine(0);
//                                string_City = addresses.get(0).getLocality();
//                                string_Country = addresses.get(0).getCountryName();
//
//                                Log.d("string_City", string_City);
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
//    public void UserActivity(){
//        Intent intent = new Intent(this, com.example.myapplication.UserActivity.class);
//        System.out.println(string_Latitude);
//        startActivity(intent);
//        finish();
//
//    }
//}


//
//public class UserLocationService extends AppCompatActivity {
//
//    private static final int REQUEST_LOCATION_PERMISSION = 1;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private LocationRequest locationRequest;
//    private LocationCallback locationCallback;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user); // Make sure you have a layout file named activity_live_location.xml
//        Log.d("********************************","In the xml file");
//
//
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        createLocationCallback();
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//        }
//        else {
//            Log.d("********************************","Permission == True");
//
//            startLocationUpdates();
//        }
//    }
//
//
//
//    private void createLocationCallback() {
//        Log.d("**********************************here?", "now ok?");
//
//        locationCallback = new LocationCallback() {
//
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                if (locationResult == null) {
//                    locationRequest = LocationRequest.create();
//                    locationRequest.setInterval(10000); // 10 seconds
//                    locationRequest.setFastestInterval(5000); // 5 seconds, deprecated in some versions
//                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    // Get latitude and longitude
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//
//                    // Initialize Geocoder
//                    Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
//                    Log.d("**********************************here?", " close");
//
//
//                    try {
//                        // Attempt to get address line, city, and country from location
//                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                        if (!addresses.isEmpty()) {
//                            Address address = addresses.get(0);
//                            String addressLine = address.getAddressLine(0); // Full address
//                            String city = address.getLocality(); // City
//                            String country = address.getCountryName(); // Country
//
////                            // Use the location data here. For example, update the UI or send location to server
////                            runOnUiThread(() -> {
////                                Toast.makeText(UserLocationService.this, "Address: " + addressLine + "\nCity: " + city + "\nCountry: " + country, Toast.LENGTH_LONG).show();
////                            });
//
//                            Log.d("**********************************here?", city);
//
//
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace(); // Handle the exception
//                    }
//                }
//            }
//        };
//    }
//
//
//    private void startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//            return;
//        }
//
//        // Check if the locationRequest is already created to avoid unnecessary re-creation
//        if (locationRequest == null) {
//            Log.d("**********************************here?", "location == null");
//
//        }
//
//        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        startLocationUpdates();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_LOCATION_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Log.d("********************************","should be start location update");
//
//                startLocationUpdates();
//            } else {
//                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}
//
//
//




//public class UserLocationService extends AppCompatActivity {
//
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    String string_Address, string_City, string_Country;
//    Double string_Longitude, string_Latitude;
//
//
//    private final static int REQUEST_CODE = 100;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//
//        // Initialize FusedLocationProviderClient
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        getLastLocation();
//
//    }
//
//
//    public void getLastLocation() {
//
//        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//
//
//        if (ContextCompat.checkSelfPermission(UserLocationPermissionActivity.class, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println();
//
//        } else {
//
//                @Override
//                public void onSuccess(Location location) {
//
//                    Log.d("TEST***","on the methood");
//
//                    if (location != null) {
//                        try {
//                            Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            if (addresses != null && !addresses.isEmpty()) {
//
//                                Log.d("TEST***","before asking");
//
//
//                                string_Latitude = location.getLatitude();
//                                string_Longitude = location.getLongitude();
//                                string_Address = addresses.get(0).getAddressLine(0);
//                                string_City =  addresses.get(0).getLocality();
//                                string_Country =  addresses.get(0).getCountryName();
//
//
//                                Log.d("The variable's type is: " , String.valueOf(string_Latitude));
//                                Log.d("The variable's type is: " , String.valueOf(string_Longitude));
//                                Log.d("The variable's type is: " , string_Address);
//                                Log.d("The variable's type is: " , string_City);
//                                Log.d("The variable's type is: " , string_Country);
//
//
//
//
//                            }
//                            else {
//                                Log.d("TEST***","Address is null");
//                                }
//
//                            }
//                         catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//
//}


/***
 *
 */


//
//public class UserLocationService extends Service {
//
//    private final int INTERVAL = 300000; // 5 minutes in milliseconds
//    private Handler handler = new Handler();
//    private FusedLocationProviderClient fusedLocationProviderClient;
//
//
//
//    double string_Latitude;
//    double string_Longitude;
//    String string_Address;
//    String string_City;
//
//    String string_Country;
//
//
//
//
//    private Runnable locationRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Log.d("******************************************************************", "Location: ");
//            getLastLocation();
//            handler.postDelayed(this, INTERVAL);
//        }
//    };
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.d("******************************************************************", "Location: ");
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//    }
//
//    private void getLastLocation() {
//        Log.d("UserLocationService", "Location: ");
//
//
//
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d("UserLocationService", "Location: ");
//
//        }
//        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
//                    if (location != null) {
//                        // Process the location (e.g., log it, store it, or send it to a server)
////                Log.d("UserLocationService", "Location: " + location.toString());
////                Log.d("UserLocationService", "Location: ");
//
//
//                        try {
//                            Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            if (addresses != null && !addresses.isEmpty()) {
//
//                                Log.d("*****************************************************", "before asking");
//
//
//                                string_Latitude = location.getLatitude();
//                                string_Longitude = location.getLongitude();
//                                string_Address = addresses.get(0).getAddressLine(0);
//                                string_City = addresses.get(0).getLocality();
//                                string_Country = addresses.get(0).getCountryName();
//
//
//                                Log.d("The variable's type is: ", String.valueOf(string_Latitude));
//                                Log.d("The variable's type is: ", String.valueOf(string_Longitude));
//                                Log.d("The variable's type is: ", string_Address);
//                                Log.d("The variable's type is: ", string_City);
//                                Log.d("The variable's type is: ", string_Country);
//
//
//                            } else {
//                                Log.d("********************************************************", "Address is null");
//                            }
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//        );
//
//
//    }
//


/**
 *
 */

//
//public class UserLocationService extends AppCompatActivity {
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    private final static int REQUEST_CODE = 100;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_location);
//
//
//        // Initialize FusedLocationProviderClient
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        getLastLocation();
//    }
//
//
//
//    private void getLastLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println();
//
//        } else {
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null) {
//                        try {
//                            Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            if (addresses != null && !addresses.isEmpty()) {
//
//                                double string_Latitude = location.getLatitude();
//                                double string_Longitude = location.getLongitude();
//                                String string_Address = addresses.get(0).getAddressLine(0);
//                                String string_City = addresses.get(0).getLocality();
//                                String string_Country = addresses.get(0).getCountryName();
//
//
//                                Log.d("*********************************************Location", string_City );
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
//}


/**
 *
 */

//
//public class UserLocationService extends AppCompatActivity {
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    private final static int REQUEST_CODE = 100;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_location);
//
//
//        // Initialize FusedLocationProviderClient
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        getLastLocation();
//    }
//
//
//
//    private void getLastLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println();
//
//        } else {
//            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null) {
//                        try {
//                            Geocoder geocoder = new Geocoder(UserLocationService.this, Locale.getDefault());
//                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            if (addresses != null && !addresses.isEmpty()) {
//
//                                double string_Latitude = location.getLatitude();
//                                double string_Longitude = location.getLongitude();
//                                String string_Address = addresses.get(0).getAddressLine(0);
//                                String string_City = addresses.get(0).getLocality();
//                                String string_Country = addresses.get(0).getCountryName();
//
//
//                                Log.d("*********************************************Location", string_City );
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
//}

//
//public class UserLocationService extends Service {
//
//    private final Handler handler = new Handler();
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private  Runnable locationUpdateRunnable = new Runnable() {
//        @Override
//        public void run() {
//            getLastLocation();
//            handler.postDelayed(this, 300000); // Schedule this runnable to run again in 5 minutes
//        }
//    };
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        startForeground(1, buildNotification()); // Placeholder for actual notification building
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        handler.post(locationUpdateRunnable); // Start the first location update immediately
//        return START_STICKY;
//    }
//
//    private void getLastLocation() {
//        Log.d("****************************************","*************************");
//        // Implement your location fetching logic here
//        // Remember to check for permissions
//    }
//
//    private Notification buildNotification() {
//        // Build a notification to show when running as a foreground service
//        // Refer to Android documentation for NotificationCompat.Builder examples
//        return null;
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null; // No binding provided
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        handler.removeCallbacks(locationUpdateRunnable); // Stop repeating the location update when service is destroyed
//    }
//}
//
