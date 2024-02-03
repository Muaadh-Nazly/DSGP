package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;


/**
 *  This where the application runs
 */
public class MainActivity extends AppCompatActivity {

    private boolean isGetStartedLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        // Delay the transition to the 'get_started' layout
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showGetStartedLayout();
            }
        }, 3000); // 2000 milliseconds (2 seconds)
    }

    private void showGetStartedLayout() {
        setContentView(R.layout.get_started);
        Button getStartedButton = findViewById(R.id.getStartedButton);
        // Add your click listener or other logic related to the 'get_started' layout

        /**
         *  User is trying to loging to system ### TODO : Firebase task?
         */
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isGetStartedLoaded) {
                    // Load activity_main.xml dynamically
                    setContentView(R.layout.login);
                    isGetStartedLoaded = true;

                    ImageView imageView = findViewById(R.id.imageView);
                    ImageView imageView2 = findViewById(R.id.imageView2);
                    ImageView imageView3 = findViewById(R.id.imageView3);

                    imageView.setImageResource(R.drawable.facebook);
                    imageView2.setImageResource(R.drawable.twitter);
                    imageView3.setImageResource(R.drawable.g_logo);

                    // Set up loginButton click listener in main.xml
                    Button loginButton = findViewById(R.id.loginButton);

                    /**
                     * If the user successfully logged in proceed to user Home Page otherwise send the retry message
                     */
                    loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Your login logic here
                            EditText username = findViewById(R.id.username);
                            EditText password = findViewById(R.id.password);
                            if (username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                                userHomePage();
                            } else {
                                Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * User Home Page loading once get on app
     */
    private void userHomePage() {
        setContentView(R.layout.home);
    }

    /**
     * @param hour : user time
     * @return current time
     * Current time is released and it should display on HomePage
     */
    private String getGreeting(int hour) {
        if (hour >= 0 && hour < 12) {
            return "Good Morning User !! ";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon User !!";
        } else {
            return "Good Evening User !!";
        }
    }
}
