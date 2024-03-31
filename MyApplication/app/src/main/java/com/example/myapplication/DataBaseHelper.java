package com.example.myapplication;

import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class DataBaseHelper {


    double account_user_latitue;
    double account_user_longitude;
    String account_user_city;
    private String userId;



    GoogleMap gMap;
    FrameLayout map;

    String Location = "Maharagama";
    String Location1;
    String Location2;
    String District;
    String Rainfall;


    ArrayList<String> FloodActivityDatabaseReaderArrayList;


//    public ArrayList<String> FloodActivityDatabaseReader(){



    public void fetchUserData() {
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

}
