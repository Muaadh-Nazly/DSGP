package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

       FirebaseAuth.getInstance().signOut(); // Sign out the user

        if (user == null) {

            setContentView(R.layout.activity_get_started);
            Button getStartedButton = findViewById(R.id.get_started);

            // User is not signed in, set up the Get Started button click listener
            getStartedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to the RegisterActivity activity when Get Started is clicked
                    RegisterActivity();
                }

            });


        } else {
            // User is signed in, directly navigate to UserActivity
//            FirebaseAuth.getInstance().signOut(); // Sign out the user
            RegisterActivity();
        }
    }

    public void RegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void UserActivity(){
        Intent intent = new Intent(this, com.example.myapplication.UserActivity.class);
        startActivity(intent);
        finish();
    }


}
