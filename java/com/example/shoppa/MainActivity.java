package com.example.shoppa;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize bottom navigation
        bottomNavigation = findViewById(R.id.bottomNavigation);
        setupNavigation();

        // Load the default fragment (Home) when the app starts
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        }
    }

    private void setupNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.navigation_models) {
                    // Show message that models can be accessed from Home
                    Toast.makeText(MainActivity.this, "Browse watch models from Home screen", Toast.LENGTH_SHORT).show();
                    return false; // Don't change selection, stay on current fragment
                } else if (itemId == R.id.navigation_cart) {
                    selectedFragment = new CartFragment();
                } else if (itemId == R.id.navigation_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    return loadFragment(selectedFragment);
                }
                return false;
            }
        });
    }

    /**
     * Helper method to load fragments into the container
     * @param fragment The fragment to load
     * @return true if fragment was loaded successfully
     */
    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            try {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            } catch (Exception e) {
                Toast.makeText(this, "Error loading fragment", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Handle back button press to navigate between fragments or exit app
     */
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // If we're not on the home fragment, go back to home
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof HomeFragment)) {
            // If current fragment is not Home, go to Home
            loadFragment(new HomeFragment());
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        } else {
            // If we're on Home fragment, exit the app
            super.onBackPressed();
        }
    }

    /**
     * Method to update cart badge count (if you want to implement badge notifications)
     */
    public void updateCartBadge(int itemCount) {
        // You can implement badge notification on cart icon here
        // This would require a custom BottomNavigationView with badge support
        if (itemCount > 0) {
            // Show badge with item count
            Toast.makeText(this, "Cart updated: " + itemCount + " items", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to refresh current fragment
     */
    public void refreshCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment != null) {
            loadFragment(currentFragment);
        }
    }

    /**
     * Getter for bottom navigation (used by HomeFragment)
     */
    public BottomNavigationView getBottomNavigation() {
        return bottomNavigation;
    }

    /**
     * Method to navigate to specific fragment from other activities
     */
    public void navigateToFragment(Fragment fragment) {
        if (fragment instanceof CartFragment) {
            bottomNavigation.setSelectedItemId(R.id.navigation_cart);
        } else if (fragment instanceof ProfileFragment) {
            bottomNavigation.setSelectedItemId(R.id.navigation_profile);
        } else if (fragment instanceof HomeFragment) {
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        }
        loadFragment(fragment);
    }

    /**
     * Method to get current fragment
     */
    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    /**
     * Method to show a temporary message
     */
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to show a long message
     */
    public void showLongMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Called when activity is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh cart badge or other UI elements if needed
        refreshCartBadge();
    }

    /**
     * Refresh cart badge with current item count
     */
    private void refreshCartBadge() {
        CartDBHelper dbHelper = new CartDBHelper(this);
        int cartItemCount = dbHelper.getCartItems().size();

        if (cartItemCount > 0) {
            // You can implement actual badge here
            // For now, we'll just log it
            System.out.println("Cart items: " + cartItemCount);
        }
    }

    /**
     * Method to clear back stack
     */
    public void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Method to handle deep linking or specific intents
     */
    public void handleDeepLink(String action) {
        switch (action) {
            case "open_cart":
                navigateToFragment(new CartFragment());
                break;
            case "open_profile":
                navigateToFragment(new ProfileFragment());
                break;
            case "open_home":
                navigateToFragment(new HomeFragment());
                break;
            default:
                navigateToFragment(new HomeFragment());
                break;
        }
    }
}