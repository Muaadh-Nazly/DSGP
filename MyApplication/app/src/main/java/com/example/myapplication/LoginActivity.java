package com.example.myapplication;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText usernameLogin, passwordLogin;
    TextView signUpInsteadSignIn;
    Button signInButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        usernameLogin = findViewById(R.id.usernameLogin);
        usernameLogin.setText("test4@iit.ac.lk");

        passwordLogin = findViewById(R.id.passwordLogin);
        passwordLogin.setText("PerfLog55");

        signInButton = findViewById(R.id.signInButton);
        signUpInsteadSignIn = findViewById(R.id.signUpInsteadSignIn);


        signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email, password;
                    email = usernameLogin.getText().toString();
                    password = String.valueOf(passwordLogin.getText().toString());

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // If sign in successful, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Authentication successful.",
                                            Toast.LENGTH_SHORT).show();
                                        UserLocationPermissionActivity();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            });


        signUpInsteadSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });


    }

    public void Register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    public void UserLocationPermissionActivity() {
        Intent intent = new Intent(this, UserLocationPermissionActivity.class);
        startActivity(intent);
        finish();
    }

}



//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//            startActivity(intent);
//            finish();
//
//        }
//    }
