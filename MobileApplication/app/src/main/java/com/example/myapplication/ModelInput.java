package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

public class ModelInput extends AppCompatActivity {

    private Interpreter interpreter; // Ensure this is initialized properly
    TextView modelOutput;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.model);

        modelOutput = findViewById(R.id.modelOutput);




    }
}
//        // Example input data
//        float[] inputFloats = {23f, 34f};
//
//        // Create an intent to start Model1 activity
//        Intent intent = new Intent(this, Model1.class);
//
//        // Put the input data into intent
//        intent.putExtra("inputFloats", inputFloats);
//
//        // Start Model1 activity
//        startActivity(intent);
//
//        modelOutput.setText("");
