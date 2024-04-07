package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Set description
        TextView descriptionTextView = findViewById(R.id.text_description);
        descriptionTextView.setText("© 2024 Disaster Safeguard. All rights reserved.\n\n" +
                "This app is copyrighted by Disaster Safeguard. " +
                "No part of this application may be reproduced, distributed, " +
                "or transmitted in any form or by any means, without the prior " +
                "written permission of the publisher.");


        // Button listeners
        Button contactButton = findViewById(R.id.btn_contact);
        Button emailButton = findViewById(R.id.btn_send_email);
        Button messageButton = findViewById(R.id.btn_send_message);
        Button rateButton = findViewById(R.id.btn_rate_app);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+94 778945321";

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));

                // Try to find and open the Google Phone app
                Intent googlePhoneIntent = new Intent(Intent.ACTION_VIEW);
                googlePhoneIntent.setData(Uri.parse("tel:" + phoneNumber));
                googlePhoneIntent.setPackage("com.google.android.dialer");

                if (googlePhoneIntent.resolveActivity(getPackageManager()) != null) {
                    // Ask for permission before opening Google Phone
                    startActivityForResult(googlePhoneIntent, REQUEST_PHONE_PERMISSION);
                } else {
                    // If the Google Phone app is not found, resort to default phone dialer
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Ask for permission before opening the default phone app
                        startActivityForResult(intent, REQUEST_PHONE_PERMISSION);
                    } else {
                        Toast.makeText(AboutUsActivity.this, "No phone app found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Gmail app to send email
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:example@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");

                // Explicitly set the package name of the Gmail app to ensure it's opened
                emailIntent.setPackage("com.google.android.gm");

                // Verify that there's an activity to handle the intent
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    // Gmail app not found, fall back to any email client
                    Intent fallbackIntent = new Intent(Intent.ACTION_SEND);
                    fallbackIntent.setType("message/rfc822");
                    fallbackIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"example@gmail.com"});
                    fallbackIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    startActivity(Intent.createChooser(fallbackIntent, "Send Email"));
                }
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Try to open WhatsApp
                Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO);
                whatsappIntent.setData(Uri.parse("smsto:" + Uri.encode("0778945321")));
                whatsappIntent.setPackage("com.whatsapp");
                if (whatsappIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(whatsappIntent);
                } else {
                    // Try to open Google Messages
                    Intent googleMessagesIntent = new Intent(Intent.ACTION_VIEW);
                    googleMessagesIntent.setData(Uri.parse("sms:0778945321"));
                    googleMessagesIntent.putExtra("sms_body", "Hello from your app!"); // Set default message
                    googleMessagesIntent.setPackage("com.google.android.apps.messaging");

                    if (googleMessagesIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(googleMessagesIntent);
                    } else {
                        // If no messaging app is available, show a toast or handle it as appropriate
                        Toast.makeText(AboutUsActivity.this, "No messaging app available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });
    }
    private void showRatingDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rate_app, null);
        bottomSheetDialog.setContentView(dialogView);

        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        Button submitButton = dialogView.findViewById(R.id.btn_submit_rating);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                sendRatingByEmail(rating);
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }
    private void sendRatingByEmail(float rating) {
        String toEmail = "recipient@gmail.com";
        String fromEmail = "sender@gmail.com";
        String subject = "App Rating";
        String body = "Rating: " + rating;

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{toEmail});
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{fromEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } else {
            Toast.makeText(AboutUsActivity.this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }


    private static final int REQUEST_PHONE_PERMISSION = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PHONE_PERMISSION && resultCode == RESULT_OK) {
            // Permission granted, the phone app will be opened
        } else {
            Toast.makeText(this, "Permission denied, unable to open phone app", Toast.LENGTH_SHORT).show();
        }
    }
}
