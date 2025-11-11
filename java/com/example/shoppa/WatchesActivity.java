package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class WatchesActivity extends AppCompatActivity {
    private GridView watchesGrid;
    private TextView modelTitle;
    private List<Watch> watchList;
    private CartDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watches);

        watchesGrid = findViewById(R.id.watchesGrid);
        modelTitle = findViewById(R.id.modelTitle);
        ImageButton backButton = findViewById(R.id.backButton);
        dbHelper = new CartDBHelper(this);

        String modelType = getIntent().getStringExtra("model_type");
        modelTitle.setText(modelType + " Watches");

        backButton.setOnClickListener(v -> finish());

        loadWatches(modelType);

        WatchesAdapter adapter = new WatchesAdapter(this, watchList, dbHelper);
        watchesGrid.setAdapter(adapter);
    }

    private void loadWatches(String modelType) {
        watchList = new ArrayList<>();
        switch (modelType) {
            case "Classic":
                watchList.add(new Watch("Classic Leather", 199.99, "Elegant leather strap watch with timeless design", R.drawable.classic_1));
                watchList.add(new Watch("Vintage Gold", 299.99, "Premium gold vintage watch with sapphire crystal", R.drawable.classic_2));
                watchList.add(new Watch("Minimalist", 159.99, "Simple and clean design for everyday wear", R.drawable.classic_3));
                break;
            case "Sport":
                watchList.add(new Watch("Sports Pro", 159.99, "Waterproof sports watch with stopwatch", R.drawable.sport_1));
                watchList.add(new Watch("Fitness Tracker", 129.99, "Smart fitness watch with heart rate monitor", R.drawable.sport_2));
                watchList.add(new Watch("Diver Watch", 249.99, "Professional diving watch with 200m water resistance", R.drawable.sport_3));
                break;
            case "Luxury":
                watchList.add(new Watch("Diamond Elite", 899.99, "Luxury watch with genuine diamonds", R.drawable.luxury_1));
                watchList.add(new Watch("Platinum Edition", 699.99, "Premium platinum watch with automatic movement", R.drawable.luxury_2));
                break;
            case "Smart":
                watchList.add(new Watch("Smart Pro", 199.99, "Advanced smartwatch with GPS and notifications", R.drawable.smart_1));
                watchList.add(new Watch("Fitness Band", 79.99, "Lightweight fitness tracker with sleep monitoring", R.drawable.smart_2));
                break;
            default:
                watchList.add(new Watch("Standard Watch", 99.99, "Quality timepiece with reliable movement", R.drawable.default_watch));
        }
    }
}