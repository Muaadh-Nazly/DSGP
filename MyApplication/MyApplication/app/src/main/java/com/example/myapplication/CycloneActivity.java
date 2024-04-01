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

public class CycloneActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    FrameLayout map;


    double account_user_latitue;
    double account_user_longitude;
    String account_user_city;
    private String userId;



    String Location;
    String Location1;
    String Location2;
    String District;
    String WindSpeed;
    String WindSpeed1;
    String WindSpeed2;
    String WindSpeed3;


    String Rainfall;




    LocalDate Today = LocalDate.now();
    LocalDate Day1 = LocalDate.now().plusDays(1);
    LocalDate Day2 = LocalDate.now().plusDays(2);
    LocalDate Day3 = LocalDate.now().plusDays(3);
    String url = "https://disaster-predictor-409bdbd99295.herokuapp.com/predict_cyclone";



    public static List<String> cyclone_predictions =new ArrayList<>();


    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclone);

        map = findViewById(R.id.map);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);

        // Fetch user data and then initialize the map
        fetchUserData(() -> {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                Log.d("******************************************************","MY 2  " + Location1);

                mapFragment.getMapAsync(CycloneActivity.this);
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

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
                                    String tdyRFR = jsonObject.getString("Prediction for " + Today + " RFR");
                                    String tdyXGB = jsonObject.getString("Prediction for " + Today + " XGB");
                                    String day1RFR = jsonObject.getString("Prediction for " + Day1 + " RFR");
                                    String day1XGB = jsonObject.getString("Prediction for " + Day1 + " XGB");
                                    String day2RFR = jsonObject.getString("Prediction for " + Day2 + " RFR");
                                    String day2XGB = jsonObject.getString("Prediction for " + Day2 + " XGB");
                                    String day3RFR = jsonObject.getString("Prediction for " + Day3 + " RFR");
                                    String day3XGB = jsonObject.getString("Prediction for " + Day3 + " XGB");

                                    cyclone_predictions.add(tdyRFR);
                                    cyclone_predictions.add(tdyXGB);
                                    cyclone_predictions.add(day1RFR);
                                    cyclone_predictions.add(day1XGB);
                                    cyclone_predictions.add(day2RFR);
                                    cyclone_predictions.add(day2XGB);
                                    cyclone_predictions.add(day3RFR);
                                    cyclone_predictions.add(day3XGB);

                                    showBottomSheetDialog(marker);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(CycloneActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        Log.d("***********4",Location+" "+Location1+" "+Location2);
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
                        params.put("Wind Speed(mph)", WindSpeed);
                        params.put("Wind Speed(mph)1", WindSpeed1);
                        params.put("Wind Speed(mph)2", WindSpeed2);
                        params.put("Wind Speed(mph)3", WindSpeed3);
                        Log.d("*********",Location+" "+Location1+" "+Location2+" "+District+" "+WindSpeed+" "+WindSpeed1+" "+WindSpeed2+" "+WindSpeed3);

                        return params;
                    }

                };

                RequestQueue queue = Volley.newRequestQueue(CycloneActivity.this);
                queue.add(stringRequest);

                return true;
            }

            ;
        });
    }

    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog(Marker marker) {
        progressDialog.dismiss();


        // Inflate the view for the bottom sheet dialog
        View view = LayoutInflater.from(this).inflate(R.layout.custom_info_window, null);

        TextView floodRFR = view.findViewById(R.id.cycloneRFR);
        TextView floodXGB = view.findViewById(R.id.cycloneXGB);
        ViewGroup parentLayout = (ViewGroup) floodRFR.getParent();
        parentLayout.removeView(floodRFR);
        parentLayout.removeView(floodXGB);

        // Find and set the TextView to display the marker title
        TextView landslideTdyRFR = view.findViewById(R.id.landslideRFR);
        TextView landslideTdyXGB = view.findViewById(R.id.landslideXGB);
        parentLayout.removeView(landslideTdyRFR);
        parentLayout.removeView(landslideTdyXGB);

        // Find and set the TextView to display the marker title
        TextView cycloneTdyRFR = view.findViewById(R.id.tdyRFR);
        TextView cycloneTdyXGB = view.findViewById(R.id.tdyXGB);
        cycloneTdyRFR.setText("Prediction for "+Today+" RFR "+ cyclone_predictions.get(0)+"%");
        cycloneTdyXGB.setText("Prediction for "+Today+" XGB "+ cyclone_predictions.get(1)+"%");



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

                day1RFR.setText("Prediction for "+Day1+" RFR "+ cyclone_predictions.get(2)+"%");
                day1XGB.setText("Prediction for "+Day1+" XGB "+ cyclone_predictions.get(3)+"%");
                day2RFR.setText("Prediction for "+Day2+" RFR "+ cyclone_predictions.get(4)+"%");
                day2XGB.setText("Prediction for "+Day2+" XGB "+ cyclone_predictions.get(5)+"%");
                day3RFR.setText("Prediction for "+Day3+" RFR "+ cyclone_predictions.get(6)+"%");
                day3XGB.setText("Prediction for "+Day3+" XGB "+ cyclone_predictions.get(7)+"%");
                dialog.dismiss(); // Dismiss the dialog when navigating to another activity
            }
        });

        dialog.show();
    }



    public void fetchUserData(final Runnable onDataFetchedCallback) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();


        DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1-serctivity-a5951.asia-southeast1.firebasedatabase.app/").getReference().child(userId);
        DatabaseReference database3 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-4532a.firebaseio.com/").getReference().child(userId);

        Task<DataSnapshot> latitudeTask = database2.child("Latitude").get();
        Task<DataSnapshot> longitudeTask = database2.child("Longitude").get();
        Task<DataSnapshot> locationTask = database2.child("City").get();
        Task<DataSnapshot> districtTask = database2.child("District").get();

        Task<DataSnapshot> location1Task = database3.child("Near By City 1").get();
        Task<DataSnapshot> location2Task = database3.child("Near By City 2").get();
        Task<DataSnapshot> rainfallTask = database3.child("Rainfall data").child("0").get(); // Make sure this path is correct

        Task<DataSnapshot> windSpeedTask = database3.child("WindSpeed data").child("0").get(); // Make sure this path is correct
        Task<DataSnapshot> windSpeedTask1 = database3.child("WindSpeed data").child("1").get();
        Task<DataSnapshot> windSpeedTask2 = database3.child("WindSpeed data").child("2").get(); // Make sure this path is correct
        Task<DataSnapshot> windSpeedTask3 = database3.child("WindSpeed data").child("3").get(); // Make sure this path is correct



        Tasks.whenAll(latitudeTask, longitudeTask, location1Task, location2Task, rainfallTask,windSpeedTask,windSpeedTask1,windSpeedTask2,windSpeedTask3).addOnCompleteListener(task -> {
            if (task.isSuccessful() && latitudeTask.getResult() != null && longitudeTask.getResult() != null  && rainfallTask.getResult() != null) {

                account_user_latitue = latitudeTask.getResult().getValue(Double.class);
                account_user_longitude = longitudeTask.getResult().getValue(Double.class);
                District = districtTask.getResult().getValue(String.class);
                Location = locationTask.getResult().getValue(String.class);

                Location1 = location1Task.getResult().getValue(String.class);
                Location2 = location2Task.getResult().getValue(String.class);
                Rainfall = String.valueOf(rainfallTask.getResult().getValue(Double.class)); // Ensure this correctly fetches the value

                WindSpeed = String.valueOf(windSpeedTask.getResult().getValue(Double.class));
                WindSpeed1 = String.valueOf(windSpeedTask1.getResult().getValue(Double.class));
                WindSpeed2 = String.valueOf(windSpeedTask2.getResult().getValue(Double.class));
                WindSpeed3 = String.valueOf(windSpeedTask3.getResult().getValue(Double.class));


                Log.d("******************************************************","MY loc  " + Location);


                Log.d("******************************************************","MY   " + Location1);
                Log.d("******************************************************","MY   " + Location2);
                Log.d("******************************************************","MY Rainfall <> " + Rainfall);
                Log.d("******************************************************","MY wind <> " + WindSpeed);
                Log.d("******************************************************","MY wind1 <> " + WindSpeed1);
                Log.d("******************************************************","MY wind2 <> " + WindSpeed2);
                Log.d("******************************************************","MY wind3 <> " + WindSpeed3);



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

