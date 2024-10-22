package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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


/**
 * Shows the Landslide Map
 */
public class LandslideActivity extends FragmentActivity implements OnMapReadyCallback {

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
    String Rainfall;
    LocalDate Today = LocalDate.now();
    LocalDate Day1 = LocalDate.now().plusDays(1);
    LocalDate Day2 = LocalDate.now().plusDays(2);
    LocalDate Day3 = LocalDate.now().plusDays(3);
    String url = "https://disaster-predictor-409bdbd99295.herokuapp.com/predict_landslide";



    public static List<String> landslide_predictions =new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landslide);

        map = findViewById(R.id.map);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);

        // Fetch user data and then initialize the map
        fetchUserData(() -> {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                Log.d("******************************************************","MY 2  " + Location1);

                mapFragment.getMapAsync(LandslideActivity.this);
            }
        });
    }

    // Showing user location in map
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        this.gMap = googleMap;
        LatLng mapSL = new LatLng(account_user_latitue, account_user_longitude);
        Marker marker = this.gMap.addMarker(new MarkerOptions().position(mapSL).title(Location));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapSL, 10)); // Adjust the zoom level

        // Set a marker on user location
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

                                    landslide_predictions.add(tdyRFR);
                                    landslide_predictions.add(tdyXGB);
                                    landslide_predictions.add(day1RFR);
                                    landslide_predictions.add(day1XGB);
                                    landslide_predictions.add(day2RFR);
                                    landslide_predictions.add(day2XGB);
                                    landslide_predictions.add(day3RFR);
                                    landslide_predictions.add(day3XGB);

                                    showBottomSheetDialog(marker);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(LandslideActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        params.put("Rainfall(mm)", Rainfall);
                        Log.d("*********",Location+" "+Location1+" "+Location2+" "+District+" "+Rainfall);

                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue( LandslideActivity.this);
                queue.add(stringRequest);
                return true;
            }
        });

    }
    // Show Bottom panel of predictions
    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog(Marker marker) {
        progressDialog.dismiss();
        // Inflate the view for the bottom sheet dialog
        View view = LayoutInflater.from(this).inflate(R.layout.custom_info_window, null);

        TextView floodRFR = view.findViewById(R.id.landslideRFR);
        TextView floodXGB = view.findViewById(R.id.landslideXGB);
        ViewGroup parentLayout = (ViewGroup) floodRFR.getParent();
        parentLayout.removeView(floodRFR);
        parentLayout.removeView(floodXGB);

        TextView cycloneRFR = view.findViewById(R.id.cycloneRFR);
        TextView cycloneXGB = view.findViewById(R.id.cycloneXGB);
        parentLayout.removeView(cycloneRFR);
        parentLayout.removeView(cycloneXGB);
        // Find and set the TextView to display the marker title
        TextView landslideTdyRFR = view.findViewById(R.id.tdyRFR);
        TextView landslideTdyXGB = view.findViewById(R.id.tdyXGB);
        landslideTdyRFR.setText("Prediction for "+Today+" RFR "+ landslide_predictions.get(0)+"%");
        landslideTdyXGB.setText("Prediction for "+Today+" XGB "+ landslide_predictions.get(1)+"%");

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

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

                day1RFR.setText("Prediction for "+Day1+" RFR "+ landslide_predictions.get(2)+"%");
                day1XGB.setText("Prediction for "+Day1+" XGB "+ landslide_predictions.get(3)+"%");
                day2RFR.setText("Prediction for "+Day2+" RFR "+ landslide_predictions.get(4)+"%");
                day2XGB.setText("Prediction for "+Day2+" XGB "+ landslide_predictions.get(5)+"%");
                day3RFR.setText("Prediction for "+Day3+" RFR "+ landslide_predictions.get(6)+"%");
                day3XGB.setText("Prediction for "+Day3+" XGB "+ landslide_predictions.get(7)+"%");
                dialog.dismiss(); // Dismiss the dialog when navigating to another activity
            }
        });

        dialog.show();
    }


    // Fetch Firebase data
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

        Tasks.whenAll(latitudeTask, longitudeTask, location1Task, location2Task, rainfallTask).addOnCompleteListener(task -> {
            if (task.isSuccessful() && latitudeTask.getResult() != null && longitudeTask.getResult() != null  && rainfallTask.getResult() != null) {

                account_user_latitue = latitudeTask.getResult().getValue(Double.class);
                account_user_longitude = longitudeTask.getResult().getValue(Double.class);
                District = districtTask.getResult().getValue(String.class);
                Location = locationTask.getResult().getValue(String.class);

                Location1 = location1Task.getResult().getValue(String.class);
                Location2 = location2Task.getResult().getValue(String.class);
                Rainfall = String.valueOf(rainfallTask.getResult().getValue(Double.class)); // Ensure this correctly fetches the value

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