package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout productsContainer;
    private GridView categoriesGrid;
    private BottomNavigationView bottomNavigation;

    // Sample data
    private String[] categories = {"Electronics", "Fashion", "Home", "Beauty", "Sports", "Toys", "Food", "More"};
    private int[] categoryIcons = {R.drawable.electronics, R.drawable.fashion, R.drawable.home,
            R.drawable.beauty, R.drawable.sports, R.drawable.toys,
            R.drawable.foods, R.drawable.more};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupCategories();
        setupProducts();
        setupNavigation();
        setupButtonListeners();
    }

    private void initializeViews() {
        productsContainer = findViewById(R.id.productsContainer);
        categoriesGrid = findViewById(R.id.categoriesGrid);
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupCategories() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.category_item, categories) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.category_item, parent, false);
                }

                ImageView icon = convertView.findViewById(R.id.categoryIcon);
                TextView name = convertView.findViewById(R.id.categoryName);

                icon.setImageResource(categoryIcons[position]);
                name.setText(categories[position]);

                return convertView;
            }
        };

        categoriesGrid.setAdapter(adapter);

        // Category click listener
        categoriesGrid.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(MainActivity.this, "Clicked: " + categories[position], Toast.LENGTH_SHORT).show();
            // Navigate to category page
        });
    }

    private void setupProducts() {
        // Use Android built-in drawables or create your own placeholders
        int[] productImages = {
                R.drawable.headphone,
                R.drawable.smartwatch,
                R.drawable.speaker,
                R.drawable.phonecase,
                R.drawable.powerbank
        };

        String[] productNames = {
                "Wireless Headphones",
                "Smart Watch",
                "Bluetooth Speaker",
                "Phone Case",
                "Portable Charger"
        };

        double[] productPrices = {49.99, 129.99, 29.99, 15.99, 24.99};

        for (int i = 0; i < 5; i++) {
            View productView = getLayoutInflater().inflate(R.layout.product_item, productsContainer, false);

            ImageView productImage = productView.findViewById(R.id.productImage);
            TextView productName = productView.findViewById(R.id.productName);
            TextView productPrice = productView.findViewById(R.id.productPrice);
            TextView productDiscount = productView.findViewById(R.id.productDiscount);

            // Set placeholder image
            productImage.setImageResource(productImages[i]);

            productName.setText(productNames[i]);
            productPrice.setText("$" + productPrices[i]);
            productDiscount.setText(i % 2 == 0 ? "20% OFF" : "Hot Deal");

            productsContainer.addView(productView);
        }
    }

    private void setupNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.navigation_profile) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void setupButtonListeners() {
        Button btnProducts = findViewById(R.id.btnProducts);
        Button btnDeals = findViewById(R.id.btnDeals);
        ImageView cartButton = findViewById(R.id.cartButton);

        btnProducts.setOnClickListener(v -> {
            Toast.makeText(this, "View All Products", Toast.LENGTH_SHORT).show();
            // Navigate to products activity
        });

        btnDeals.setOnClickListener(v -> {
            Toast.makeText(this, "Today's Deals", Toast.LENGTH_SHORT).show();
            // Navigate to deals activity
        });

        cartButton.setOnClickListener(v -> {
            Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show();
            // Navigate to cart activity
        });
    }
}