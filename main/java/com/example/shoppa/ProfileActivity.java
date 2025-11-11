package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    // Declare UI components
    private ImageButton backButton, settingsButton;
    private Button editProfileButton;
    private TextView userName, userEmail, userBio;
    private LinearLayout myOrdersLayout, myAddressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Make sure your XML is named activity_profile.xml

        // Initialize UI components
        initializeViews();

        // Set up click listeners
        setupClickListeners();

        // Set user data
        setUserData();
    }

    private void initializeViews() {
        backButton = findViewById(R.id.backButton);
        settingsButton = findViewById(R.id.settingsButton);
        editProfileButton = findViewById(R.id.editProfileButton);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userBio = findViewById(R.id.userBio);

        // Initialize menu layouts
        myOrdersLayout = findViewById(R.id.myOrdersLayout);
        myAddressLayout = findViewById(R.id.myAddressLayout);
    }

    private void setupClickListeners() {
        // Back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back or finish activity
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                Toast.makeText(ProfileActivity.this, "Going back", Toast.LENGTH_SHORT).show();
            }
        });

        // Settings button click listener
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
                Toast.makeText(ProfileActivity.this, "Setiing", Toast.LENGTH_SHORT).show();
            }
        });

        // Edit profile button click listener
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to edit profile activity
                Toast.makeText(ProfileActivity.this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
                // Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                // startActivity(intent);
            }
        });

        // My Orders click listener
        myOrdersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "My Orders", Toast.LENGTH_SHORT).show();
            }
        });

        // My Address click listener
        myAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "My Address", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserData() {
        // Set sample user data - you can replace this with actual data from database/API
        userName.setText("Ali Shopa");
        userEmail.setText("ali.shopa@example.com");
        userBio.setText("Love shopping for great deals and discounts! Always looking for the best prices.");
    }
}