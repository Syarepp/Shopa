package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session = new SessionManager(getApplicationContext());

        // Use a Handler to delay the check, so the splash screen is visible
        new Handler().postDelayed(() -> {
            // Check if user is logged in
            if (session.isLoggedIn()) {
                // User is logged in, go to MainActivity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
            } else {
                // User is not logged in, go to LoginActivity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
            }
            // Close this activity
            finish();
        }, 2000); // 2 seconds delay
    }
}