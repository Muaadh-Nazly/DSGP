package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
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

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.annotation.NonNull;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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




public class FloodActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    FrameLayout map;
    String Location ;
    String Location1 ;
    String Location2;
    String District;
    String Rainfall;
    String userId;


    double account_user_longitude;
    double account_user_latitue;

    LocalDate Today = LocalDate.now();
    LocalDate Day1 = LocalDate.now().plusDays(1);
    LocalDate Day2 = LocalDate.now().plusDays(2);
    LocalDate Day3 = LocalDate.now().plusDays(3);
    String url = "https://disaster-predictor-409bdbd99295.herokuapp.com/predict_flood";

    public static List<String> flood_predictions=new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood);

        fetchUserData();


        map = findViewById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        this.gMap = googleMap;
        LatLng mapSL = new LatLng(account_user_latitue, account_user_longitude);
        Marker marker = this.gMap.addMarker(new MarkerOptions().position(mapSL).title("Marker in Kirindiwela"));
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
                                    String tdyRFR = jsonObject.getString("Prediction for " + Today + " RFR");
                                    String tdyXGB = jsonObject.getString("Prediction for " + Today + " XGB");
                                    String day1RFR = jsonObject.getString("Prediction for " + Day1 + " RFR");
                                    String day1XGB = jsonObject.getString("Prediction for " + Day1 + " XGB");
                                    String day2RFR = jsonObject.getString("Prediction for " + Day2 + " RFR");
                                    String day2XGB = jsonObject.getString("Prediction for " + Day2 + " XGB");
                                    String day3RFR = jsonObject.getString("Prediction for " + Day3 + " RFR");
                                    String day3XGB = jsonObject.getString("Prediction for " + Day3 + " XGB");

                                    flood_predictions.add(tdyRFR);
                                    flood_predictions.add(tdyXGB);
                                    flood_predictions.add(day1RFR);
                                    flood_predictions.add(day1XGB);
                                    flood_predictions.add(day2RFR);
                                    flood_predictions.add(day2XGB);
                                    flood_predictions.add(day3RFR);
                                    flood_predictions.add(day3XGB);

                                    showBottomSheetDialog(marker);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FloodActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        if (Location != null && Location1 != null && Location2 != null && District != null && Rainfall != null) {
                            params.put("Location", Location);
                            params.put("Location1", Location1);
                            params.put("Location2", Location2);
                            params.put("District", District);
                            params.put("Rainfall(mm)", Rainfall);
                        } else {

                            Log.d("******************************************","THE  VALUS ARE NULL");

                        }

                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(FloodActivity.this);
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

        TextView landslideRFR = view.findViewById(R.id.landslideRFR);
        TextView landslideXGB = view.findViewById(R.id.landslideXGB);
        ViewGroup parentLayout = (ViewGroup) landslideRFR.getParent();
        parentLayout.removeView(landslideRFR);
        parentLayout.removeView(landslideXGB);

        TextView cycloneRFR = view.findViewById(R.id.cycloneRFR);
        TextView cycloneXGB = view.findViewById(R.id.cycloneXGB);
        parentLayout.removeView(cycloneRFR);
        parentLayout.removeView(cycloneXGB);

        // Find and set the TextView to display the marker title
        TextView floodTdyRFR = view.findViewById(R.id.tdyRFR);
        TextView floodTdyXGB = view.findViewById(R.id.tdyXGB);

        floodTdyRFR.setText("Prediction for "+Today+" RFR "+flood_predictions.get(0)+"%");
        floodTdyXGB.setText("Prediction for "+Today+" XGB "+flood_predictions.get(1)+"%");

        // Create and show the bottom sheet dialog
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);


        // Handle button click
        Button moreDetailsButton = view.findViewById(R.id.moreDetailsButton);
        moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start another activity to load another XML page
                setContentView(R.layout.activity_details);
                TextView day1RFR = findViewById(R.id.day1RFR);
                TextView day1XGB = findViewById(R.id.day1XGB);
                TextView day2RFR = findViewById(R.id.day2RFR);
                TextView day2XGB = findViewById(R.id.day2XGB);
                TextView day3RFR = findViewById(R.id.day3RFR);
                TextView day3XGB = findViewById(R.id.day3XGB);

                day1RFR.setText("Prediction for "+Day1+" RFR "+flood_predictions.get(2)+"%");
                day1XGB.setText("Prediction for "+Day1+" XGB "+flood_predictions.get(3)+"%");
                day2RFR.setText("Prediction for "+Day2+" RFR "+flood_predictions.get(4)+"%");
                day2XGB.setText("Prediction for "+Day2+" XGB "+flood_predictions.get(5)+"%");
                day3RFR.setText("Prediction for "+Day3+" RFR "+flood_predictions.get(6)+"%");
                day3XGB.setText("Prediction for "+Day3+" XGB "+flood_predictions.get(7)+"%");

                dialog.dismiss(); // Dismiss the dialog when navigating to another activity
            }
        });
        dialog.show();
    }




    public void fetchUserData() {


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();


        DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1-serctivity-a5951.asia-southeast1.firebasedatabase.app/").getReference().child(userId);
        DatabaseReference database3 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-4532a.firebaseio.com/").getReference().child(userId);

        Task<DataSnapshot> latitudeTask = database2.child("Latitude").get();
        Task<DataSnapshot> longitudeTask = database2.child("Longitude").get();
        Task<DataSnapshot> location1Task = database3.child("Near By City 1").get();
        Task<DataSnapshot> location2Task = database3.child("Near By City 2").get();
        Task<DataSnapshot> rainfallTask = database3.child("City").child("0").get();

        Tasks.whenAll(latitudeTask, longitudeTask, location1Task, location2Task, rainfallTask).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d("FetchUserData", "Failed to fetch user data due to a task failure.");
                return;
            }

            try {

                account_user_latitue = latitudeTask.getResult().getValue(Double.class);
                account_user_longitude = longitudeTask.getResult().getValue(Double.class);
                Location1 = location1Task.getResult().getValue(String.class);
                Location2 = location2Task.getResult().getValue(String.class);
                Rainfall = rainfallTask.getResult().getValue(String.class);
                Log.d("*************************************************","Longitude " + account_user_longitude);


            } catch (Exception e) {
                Log.d("***********************************", " Fetch user data An error occurred while fetching data: " + e.getMessage());
            }
        });

        Log.d("*************************************************","Fetch user data inside");



    }


    private void fetchFloodPredictions(Marker marker) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // Extract predictions and add them to the list
                            // ...

                            // After successful data fetching, show the dialog
                            showBottomSheetDialog(marker);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FloodActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                if (Location != null && Location1 != null && Location2 != null && District != null && Rainfall != null) {
                    params.put("Location", Location);
                    params.put("Location1", Location1);
                    params.put("Location2", Location2);
                    params.put("District", District);
                    params.put("Rainfall(mm)", Rainfall);
                } else {
                    Log.d("FetchFloodPredictions", "One or more parameters are null.");
                }
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(FloodActivity.this);
        queue.add(stringRequest);
    }














}