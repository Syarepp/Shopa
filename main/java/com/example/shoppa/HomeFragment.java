package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    // All the views from your "Home" layout
    private LinearLayout productsContainer;
    private GridView categoriesGrid;
    private Button btnProducts, btnDeals;
    private ImageView cartButton;

    // Change categories to watch models
    private String[] models = {"Classic", "Sport", "Luxury", "Smart", "Digital", "Vintage", "Chronograph", "Diver"};
    private int[] modelIcons = {
            android.R.drawable.ic_lock_idle_alarm,    // Classic - using system icons as placeholder
            android.R.drawable.ic_media_play,         // Sport
            android.R.drawable.star_big_on,           // Luxury
            android.R.drawable.ic_menu_manage,        // Smart
            android.R.drawable.ic_dialog_info,        // Digital
            android.R.drawable.ic_menu_today,         // Vintage
            android.R.drawable.ic_lock_idle_alarm,        // Chronograph
            android.R.drawable.ic_menu_compass        // Diver
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // All setup logic from your old MainActivity
        initializeViews(view);
        setupCategories(view);
        setupProducts(view);
        setupButtonListeners(view);
    }

    private void initializeViews(View view) {
        // Use 'view.findViewById' in Fragments
        productsContainer = view.findViewById(R.id.productsContainer);
        categoriesGrid = view.findViewById(R.id.categoriesGrid);
        btnProducts = view.findViewById(R.id.btnProducts);
        btnDeals = view.findViewById(R.id.btnDeals);
        cartButton = view.findViewById(R.id.cartButton);
    }

    private void setupCategories(View view) {
        // Use 'requireContext()' for the adapter's context
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.category_item, models) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    // Use 'getLayoutInflater()' from the fragment
                    convertView = getLayoutInflater().inflate(R.layout.category_item, parent, false);
                }
                ImageView icon = convertView.findViewById(R.id.categoryIcon);
                TextView name = convertView.findViewById(R.id.categoryName);
                icon.setImageResource(modelIcons[position]);
                name.setText(models[position]);
                return convertView;
            }
        };
        categoriesGrid.setAdapter(adapter);

        // When model is clicked, go to WatchesActivity
        categoriesGrid.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(getActivity(), WatchesActivity.class);
            intent.putExtra("model_type", models[position]);
            startActivity(intent);
        });
    }

    private void setupProducts(View view) {
        // Sample featured watches - using system drawables as placeholders
        int[] productImages = {
                android.R.drawable.ic_lock_idle_alarm,
                android.R.drawable.ic_media_play,
                android.R.drawable.star_big_on,
                android.R.drawable.ic_menu_manage,
                android.R.drawable.ic_dialog_info
        };

        String[] productNames = {
                "Classic Leather Watch",
                "Sports Pro Edition",
                "Luxury Gold Watch",
                "Smart Watch Pro",
                "Digital Chronograph"
        };

        double[] productPrices = {199.99, 159.99, 899.99, 299.99, 129.99};

        String[] productDescriptions = {
                "Elegant leather strap with classic design",
                "Waterproof sports watch with advanced features",
                "Premium luxury watch with gold plating",
                "Smartwatch with fitness tracking and notifications",
                "Modern digital watch with multiple time zones"
        };

        // Clear existing products
        productsContainer.removeAllViews();

        for (int i = 0; i < 5; i++) {
            // Use 'getLayoutInflater()'
            View productView = getLayoutInflater().inflate(R.layout.product_item, productsContainer, false);
            ImageView productImage = productView.findViewById(R.id.productImage);
            TextView productName = productView.findViewById(R.id.productName);
            TextView productPrice = productView.findViewById(R.id.productPrice);
            TextView productDiscount = productView.findViewById(R.id.productDiscount);

            productImage.setImageResource(productImages[i]);
            productName.setText(productNames[i]);
            productPrice.setText("$" + productPrices[i]);
            productDiscount.setText(i % 2 == 0 ? "20% OFF" : "NEW");

            // Add click listener to featured products
            final int finalI = i;
            productView.setOnClickListener(v -> {
                // Create a temporary CartDBHelper to add to cart
                CartDBHelper dbHelper = new CartDBHelper(getContext());
                dbHelper.addToCart(productNames[finalI], productPrices[finalI],
                        productDescriptions[finalI], productImages[finalI]);
                Toast.makeText(getContext(), productNames[finalI] + " added to cart!", Toast.LENGTH_SHORT).show();
            });

            productsContainer.addView(productView);
        }
    }

    private void setupButtonListeners(View view) {
        btnProducts.setOnClickListener(v -> {
            Toast.makeText(getContext(), "View All Products", Toast.LENGTH_SHORT).show();
            // You can navigate to a full products page here
            Intent intent = new Intent(getActivity(), WatchesActivity.class);
            intent.putExtra("model_type", "All");
            startActivity(intent);
        });

        btnDeals.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Today's Deals", Toast.LENGTH_SHORT).show();
            // Show special deals or promotions
            showSpecialDeals();
        });

        cartButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Cart", Toast.LENGTH_SHORT).show();
            // Navigate to cart - you might want to switch to CartFragment
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadFragment(new CartFragment());
                // Also update bottom navigation selection
                ((MainActivity) getActivity()).getBottomNavigation().setSelectedItemId(R.id.navigation_cart);
            }
        });
    }

    private void showSpecialDeals() {
        // Create a simple dialog or toast showing today's deals
        String[] deals = {
                "🔥 Classic Watches - 30% OFF",
                "⚡ Sports Watches - Buy 1 Get 1 Free",
                "💎 Luxury Collection - Special Financing",
                "📱 Smart Watches - Free Shipping"
        };

        StringBuilder dealsMessage = new StringBuilder("Today's Special Deals:\n");
        for (String deal : deals) {
            dealsMessage.append("• ").append(deal).append("\n");
        }

        Toast.makeText(getContext(), dealsMessage.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the view when returning from other activities
        if (getView() != null) {
            setupProducts(getView());
        }
    }
}