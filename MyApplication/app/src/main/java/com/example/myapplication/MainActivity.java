package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    FirebaseAuth google_Account_Firebase_Authenticator_User_Instance;
    FirebaseUser application_User;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        google_Account_Firebase_Authenticator_User_Instance = FirebaseAuth.getInstance();
        application_User = google_Account_Firebase_Authenticator_User_Instance.getCurrentUser();

        // if no user found need to Register
        if (application_User != null) {

            setContentView(R.layout.activity_get_started);
            Button getStartedButton = findViewById(R.id.get_started);

            getStartedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RegisterActivity();
                }

            });


        }

        // User is signed in, directly navigate to UserActivity
        else {

//        FirebaseAuth.getInstance().signOut();
        RegisterActivity();
//            UserActivity();


        }

    }

    public void RegisterActivity(){

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();

    }

    public void UserActivity(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }


}


//      FirebaseAuth.getInstance().signOut(); // Sign out the user
//     ClassActivity classActivity = new ClassActivity();
