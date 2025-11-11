package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {
    private LinearLayout orderItemsLayout;
    private TextView checkoutTotal;
    private EditText addressInput, cityInput, postalCodeInput;
    private Button confirmOrderButton;
    private ImageButton backButton;
    private CartDBHelper dbHelper;
    private List<CartItem> cartItems;
    private double totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        dbHelper = new CartDBHelper(this);
        cartItems = dbHelper.getCartItems();

        initializeViews();
        loadOrderItems();
        setupClickListeners();
    }

    private void initializeViews() {
        orderItemsLayout = findViewById(R.id.orderItemsLayout);
        checkoutTotal = findViewById(R.id.checkoutTotal);
        addressInput = findViewById(R.id.addressInput);
        cityInput = findViewById(R.id.cityInput);
        postalCodeInput = findViewById(R.id.postalCodeInput);
        confirmOrderButton = findViewById(R.id.confirmOrderButton);
        backButton = findViewById(R.id.backButton);
    }

    private void loadOrderItems() {
        orderItemsLayout.removeAllViews();
        totalAmount = 0;

        for (CartItem item : cartItems) {
            View itemView = getLayoutInflater().inflate(R.layout.cart_item, orderItemsLayout, false);

            Button removeButton = itemView.findViewById(R.id.removeButton);
            removeButton.setVisibility(View.GONE);

            TextView itemName = itemView.findViewById(R.id.itemName);
            TextView itemPrice = itemView.findViewById(R.id.itemPrice);
            TextView itemQuantity = itemView.findViewById(R.id.itemQuantity);

            itemName.setText(item.getWatchName());
            itemPrice.setText(String.format(Locale.US, "$%.2f", item.getTotalPrice()));
            itemQuantity.setText("Qty: " + item.getQuantity());

            totalAmount += item.getTotalPrice();
            orderItemsLayout.addView(itemView);
        }

        checkoutTotal.setText(String.format(Locale.US, "Total: $%.2f", totalAmount));
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(v -> finish());

        confirmOrderButton.setOnClickListener(v -> {
            String address = addressInput.getText().toString().trim();
            String city = cityInput.getText().toString().trim();
            String postalCode = postalCodeInput.getText().toString().trim();

            if (address.isEmpty() || city.isEmpty() || postalCode.isEmpty()) {
                Toast.makeText(this, "Please fill in all address fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.clearCart();
            Toast.makeText(this, "Order confirmed! Total: $" + totalAmount, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}