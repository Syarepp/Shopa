package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

// Imports for Navigation
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    // Declare UI components
    private ImageButton backButton, settingsButton;
    private Button editProfileButton;
    private TextView userName, userEmail, userBio;
    private LinearLayout myOrdersLayout, myAddressLayout;

    // Declare the new Navigation Bar
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This links to your existing XML file
        setContentView(R.layout.activity_profile);

        // Initialize UI components (including the new one)
        initializeViews();

        // Set up click listeners
        setupClickListeners();

        // Set user data
        setUserData();

        // NEW: Set up the bottom navigation logic
        setupNavigation();
    }

    private void initializeViews() {
        // Your existing views
        backButton = findViewById(R.id.backButton);
        settingsButton = findViewById(R.id.settingsButton);
        editProfileButton = findViewById(R.id.editProfileButton);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userBio = findViewById(R.id.userBio);
        myOrdersLayout = findViewById(R.id.myOrdersLayout);
        myAddressLayout = findViewById(R.id.myAddressLayout);

        // NEW: Find the navigation bar from your XML
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupClickListeners() {
        // Back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This is the correct way to "go back"
            }
        });

        // Settings button click listener
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
            }
        });

        // Edit profile button click listener
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        // Set sample user data
        userName.setText("Ali Shopa");
        userEmail.setText("ali.shopa@example.com");
        userBio.setText("Love shopping for great deals and discounts! Always looking for the best prices.");
    }

    // NEW METHOD: This adds all the logic for the navigation bar
    private void setupNavigation() {
        // This line highlights the "Profile" icon
        bottomNavigation.setSelectedItemId(R.id.navigation_profile);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    finish(); // Finish this activity
                    return true;
                } else if (itemId == R.id.navigation_models) {
                    Toast.makeText(ProfileActivity.this, "Categories", Toast.LENGTH_SHORT).show();
                    // TODO: Navigate to CategoriesActivity
                    return true;
                } else if (itemId == R.id.navigation_cart) {
                    Toast.makeText(ProfileActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                    // TODO: Navigate to CartActivity
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    // We are already here, do nothing
                    return true;
                }
                return false;
            }
        });
    }
}