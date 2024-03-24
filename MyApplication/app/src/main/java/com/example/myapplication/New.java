package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class New {

    String userId;
    List<String> windSpeedValues = new ArrayList<>();
    List<String> rainfallData = new ArrayList<>();// List to store all rainfall values

    public void user_details(){


        Log.d("*******************************","INSIDE NEW CLASS");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();


        Log.d("*******************************",userId);



        FirebaseDatabase database = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-4532a.firebaseio.com/");
        DatabaseReference userRef = database.getReference(userId);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    // Successfully retrieved the data, now get the values
                    DataSnapshot dataSnapshot = task.getResult();
                    String nearByCity1 = dataSnapshot.child("Near By City 1").getValue(String.class);
                    String nearByCity2 = dataSnapshot.child("Near By City 2").getValue(String.class);

                    Log.d("********************************", "NearByCity1: " + nearByCity1);
                    Log.d("********************************", "NearByCity2: " + nearByCity2);


                } else {
                    // Failed to retrieve the data
                    Log.e("*********************************************", "Error getting user data", task.getException());
                }
            }
        });


        // Initialize or declare your array/list outside the method if you plan to collect values

        DatabaseReference windSpeedDataRef = database.getReference(userId).child("WindSpeed data");
        DatabaseReference rainfallDataRef = database.getReference(userId).child("Rainfall data");


        windSpeedDataRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("********************************", "SUCCESSFUL IN NEW");

                    // Successfully retrieved the data, directly get the value
                    DataSnapshot dataSnapshot = task.getResult();
                    // Directly convert the single value to a string, regardless of its original type
                    String value = String.valueOf(dataSnapshot.getValue());
                    // If you want to add it to the array
                    windSpeedValues.add(value);
                    Log.d("********************************", "WindSpeed value: " + value);
                } else {
                    // Failed to retrieve the data
                    Log.e("*******************************", "Error getting WindSpeed Data", task.getException());
                }
            }
        });


        rainfallDataRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("********************************", "SUCCESSFUL IN NEW");

                    // Successfully retrieved the data, directly get the value
                    DataSnapshot dataSnapshot = task.getResult();
                    // Directly convert the single value to a string, regardless of its original type
                    String value = String.valueOf(dataSnapshot.getValue());
                    // If you want to add it to the array
                    rainfallData.add(value);
                    Log.d("********************************", "Rainfall value: " + value);
                } else {
                    // Failed to retrieve the data
                    Log.e("*******************************", "Error getting WindSpeed Data", task.getException());
                }
            }
        });

        Log.d("********************************", "finsihed NEW CLASS" + windSpeedValues );
        Log.d("********************************", "finsihed NEW CLASS" + rainfallData );


    }
}