//package com.example.myapplication;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import java.util.ArrayList;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DatabaseReference;
//
//import java.util.ArrayList;
//
//public class SelectedLocation extends AppCompatActivity {
//
//    private DatabaseReference mDatabase;
//
//    String provinces;
//    String[][] item2 = {
//            {"Colombo","Kalutara","Gampaha"},
//            {},
//            {}
//
//
//    };
//
//    AutoCompleteTextView autoCompleteTextView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_selected_location);
//
//
//        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
////        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, );
//
//
//        Log.d("**********************","function over");
//
//
//
//
//
//        autoCompleteTextView.setAdapter(adapterItems);
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = parent.getItemAtPosition(position).toString();
//                Toast.makeText(SelectedLocation.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    private void getData() {
//        // Initialize the database reference from a specific Firebase database URL
//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-af750.firebaseio.com/");
//        DatabaseReference myRef = database.getReference("Provinces"); // This gets the root of your database
//        Log.d("**********************************","inside the getData function mmmm");
//        // Assuming you want to fetch data from the root. If you need to fetch from a specific path, use .child("path")
//        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//
//
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//
//                Log.d("**********************************","????");
//
//                if (!task.isSuccessful()) {
//                    Log.e("******************************************firebase", "Error getting data", task.getException());
//                    Log.d("**********************************","not");
//
//                }
//                else {
//                    // Assuming the value at the root is a simple object or a string
//                    // For complex objects or lists, you would handle them differently
//                    provinces = String.valueOf(task.getResult().getValue());
//                    Log.d("*******************************",provinces);
//
//                    // Split the string by comma and trim each value
//                    String[] provincesArray = provinces.split(",");
//                    for (int i = 0; i < provincesArray.length; i++) {
//                        provincesArray[i] = provincesArray[i].trim();
//                    }
//
//                    // Update the adapter with the new data
//                    ArrayAdapter<String> adapterItems = new ArrayAdapter<>(SelectedLocation.this, android.R.layout.simple_list_item_1, provincesArray);
//                    autoCompleteTextView.setAdapter(adapterItems);
//
//
//
//                }
//            }
//
//
//        });
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//}


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
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2, autoCompleteTextView3;
    Button userSelectedSetLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selected_location);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);
        autoCompleteTextView3 = findViewById(R.id.autoCompleteTextView3);
        userSelectedSetLocationButton = findViewById(R.id.userSelectedSetLocationButton);

        getProvincesData();


        userSelectedSetLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        //                       provincesArray[i] = provincesArray[i].trim();
                        provincesArray[i] = provincesArray[i].trim().replaceAll("=", "");

                    }
                    Log.d("***************************provinceArray", Arrays.toString(provincesArray));

                    // Update the adapter with the new data
                    ArrayAdapter<String> adapterItems = new ArrayAdapter<>(SelectedLocation.this, android.R.layout.simple_list_item_1, provincesArray);
                    autoCompleteTextView.setAdapter(adapterItems);


                    autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = parent.getItemAtPosition(position).toString();
                            Toast.makeText(SelectedLocation.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                            getDistrictData(selectedItem);
                        }
                    });
                }
            }
        });
    }

    public void getDistrictData(String selectedItem) {
        Log.d("***************************** selected Item", selectedItem);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-af750.firebaseio.com/");
        DatabaseReference districtsRef = database.getReference("Districts");
        DatabaseReference westernRef = districtsRef.child(selectedItem);


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
                        districsArray[i] = districsArray[i].trim().trim().replaceAll("=", "");

                    }

                    Log.d("****************", Arrays.toString(districsArray));
                    // Update the adapter with the new data
                    ArrayAdapter<String> adapterItems = new ArrayAdapter<>(SelectedLocation.this, android.R.layout.simple_list_item_1, districsArray);
                    autoCompleteTextView2.setAdapter(adapterItems);

                    autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = parent.getItemAtPosition(position).toString();
                            Toast.makeText(SelectedLocation.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    public void UserActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }
    

}

