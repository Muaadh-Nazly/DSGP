package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserSettings extends AppCompatActivity {

    private ImageView profileImage;
    private TextView usernameTextView;
    private TextView emailTextView;
    private Button logoutButton;

    private static final int REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        profileImage = findViewById(R.id.profileImage);
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        logoutButton = findViewById(R.id.logoutButton);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String user_name = user.getDisplayName();
        String User_email = user.getEmail();

        Log.d("******************************************","name" + user_name);

        // Set dummy data (replace with actual user data)
        profileImage.setImageResource(R.drawable.baseline_person_24);
        usernameTextView.setText(user_name);
        emailTextView.setText(User_email);

        // Add OnClickListener to emergencyTextView
        TextView emergencyTextView = findViewById(R.id.emergencyTextView);





        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity();
            }
        });



        emergencyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if permission to call phone is granted
                if (ActivityCompat.checkSelfPermission(UserSettings.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(UserSettings.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                } else {
                    // Permission is granted, call the emergency number
                    callEmergencyNumber();
                }
            }
        });
    }

    // Method to call the emergency number
    private void callEmergencyNumber() {
        String emergencyNumber = "tel:119";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(emergencyNumber));

        try {
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to initiate call. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, call the emergency number
                callEmergencyNumber();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied to call phone. Please grant the permission.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void RegisterActivity(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }


}