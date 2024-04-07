package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ReportActivity extends FragmentActivity implements OnMapReadyCallback {
    String Location;
    String Location1 ;
    String Location2 ;
    String District;
    String Rainfall ;
    String WindSpeed ;
    LocalDate Today = LocalDate.now();
    GoogleMap gMap;
    double account_user_longitude;
    double account_user_latitue;
    String userId;
    FrameLayout map;
    String url = "https://disaster-predictor-409bdbd99295.herokuapp.com/predict_three";
    public static List<String> predictions = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood);

        map = findViewById(R.id.map);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);

        // Fetch user data and then initialize the map
        fetchUserData(() -> {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(ReportActivity.this);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d("***********1",Location+" "+Location1+" "+Location2);

        this.gMap = googleMap;
        LatLng mapSL = new LatLng(account_user_latitue, account_user_longitude);
        Marker marker = this.gMap.addMarker(new MarkerOptions().position(mapSL).title(Location));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapSL, 10)); // Adjust the zoom level

        // Set a marker click listener
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String floodRFR = jsonObject.getString("Prediction for Flood RFR");
                                    String floodXGB = jsonObject.getString("Prediction for Flood XGB");
                                    String landslideRFR = jsonObject.getString("Prediction for Landslide RFR");
                                    String landslideXGB = jsonObject.getString("Prediction for Landslide XGB");
                                    String cycloneRFR = jsonObject.getString("Prediction for Cyclone RFR");
                                    String cycloneXGB = jsonObject.getString("Prediction for Cyclone XGB");

                                    predictions.add(floodRFR);
                                    predictions.add(floodXGB);
                                    predictions.add(landslideRFR);
                                    predictions.add(landslideXGB);
                                    predictions.add(cycloneRFR);
                                    predictions.add(cycloneXGB);

                                    showBottomSheetDialog(marker);

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ReportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        Log.d("************3",Location+" "+Location1+" "+Location2);
                        if(Location==null & Location1==null & Location2==null)
                            Location = Location1 = Location2 = "Not a value";
                        else if(Location==null & Location1==null)
                            Location = Location1 = Location2;
                        else if (Location==null & Location2==null)
                            Location = Location2 = Location1;
                        else if(Location1==null & Location2==null)
                            Location1 = Location2 = Location;
                        else if(Location==null)
                            Location = Location1;
                        else if(Location1==null)
                            Location1 = Location2;
                        else if(Location2==null)
                            Location2 = Location;

                        params.put("Location", Location);
                        params.put("Location1", Location1);
                        params.put("Location2", Location2);
                        params.put("District", District);
                        params.put("Rainfall(mm)", Rainfall);
                        params.put("Wind Speed(mph)", WindSpeed);
                        Log.d("**************55",Location+" "+Location1+" "+Location2+" "+District+" "+Rainfall+" "+WindSpeed);

                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(ReportActivity.this);
                queue.add(stringRequest);

                return true;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog(Marker marker) {
        progressDialog.dismiss();

        // Inflate the view for the bottom sheet dialog
        View view = LayoutInflater.from(this).inflate(R.layout.custom_info_window, null);

        Button moreDetails = view.findViewById(R.id.moreDetailsButton);
        ViewGroup parentLayout = (ViewGroup) moreDetails.getParent();
        parentLayout.removeView(moreDetails);

        // Find and set the TextView to display the marker title
        TextView floodRFR = view.findViewById(R.id.tdyRFR);
        TextView floodXGB = view.findViewById(R.id.tdyXGB);
        TextView landslideRFR = view.findViewById(R.id.landslideRFR);
        TextView landslideXGB = view.findViewById(R.id.landslideXGB);
        TextView cycloneRFR = view.findViewById(R.id.cycloneRFR);
        TextView cycloneXGB = view.findViewById(R.id.cycloneXGB);

        floodRFR.setText("Prediction for " + Today + " Flood RFR " + predictions.get(0)+"%");
        floodXGB.setText("Prediction for " + Today + " Flood XGB " + predictions.get(1)+"%");
        landslideRFR.setText("Prediction for " + Today + " Landslide RFR " + predictions.get(2)+"%");
        landslideXGB.setText("Prediction for " + Today + " Landslide XGB " + predictions.get(3)+"%");
        cycloneRFR.setText("Prediction for " + Today + " Cyclone RFR " + predictions.get(4)+"%");
        cycloneXGB.setText("Prediction for " + Today + " Cyclone XGB " + predictions.get(5)+"%");

        // Create and show the bottom sheet dialog
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        dialog.show();
    }

    public void fetchUserData(final Runnable onDataFetchedCallback) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();

        Log.d("***************************","isnide the fetch");

        DatabaseReference database3 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-4532a.firebaseio.com/").getReference().child(userId);

        Task<DataSnapshot> latitudeTask = database3.child("Latitude").get();
        Task<DataSnapshot> longitudeTask = database3.child("Longitude").get();


        Task<DataSnapshot> districtTask = database3.child("District").get();
        Task<DataSnapshot> locationTask = database3.child("City").get();


        Task<DataSnapshot> location1Task = database3.child("Near By City 1").get();
        Task<DataSnapshot> location2Task = database3.child("Near By City 2").get();
        Task<DataSnapshot> rainfallTask = database3.child("Rainfall data").child("0").get(); // Make sure this path is correct

        Task<DataSnapshot> windSpeedTask = database3.child("WindSpeed data").child("0").get(); // Make sure this path is correct



        Tasks.whenAll(latitudeTask, longitudeTask, location1Task, location2Task, rainfallTask,windSpeedTask).addOnCompleteListener(task -> {
            if (task.isSuccessful() &&


                    latitudeTask.getResult() != null && longitudeTask.getResult() != null &&
                    rainfallTask.getResult() != null  &&
                    districtTask.getResult() != null && locationTask.getResult() != null &&
                    location1Task.getResult() != null && location2Task.getResult() != null &&
                    windSpeedTask.getResult() != null ) {

                account_user_latitue = Double.parseDouble(Objects.requireNonNull(latitudeTask.getResult().getValue(String.class)));
                account_user_longitude = Double.parseDouble(Objects.requireNonNull(longitudeTask.getResult().getValue(String.class)));

                District = districtTask.getResult().getValue(String.class);
                Location1 = locationTask.getResult().getValue(String.class);

                Location1 = location1Task.getResult().getValue(String.class);
                Location2 = location2Task.getResult().getValue(String.class);
                Rainfall = String.valueOf(rainfallTask.getResult().getValue(Double.class)); // Ensure this correctly fetches the value

                WindSpeed = String.valueOf(windSpeedTask.getResult().getValue(Double.class));


                Log.d("******************************************************","MY loc  " + Location);


                Log.d("******************************************************","MY   " + Location1);
                Log.d("******************************************************","MY   " + Location2);
                Log.d("******************************************************","MY Rainfall <> " + Rainfall);
                Log.d("******************************************************","MY wind <> " + WindSpeed);



                // Data fetched, now proceed with dependent operations
                if (onDataFetchedCallback != null) {
                    onDataFetchedCallback.run();
                }
            } else {
                Log.d("FetchUserData", "Failed to fetch user data due to a task failure or incorrect database path.");
            }
        });
    }
}