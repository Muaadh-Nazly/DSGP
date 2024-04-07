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

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Arrays;


/**
 * User selecting its own location to see disaster predictions in that selected region
 */
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

    String disasterType;



    String user_selected_latitue;
    String user_selected_longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selected_location);

        Log.d("******************************", "HEEEEEEEEEEEEEEEEEE");



        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);
        autoCompleteTextView3 = findViewById(R.id.autoCompleteTextView3);
        userSelectedSetLocationButton = findViewById(R.id.userSelectedSetLocationButton);


        Intent intent = getIntent();
        disasterType = intent.getStringExtra("DISASTER");
        Log.d("*********************************","DISASTER TYPE IN SELECTED LOCATION" + disasterType);


        Log.d("******************************", "hello2 selected location");

        getProvincesData();


        userSelectedSetLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelectedLocationRead(() -> {

                    Log.d("***************************************************", "Data fetched successfully: Latitude: " + user_selected_latitue + ", Longitude: " + user_selected_longitude);
                    userSelectedLocationWrite();
                    LocationDetails2();

                });
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


    public void userSelectedLocationRead(final Runnable onDataFetchedCallback) {

        Log.d("**********************","Did I start");


        DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-19dc1.firebaseio.com/").getReference().child("Cities").child(selected_district).child(selected_cities);

        Task<DataSnapshot> latitudeTask = database2.child("Latitude").get();
        Task<DataSnapshot> longitudeTask = database2.child("Longitude").get();

        Tasks.whenAll(latitudeTask, longitudeTask).addOnCompleteListener(task -> {
            if (task.isSuccessful() && latitudeTask.getResult() != null && longitudeTask.getResult() != null) {
                user_selected_latitue = latitudeTask.getResult().getValue(String.class);
                user_selected_longitude = longitudeTask.getResult().getValue(String.class);

                Log.d("**********************************", "The mf latitude " + user_selected_latitue);
                Log.d("**********************************", "The mf longitude " + user_selected_longitude);



                // Important: Trigger callback here to ensure the following code executes after data is fetched
                if (onDataFetchedCallback != null) {
                    onDataFetchedCallback.run();
                }
            } else {
                Log.d("**************************************", "Failed to fetch user data due to a task failure or incorrect database path.");
                // Consider calling onDataFetchedCallback here as well, with an error status or simply handling the error as needed
            }
        });

        Log.d("**********************","Did I finish");


    }



    public void userSelectedLocationWrite(){


        Log.d("*************************************************", "userSelectedLocationWrite");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();


        DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-4532a.firebaseio.com/").getReference().child(userId);
        database2.child("City").setValue(selected_cities);
        database2.child("District").setValue(selected_district);
        database2.child("Province").setValue(selected_provinces);

        database2.child("Latitude").setValue(user_selected_latitue);
        database2.child("Longitude").setValue(user_selected_longitude);

        Log.d("**********************************", "Selected Latitude " +  user_selected_latitue);
        Log.d("**********************************", "Selected Longitude "+ user_selected_longitude);
        Log.d("**********************************", "Selected City " + selected_cities);
        Log.d("**********************************", "Selected District " + selected_district);


    }

    public void LocationDetails2() {

        Log.d("********************************", "INSIDE DEATILS 2 PASSING" + user_selected_latitue);
        Log.d("********************************", "INSIDE DEATILS 2 PASSING" + user_selected_longitude);


        Intent intent = new Intent(this, LocationDetails2.class);
        intent.putExtra("USER_SELECTED_LATITUDE", String.valueOf(user_selected_latitue));
        intent.putExtra("USER_SELECTED_LONGITUDE", String.valueOf(user_selected_longitude));
        intent.putExtra("USER_SELECTED_CITY", String.valueOf(selected_cities));
        intent.putExtra("USER_SELECTED_DISTRICT", String.valueOf(selected_district));


        intent.putExtra("DISASTER", String.valueOf(disasterType));


        startActivity(intent);
        finish();

    }

}
