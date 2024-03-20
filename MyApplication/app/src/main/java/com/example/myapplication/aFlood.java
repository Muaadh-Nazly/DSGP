package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class aFlood extends AppCompatActivity {
    LocalDate today = LocalDate.now();
    LocalDate day1 = LocalDate.now().plusDays(1);
    LocalDate day2 = LocalDate.now().plusDays(2);
    LocalDate day3 = LocalDate.now().plusDays(3);

    EditText location,location1,location2,district,rainfall;
    Button predict;
    TextView rfrToday;
    TextView xgbToday;
    TextView rfrDay1;
    TextView xgbDay1;
    TextView rfrDay2;
    TextView xgbDay2;
    TextView rfrDay3;
    TextView xgbDay3;
    String url = "https://disaster-predictor-409bdbd99295.herokuapp.com/predict_flood";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landslide);

        location = findViewById(R.id.location);
        location1 = findViewById(R.id.location1);
        location2 = findViewById(R.id.location2);
        district = findViewById(R.id.district);
        rainfall = findViewById(R.id.rainfall);
        predict = findViewById(R.id.predict);
        rfrToday = findViewById(R.id.rfrToday);
        xgbToday = findViewById(R.id.xgbToday);
        rfrDay1 = findViewById(R.id.rfrDay1);
        xgbDay1 = findViewById(R.id.xgbDay1);
        rfrDay2 = findViewById(R.id.rfrDay2);
        xgbDay2 = findViewById(R.id.xgbDay2);
        rfrDay3 = findViewById(R.id.rfrDay3);
        xgbDay3 = findViewById(R.id.xgbDay3);


        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String tdyRFR =jsonObject.getString("Prediction for "+today+" RFR");
                                    String tdyXGB =jsonObject.getString("Prediction for "+today+" XGB");
                                    String day1RFR =jsonObject.getString("Prediction for "+day1+" RFR");
                                    String day1XGB =jsonObject.getString("Prediction for "+day1+" XGB");
                                    String day2RFR =jsonObject.getString("Prediction for "+day2+" RFR");
                                    String day2XGB =jsonObject.getString("Prediction for "+day2+" XGB");
                                    String day3RFR =jsonObject.getString("Prediction for "+day3+" RFR");
                                    String day3XGB =jsonObject.getString("Prediction for "+day3+" XGB");
                                    rfrToday.setText(today.toString()+" RFR: "+tdyRFR+"%");
                                    xgbToday.setText(today.toString()+" XGB: "+tdyXGB+"%");
                                    rfrDay1.setText(day1.toString()+" RFR: "+day1RFR+"%");
                                    xgbDay1.setText(day1.toString()+" XGB: "+day1XGB+"%");
                                    rfrDay2.setText(day2.toString()+" RFR: "+day2RFR+"%");
                                    xgbDay2.setText(day2.toString()+" XGB: "+day2XGB+"%");
                                    rfrDay3.setText(day3.toString()+" RFR: "+day3RFR+"%");
                                    xgbDay3.setText(day3.toString()+" XGB: "+day3XGB+"%");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                Toast.makeText(aFlood.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("Location",location.getText().toString());
                        params.put("Location1",location1.getText().toString());
                        params.put("Location2",location2.getText().toString());
                        params.put("District",district.getText().toString());
                        params.put("Rainfall(mm)",rainfall.getText().toString());

                        return params;
                    }

                };
                RequestQueue queue = Volley.newRequestQueue(aFlood.this);
                queue.add(stringRequest);

            }
        });


    }
}