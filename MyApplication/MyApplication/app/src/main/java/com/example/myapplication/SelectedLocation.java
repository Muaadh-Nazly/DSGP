package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Arrays;

public class SelectedLocation extends AppCompatActivity {

    private DatabaseReference mDatabase;
    String provinces;
    String districs;
    String cities;


    String selected_provinces;
    String selected_district;
    String selected_cities;


    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2, autoCompleteTextView3;
    Button userSelectedSetLocationButton;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selected_location);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);
        autoCompleteTextView3 = findViewById(R.id.autoCompleteTextView3);
        userSelectedSetLocationButton = findViewById(R.id.userSelectedSetLocationButton);

        Log.d("******************************","HEEEEEEEEEEEEEEEEEE");

        getProvincesData();


        userSelectedSetLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("************************************ Sleceted Location ","***" + selected_provinces + selected_district + selected_cities );
                UserActivity();
            }
        });

    }


    private void getProvincesData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-af750.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Provinces");

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Firebase", "Error getting data", task.getException());
                } else {
                    provinces = String.valueOf(task.getResult().getValue());
//                    Log.d("****************************Provinces", provinces);
//                    Log.d("***********************Ref", String.valueOf(myRef));

                    // Split the string by comma and trim each value
                    String[] provincesArray = provinces.split(",");
                    for (int i = 0; i < provincesArray.length; i++) {
                        provincesArray[i] = provincesArray[i].trim().replace("{", "").replace("}", "").replace("=", "");
                    }
                    Log.d("provinceArray", Arrays.toString(provincesArray));


                    // Update the adapter with the new data
                    ArrayAdapter<String> adapterItems = new ArrayAdapter<>(SelectedLocation.this, android.R.layout.simple_list_item_1, provincesArray);
                    autoCompleteTextView.setAdapter(adapterItems);


                    autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selected_provinces = parent.getItemAtPosition(position).toString();
                            Toast.makeText(SelectedLocation.this, "Selected: " + selected_provinces, Toast.LENGTH_SHORT).show();
                            getDistrictData(selected_provinces);
                        }
                    });
                }
            }
        });
    }

    public void getDistrictData(String selected_provinces) {
        Log.d("***************************** selected Item", selected_provinces);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-af750.firebaseio.com/");
        DatabaseReference districtsRef = database.getReference("Districts");
        DatabaseReference westernRef = districtsRef.child(selected_provinces);


        westernRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Firebase", "Error getting data", task.getException());
                } else {
                    districs = String.valueOf(task.getResult().getValue());
                    Log.d("***************************************Districts", districs);
                    Log.d("***********************Ref", String.valueOf(westernRef));

                    // Split the string by comma and trim each value
                    String[] districsArray = districs.split(",");
                    for (int i = 0; i < districsArray.length; i++) {
                        districsArray[i] = districsArray[i].trim().replace("{", "").replace("}", "").replace("=", "");

                    }

                    Log.d("****************", Arrays.toString(districsArray));
                    // Update the adapter with the new data
                    ArrayAdapter<String> adapterItems = new ArrayAdapter<>(SelectedLocation.this, android.R.layout.simple_list_item_1, districsArray);
                    autoCompleteTextView2.setAdapter(adapterItems);

                    autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selected_district = parent.getItemAtPosition(position).toString();
                            Toast.makeText(SelectedLocation.this, "Selected: " + selected_district, Toast.LENGTH_SHORT).show();
                            getCityData(selected_district);

                        }
                    });
                }
            }
        });

    }


    public void getCityData(String selected_district){

        Log.d("***************************** selected Item", selected_district);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-af750.firebaseio.com/");
        DatabaseReference districtsRef = database.getReference("Cities");
        DatabaseReference westernRef = districtsRef.child(selected_district);


        westernRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Firebase", "Error getting data", task.getException());
                }

                else {
                    cities = String.valueOf(task.getResult().getValue());
                    Log.d("***************************************Cities", cities);
                    Log.d("***********************Ref", String.valueOf(westernRef));

                    // Split the string by comma and trim each value
                    String[] citiesArray = cities.split(",");
                    for (int i = 0; i < citiesArray.length; i++) {
                        citiesArray[i] = citiesArray[i].trim().replace("{", "").replace("}", "").replace("=", "");

                    }

                    Log.d("***********************************************", Arrays.toString(citiesArray));

                    Log.d("****************", Arrays.toString(citiesArray));
                    // Update the adapter with the new data
                    ArrayAdapter<String> adapterItems = new ArrayAdapter<>(SelectedLocation.this, android.R.layout.simple_list_item_1, citiesArray);
                    autoCompleteTextView3.setAdapter(adapterItems);

                    autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selected_cities = parent.getItemAtPosition(position).toString();
                            Toast.makeText(SelectedLocation.this, "Selected: " + selected_cities, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


















    public void UserActivity() {

        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("SELECTED_PROVINCE", selected_provinces);
        intent.putExtra("SELECTED_DISTRICT", selected_district);
        intent.putExtra("SELECTED_CITY", selected_cities);
        startActivity(intent);
        finish();

    }








}

