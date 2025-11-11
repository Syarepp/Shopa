package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {
    private LinearLayout cartItemsLayout;
    private TextView totalPriceText;
    private Button buyNowButton;
    private CartDBHelper dbHelper;
    private List<CartItem> cartItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartItemsLayout = view.findViewById(R.id.cartItemsLayout);
        totalPriceText = view.findViewById(R.id.totalPriceText);
        buyNowButton = view.findViewById(R.id.buyNowButton);
        dbHelper = new CartDBHelper(getContext());

        loadCartItems();
        setupBuyNowButton();
    }

    private void loadCartItems() {
        cartItems = dbHelper.getCartItems();
        cartItemsLayout.removeAllViews();

        double total = 0;

        for (CartItem item : cartItems) {
            View itemView = getLayoutInflater().inflate(R.layout.cart_item, cartItemsLayout, false);

            TextView itemName = itemView.findViewById(R.id.itemName);
            TextView itemPrice = itemView.findViewById(R.id.itemPrice);
            TextView itemQuantity = itemView.findViewById(R.id.itemQuantity);
            Button removeButton = itemView.findViewById(R.id.removeButton);

            itemName.setText(item.getWatchName());
            itemPrice.setText(String.format(Locale.US, "$%.2f", item.getTotalPrice()));
            itemQuantity.setText("Qty: " + item.getQuantity());

            total += item.getTotalPrice();

            removeButton.setOnClickListener(v -> {
                dbHelper.removeFromCart(item.getId());
                loadCartItems();
                Toast.makeText(getContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();
            });

            cartItemsLayout.addView(itemView);
        }

        totalPriceText.setText(String.format(Locale.US, "Total: $%.2f", total));
    }

    private void setupBuyNowButton() {
        buyNowButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(getContext(), "Cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getContext(), CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCartItems();
    }
}