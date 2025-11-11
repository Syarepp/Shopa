package com.example.shoppa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class WatchesAdapter extends ArrayAdapter<Watch> {
    private Context context;
    private List<Watch> watchList;
    private CartDBHelper dbHelper;

    public WatchesAdapter(Context context, List<Watch> watchList, CartDBHelper dbHelper) {
        super(context, R.layout.watch_item, watchList);
        this.context = context;
        this.watchList = watchList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.watch_item, parent, false);
        }

        Watch watch = watchList.get(position);

        ImageView watchImage = convertView.findViewById(R.id.watchImage);
        TextView watchName = convertView.findViewById(R.id.watchName);
        TextView watchPrice = convertView.findViewById(R.id.watchPrice);

        watchImage.setImageResource(watch.getImageResource());
        watchName.setText(watch.getName());
        watchPrice.setText(String.format("$%.2f", watch.getPrice()));

        convertView.setOnClickListener(v -> {
            Toast.makeText(context,
                    watch.getName() + "\n" + watch.getDescription() + "\nPrice: $" + watch.getPrice(),
                    Toast.LENGTH_LONG).show();

            dbHelper.addToCart(watch.getName(), watch.getPrice(), watch.getDescription(), watch.getImageResource());
            Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}