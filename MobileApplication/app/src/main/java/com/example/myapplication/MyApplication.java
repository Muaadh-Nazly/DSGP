//package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.app.Application;
//import com.google.firebase.FirebaseApp;
//public class MyApplication extends Application {
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        // Initialize Firebase
//        FirebaseApp.initializeApp(this);
//    }
//}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_get_started);
//        Button getStartedButton = findViewById(R.id.get_started);
//
//
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//
//        FirebaseAuth.getInstance().signOut(); // Sign out the user
//
//
//        if (user == null) {
//            // User is not signed in, set up the Get Started button click listener
////            getStartedButton.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    // Navigate to the RegisterActivity activity when Get Started is clicked
////                    RegisterActivity();
////                }
////            });
//
//            Register();
//
//
//
//        } else {
//            // User is signed in, directly navigate to UserActivity
//            FirebaseAuth.getInstance().signOut(); // Sign out the user
//            Register();
//        }
//    }



///////////////////////////////////////////////////////////////////////////////////////////

//package com.example.myapplication;
//
//import android.os.Bundle;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//
//public class m2 {
//    package com.example.myapplication;
//
//import android.content.Intent;
//import android.icu.text.SimpleDateFormat;
//import android.icu.util.Calendar;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import com.google.firebase.auth.FirebaseAuth;
//import java.util.Locale;
//
//    public class main extends AppCompatActivity {
//
//
//        protected void onCreate(Bundle savedInstanceState) {
//
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_get_started);
//            Button getStartedButton = findViewById(R.id.activity_get_started);
//            getStartedButtonClicked();
//
//
//            protected void getStartedButtonClicked(Bundle savedInstance) {
//                super.onCreate(savedInstance);
//                setContentView(R.layout.activity_register);
//
//                mAuth = FirebaseAuth.getInstance();
//                editTextEmail = findViewById(R.id.registerUsername);
//                editTextPassword = findViewById(R.id.registerPassword);
//                buttonRegister = findViewById(R.id.registerButton);
//                loginPage = findViewById(R.id.loginInsteadRegister);
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
////            Intent intent = new Intent(main.this, RegisterActivity.class);
////            startActivity(intent);
//
//
//            });
//        }
//    }
//
//
//
////
////
////
////        private void loadActivity() {
////            setContentView(R.layout.activity_main);
////
////            ImageView imageView_1 = findViewById(R.id.image_1);
////            ImageView imageView_2 = findViewById(R.id.image_2);
////            ImageView imageView_3 = findViewById(R.id.image_3);
////            ImageView imageView_4 = findViewById(R.id.image_4);
////            ImageView imageView_5 = findViewById(R.id.image_5);
////            ImageView imageView_6 = findViewById(R.id.image_6);
////
////            imageView_1.setImageResource(R.drawable.flood);
////            imageView_2.setImageResource(R.drawable.landslide);
////            imageView_3.setImageResource(R.drawable.cyclone);
////            imageView_4.setImageResource(R.drawable.fullreport);
////            imageView_5.setImageResource(R.drawable.aboutus);
////            imageView_6.setImageResource(R.drawable.images);
////
////            TextView greetNameTextView = findViewById(R.id.greetName);
////
////            // Get the current time
////            Calendar currentTime = Calendar.getInstance();
////            SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
////            int hour = Integer.parseInt(hourFormat.format(currentTime.getTime()));
////
////            // Set greeting based on the time of the day
////            String greeting = getGreeting(hour);
////            greetNameTextView.setText(greeting);
////
////            CardView FloodCard = findViewById(R.id.floodCard);
////            CardView LandslideCard = findViewById(R.id.landslideCard);
////            CardView cycloneCard = findViewById(R.id.CycloneCard);
////            CardView ReportCard = findViewById(R.id.fullreportCard);
////            CardView AboutUsCard = findViewById(R.id.aboutusCard);
////            CardView UserCard = findViewById(R.id.userCard);
////
////
////            FloodCard.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Intent intent = new Intent(main.this, FloodActivity.class);
////                    startActivity(intent);
////                }
////            });
////
////            LandslideCard.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Intent intent = new Intent(main.this, LandslideActivity.class);
////                    startActivity(intent);
////                }
////            });
////
////            cycloneCard.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Intent intent = new Intent(main.this, CycloneActivity.class);
////                    startActivity(intent);
////                }
////            });
////
////            ReportCard.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Intent intent = new Intent(main.this, ReportActivity.class);
////                    startActivity(intent);
////                }
////            });
////
////            AboutUsCard.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Intent intent = new Intent(main.this, AboutUsActivity.class);
////                    startActivity(intent);
////                }
////            });
////
////            UserCard.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    Intent intent = new Intent(main.this, UserActivity.class);
////                    startActivity(intent);
////                }
////            });
////
////        }
////
////        private String getGreeting ( int hour){
////            if (hour >= 0 && hour < 12) {
////                return "Good Morning User ";
////            } else if (hour >= 12 && hour < 17) {
////                return "Good Afternoon User ";
////            } else {
////                return "Good Evening User ";
////            }
////        }
////    }
//
//
//
////            @Override
////            public void onClick(View view) {
////                if (!isGetStartedLoaded) {
////                    // Load activity_main.xml dynamically
////                    setContentView(R.layout.activity_register);
////                    isGetStartedLoaded = true;
////
////                    ImageView imageView = findViewById(R.id.imageView);
////                    ImageView imageView2 = findViewById(R.id.imageView2);
////                    ImageView imageView3 = findViewById(R.id.imageView3);
////
////                    imageView.setImageResource(R.drawable.facebook);
////                    imageView2.setImageResource(R.drawable.twitter);
////                    imageView3.setImageResource(R.drawable.g_logo);
////
////                    // Set up loginButton click listener in main.xml
////                    Button registerButton = findViewById(R.id.registerButton);
////                    registerButton.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
//////                            // Your activity_login logic here
//////                            EditText username = findViewById(R.id.username);
//////                            EditText password = findViewById(R.id.password);
//////                            if (username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
//////                                // LoginActivity successful, load the ClothingActivity
//////                                loadActivity();
//////                            } else {
//////                                Toast.makeText(main.this, "LoginActivity Failed!", Toast.LENGTH_SHORT).show();
//////                            }
////                            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
////                            startActivity(intent);
////                            finish();
////
////
////                            }
//
////
//}


////////////////////////////////////////////////////////////////////////////////////////////


//
//
//public class Model1 extends AppCompatActivity {
//
//
//    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (isConnectedToInternet(context)) {
//                downloadModel();
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Register network change broadcast receiver
//        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//
//        downloadModel();
//    }
//
//    private void downloadModel() {
//        FirebaseModelDownloader.getInstance()
//                .getModel("NeuralNetwork", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, new CustomModelDownloadConditions.Builder().build())
//                .addOnSuccessListener(new OnSuccessListener<CustomModel>() {
//                    @Override
//                    public void onSuccess(CustomModel model) {
//                        // Model downloaded successfully
//                        // Initialize your model here
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    if (e instanceof FirebaseMlException) {
//                        FirebaseMlException mlException = (FirebaseMlException) e;
//                        if (mlException.getCode() == FirebaseMlException.UNAVAILABLE) {
//                            // Model download failed due to no internet connection
//                            // The BroadcastReceiver will attempt to download the model again when the internet is available
//                        }
//                    }
//                });
//    }
//
//    private boolean isConnectedToInternet(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Unregister network change broadcast receiver
//        unregisterReceiver(networkChangeReceiver);
//    }
//}
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
//
//
////    public float[] runInference(float[] inputFloats) {
////        // Ensure the model is loaded
////        if (interpreter == null) {
//           throw new IllegalStateException("Interpreter is not initialized or model loading failed. HERE blah");
//        }
//
//        // Preparing the input buffer
//        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(4 * inputFloats.length).order(ByteOrder.nativeOrder());
//        for (float value : inputFloats) {
//            inputBuffer.putFloat(value);
//        }
//
//        // Assuming the model's output is a single float representing a probability
//        ByteBuffer outputBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder());
//        interpreter.run(inputBuffer, outputBuffer);
//
//        // Rewind the output buffer to read from it
//        outputBuffer.rewind();
//        float[] results = new float[1]; // Allocate a float array to hold the model's output
//        outputBuffer.asFloatBuffer().get(results); // Read the model's output into the array
//
//        return results;
//    }




/**
 *  // Example for a model expecting a float array as input
 *         float[] inputData = { 23,34};
 *         ByteBuffer inputBuffer = ByteBuffer.allocateDirect(inputData.length * Float.SIZE / Byte.SIZE);
 *         inputBuffer.order(ByteOrder.nativeOrder());
 *
 *         for (float data : inputData) {
 *             inputBuffer.putFloat(data);
 *         }
 *
 *         // Assuming the model's input and output are both float arrays.
 *         float[] outputData = new float[/* size of your model's output *
 */
