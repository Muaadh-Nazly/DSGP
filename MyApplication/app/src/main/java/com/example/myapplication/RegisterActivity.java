package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//Firebase Authentication imports
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/**
 * User registration page
 */
public class RegisterActivity extends AppCompatActivity {


    TextInputEditText  registerUsername;
    TextView signInInsteadSignUp,registerPassword;
    Button signUpButton;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerUsername = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);


        signUpButton = findViewById(R.id.signUpButton);
        signInInsteadSignUp = findViewById(R.id.signInInsteadSignUp);


        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email, password;

                email = registerUsername.getText().toString();
                password = String.valueOf(registerPassword.getText().toString());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;}

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;}

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(RegisterActivity.this, "Authentication successful.",
                                        Toast.LENGTH_SHORT).show();
                                    UserLocationPermissionActivity();

                                }

                                else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });


        signInInsteadSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }




    public void Login () {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void UserLocationPermissionActivity() {
        Intent intent = new Intent(this, UserLocationPermissionActivity.class);
        startActivity(intent);
        finish();
    }


}
