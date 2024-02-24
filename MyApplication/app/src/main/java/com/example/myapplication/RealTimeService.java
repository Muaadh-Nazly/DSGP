//package com.example.myapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.tensorflow.lite.Interpreter;
//
//public class ModelInput extends AppCompatActivity {
//
//    private Interpreter interpreter; // Ensure this is initialized properly
//    TextView modelOutput;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.model);
//
//        modelOutput = findViewById(R.id.modelOutput);