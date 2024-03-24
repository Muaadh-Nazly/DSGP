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
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiresApi(api = Build.VERSION_CODES.O)
public class ReportActivity extends FragmentActivity implements OnMapReadyCallback {
    String Location = "Balangoda";
    String Location1 = "Balangoda";
    String Location2 = "Balangoda";
    String District = "Ratnapura";
    String Rainfall = "6.7";
    String WindSpeed = "0.9";
    LocalDate Today = LocalDate.now();
    GoogleMap gMap;
    FrameLayout map;
    String floodURL = "https://disaster-predictor-409bdbd99295.herokuapp.com/predict_flood";
    String landslideURL = "https://disaster-predictor-409bdbd99295.herokuapp.com/predict_landslide";
    String cycloneURL = "https://disaster-predictor-409bdbd99295.herokuapp.com/predict_cyclone";
    public static List<String> predictions = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        map = findViewById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
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
                StringRequest stringRequest = new StringRequest(Request.Method.POST, floodURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String floodRFR = jsonObject.getString("Prediction for " + Today + " RFR");
                                    String floodXGB = jsonObject.getString("Prediction for " + Today + " XGB");
                                    String landslideRFR = jsonObject.getString("Prediction for " + Today + " RFR");
                                    String landslideXGB = jsonObject.getString("Prediction for " + Today + " XGB");
                                    String cycloneRFR = jsonObject.getString("Prediction for " + Today + " RFR");
                                    String cycloneXGB = jsonObject.getString("Prediction for " + Today + " XGB");

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
                        params.put("Location", Location);
                        params.put("Location1", Location1);
                        params.put("Location2", Location2);
                        params.put("District", District);
                        params.put("Rainfall(mm)", Rainfall);
                        params.put("Wind Speed(mph)", WindSpeed);

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
        for(String pred:predictions){
            Log.d("************",pred);
        }
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
        for(String pred:predictions){
            Log.d("**********",pred);
        }

        floodRFR.setText("Prediction for " + Today + " Flood RFR " + predictions.get(0)+"%");
        floodXGB.setText("Prediction for " + Today + " Flood XGB " + predictions.get(1)+"%");
        landslideRFR.setText("Prediction for " + Today + " Landslide RFR " + predictions.get(2)+"%");
        landslideXGB.setText("Prediction for " + Today + " Landslide XGB " + predictions.get(3)+"%");
        cycloneRFR.setText("Prediction for " + Today + " Cyclone RFR " + predictions.get(4)+"%");
        cycloneXGB.setText("Prediction for " + Today + " Cyclone XGB " + predictions.get(5)+"%");

        // Create and show the bottom sheet dialog
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        // Handle button click


        dialog.show();
    }
}