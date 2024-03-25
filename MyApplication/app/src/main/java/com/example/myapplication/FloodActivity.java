package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FloodActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    FrameLayout map;
    String Location = "Balangoda";
    String Location1 = "Balangoda";
    String Location2 = "Balangoda";
    String District = "Ratnapura";
    String Rainfall = "6.7";
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
        LatLng mapSL = new LatLng(7.0412, 80.1289);
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
                        params.put("Location", Location);
                        params.put("Location1", Location1);
                        params.put("Location2", Location2);
                        params.put("District", District);
                        params.put("Rainfall(mm)", Rainfall);

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



}