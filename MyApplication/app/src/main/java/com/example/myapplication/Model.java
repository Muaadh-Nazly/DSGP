package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.util.Locale;

public class Model extends AppCompatActivity {
    private TextView modelOutput2;
    private Interpreter tflite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.model);

        modelOutput2 = findViewById(R.id.modelOutput);
        floodLoadModel();
        LandslideModel();
        CycloneLoadModel();


    }

    private void floodLoadModel() {
        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelDownloader.getInstance()
                .getModel("NeuralNetwork", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions)
                .addOnSuccessListener(model -> {
                    File modelFile = model.getFile();
                    if (modelFile != null) {
                        tflite = new Interpreter(modelFile);
                        floodRunInference(); // Now that the model is loaded, run the inference
                    }
                })
                .addOnFailureListener(e -> Log.e("Model1", "Model download or initialization failed", e));
    }




    private void CycloneLoadModel(){

    }

    private void LandslideModel(){

    }




    private void floodRunInference() {
        // Example input data
        float[][] input = new float[1][2]; // Assuming your model expects 2 float values as input
        input[0][0] = 100f; // Example value
        input[0][1] = 100f; // Example value

        // Prepare the model's output
        float[][] output = new float[1][1]; // Assuming your model outputs a single float value

        tflite.run(input, output);

        // Process the output
        float prediction = output[0][0];
        // Display the result on UI
        modelOutput2.setText(String.format(Locale.US, "Prediction: %.2f", prediction));
    }


    private void cycloneRunInference(){

    }


    private void landslideRunInference(){

    }



    @Override
    protected void onDestroy() {
        if (tflite != null) {
            tflite.close(); // Close the interpreter when the activity is destroyed
        }
        super.onDestroy();
    }
}
