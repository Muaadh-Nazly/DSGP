package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView usernameTextView;
    private TextView emailTextView;
    private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Initialize views
        profileImage = findViewById(R.id.profileImage);
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        editProfileButton = findViewById(R.id.editProfileButton);

        // Set dummy data (replace with actual user data)
        profileImage.setImageResource(R.drawable.baseline_person_24);
        usernameTextView.setText("John Doe");
        emailTextView.setText("john.doe@example.com");

        // Handle edit profile button click (if needed)
        editProfileButton.setOnClickListener(v -> {
            // Implement your logic here for editing the user profile
        });
    }
}