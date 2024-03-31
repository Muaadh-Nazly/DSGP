package com.example.myapplication;

import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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


    public void FloodActivityDatabaseReader(){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        userId = currentUser.getUid();


        /*
         * Location displaying
         */

        DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1-serctivity-a5951.asia-southeast1.firebasedatabase.app/").getReference().child(userId);

        database2.child("Longitude").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    account_user_longitude = (double) dataSnapshot.getValue(); // Assuming it's a string
                    System.out.println("*******************************longitude: " + account_user_longitude);
                } else {
                    System.out.println("*********************************longitude data not found");
                }
            } else {
                System.out.println("******************************************Failed to read Rainfall data");
            }
        });

        database2.child("Latitude").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    account_user_latitue = (double) dataSnapshot.getValue(); // Assuming it's a string
                    System.out.println("*******************************latitude: " + account_user_latitue);


                    // Due to asynchronize have to add here
                    LatLng mapSL = new LatLng(account_user_latitue, account_user_longitude);
                    Marker marker = this.gMap.addMarker(new MarkerOptions().position(mapSL).title("Marker in " + account_user_latitue));
                    this.gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapSL, 10));



                }

                else {
                    System.out.println("*********************************latitude data not found");
                }
            } else {
                System.out.println("******************************************Failed to read Rainfall data");
            }
        });


        database2.child("City").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    account_user_city = (String) dataSnapshot.getValue(); // Assuming it's a string
                    System.out.println("*******************************User city: " + account_user_city);
                } else {
                    System.out.println("*********************************longitude data not found");
                }
            } else {
                System.out.println("******************************************Failed to read longitude data");
            }
        });


        /*
         *  Getting real value
         */

        DatabaseReference database3 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-4532a.firebaseio.com/").getReference().child(userId);


//        Location = "Balangoda";
//         Location1 = "Balangoda";
//         Location2 = "Balangoda";
//         District = "Ratnapura";
//         Rainfall = "6.7";


        database3.child("Near By City 1").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    Location1 = (String) dataSnapshot.getValue(); // Assuming it's a string
                    System.out.println("*******************************longitude: " + Location1);
                } else {
                    System.out.println("*********************************longitude data not found");
                }
            } else {
                System.out.println("******************************************Failed to read Rainfall data");
            }
        });


        database3.child("Near By City 2").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    Location2 = (String) dataSnapshot.getValue(); // Assuming it's a string
                    System.out.println("*******************************longitude: " + Location2);
                } else {
                    System.out.println("*********************************longitude data not found");
                }
            } else {
                System.out.println("******************************************Failed to read Rainfall data");
            }
        });


        database3.child(userId).child("Rainfall data").child("0").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        if (dataSnapshot.exists()) {
                            Rainfall = dataSnapshot.getValue(String.class);
                            System.out.println("Rainfall data: " + Rainfall);

                        } else {
                            System.out.println("Rainfall data not found for user: " + userId);
                        }
                    } else {
                        System.out.println("Failed to read rainfall data for user: " + userId);
                    }

                });


        FloodActivityDatabaseReaderArrayList.add(Location);
        FloodActivityDatabaseReaderArrayList.add(Location1);
        FloodActivityDatabaseReaderArrayList.add(Location2);
        FloodActivityDatabaseReaderArrayList.add(Rainfall);

        Log.d("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", String.valueOf(FloodActivityDatabaseReaderArrayList));

        ArrayList<String> test = new ArrayList<>();
    }


}
