// File: src/main/java/com/example/shoppa/ProfileFragment.java

package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    // Views from your profile layout
    private ImageButton settingsButton;
    private Button editProfileButton;
    private TextView userName, userEmail, userBio;
    private LinearLayout myOrdersLayout, myAddressLayout;

    // NOTE: The 'backButton' is removed. Navigation is handled
    // by the main BottomNavigationView and the device's back button.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // All setup logic from your old ProfileActivity
        initializeViews(view);
        setupClickListeners(view);
        setUserData(view);
    }

    private void initializeViews(View view) {
        // Use 'view.findViewById'
        settingsButton = view.findViewById(R.id.settingsButton);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userBio = view.findViewById(R.id.userBio);
        myOrdersLayout = view.findViewById(R.id.myOrdersLayout);
        myAddressLayout = view.findViewById(R.id.myAddressLayout);
    }

    private void setupClickListeners(View view) {
        // 'backButton' is no longer here.

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use 'getContext()' for the Intent context.
                // This starts SettingsActivity as a *new* screen.
                startActivity(new Intent(getContext(), SettingsActivity.class));
                Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
            }
        });

        editProfileButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
            // TODO: Start EditProfileActivity
            // startActivity(new Intent(getContext(), EditProfileActivity.class));
        });

        myOrdersLayout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "My Orders", Toast.LENGTH_SHORT).show();
            // TODO: Start MyOrdersActivity
            // startActivity(new Intent(getContext(), MyOrdersActivity.class));
        });

        myAddressLayout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "My Address", Toast.LENGTH_SHORT).show();
            // TODO: Start MyAddressActivity
            // startActivity(new Intent(getContext(), MyAddressActivity.class));
        });
    }

    private void setUserData(View view) {
        // Set sample data
        userName.setText("Ali Shopa");
        userEmail.setText("ali.shopa@example.com");
        userBio.setText("Love shopping for great deals and discounts! Always looking for the best prices.");
    }
}