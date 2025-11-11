// File: src/main/java/com/example/shoppa/ProfileFragment.java

package com.example.shoppa;

import android.content.Context; // Make sure Context is imported
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
// Remove duplicate imports if any
// import android.content.Intent;
// import android.widget.Toast;

public class ProfileFragment extends Fragment {

    // Views from your profile layout
    private ImageButton settingsButton;
    private Button editProfileButton;
    private TextView userName, userEmail, userBio;
    private LinearLayout myOrdersLayout, myAddressLayout;

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

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // *** THIS IS THE FIX ***
                // We check if the Fragment is currently added to an Activity
                // and if that Activity is not null.
                if (getActivity() != null && isAdded()) {
                    // Get the context from the Activity
                    Context context = getActivity();

                    // Start the SettingsActivity
                    startActivity(new Intent(context, SettingsActivity.class));

                    // Show the toast
                    Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editProfileButton.setOnClickListener(v -> {
            // We can add the same safety check here
            if (getActivity() != null && isAdded()) {
                Toast.makeText(getActivity(), "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
                // TODO: Start EditProfileActivity
                // startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        myOrdersLayout.setOnClickListener(v -> {
            if (getActivity() != null && isAdded()) {
                Toast.makeText(getActivity(), "My Orders", Toast.LENGTH_SHORT).show();
                // TODO: Start MyOrdersActivity
                // startActivity(new Intent(getActivity(), MyOrdersActivity.class));
            }
        });

        myAddressLayout.setOnClickListener(v -> {
            if (getActivity() != null && isAdded()) {
                Toast.makeText(getActivity(), "My Address", Toast.LENGTH_SHORT).show();
                // TODO: Start MyAddressActivity
                // startActivity(new Intent(getActivity(), MyAddressActivity.class));
            }
        });
    }

    private void setUserData(View view) {
        // Set sample data
        userName.setText("Ali Shopa");
        userEmail.setText("ali.shopa@example.com");
        userBio.setText("Love shopping for great deals and discounts! Always looking for the best prices.");
    }
}