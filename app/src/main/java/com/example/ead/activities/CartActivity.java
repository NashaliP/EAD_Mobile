package com.example.ead.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ead.R;
import com.example.ead.adapter.CartAdapter;
import com.example.ead.persistence.AppDatabase;
import com.example.ead.persistence.CartDao;
import com.example.ead.persistence.CartItem;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> addressLauncher;
    private ActivityResultLauncher<Intent> paymentLauncher;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private CartDao cartDao;
    private TextView txtCartEmpty,txtSubtotal, txtDelivery, txtTotal,txtAddress, txtPaymentMethod;
    private ImageView addressArrow, paymentArrow;
    private Button btnPlaceOrder;
    private static final double DELIVERY_CHARGE = 102.00;
    private double subtotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRV);
        txtCartEmpty = findViewById(R.id.txtCartEmpty);
        txtSubtotal = findViewById(R.id.txtSubtotal);
        txtDelivery = findViewById(R.id.txtDelivery);
        txtTotal = findViewById(R.id.txtTotal);
        txtAddress = findViewById(R.id.txtAddress);
        txtPaymentMethod = findViewById(R.id.txtPayment);
        addressArrow = findViewById(R.id.addressArrow);
        paymentArrow = findViewById(R.id.paymentArrow);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Activity Result Launchers
        addressLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Handle address result
                            String address = data.getStringExtra("address");
                            txtAddress.setText(address); // Display address in the cart UI
                        }
                    }
                });

        paymentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Handle payment result
                            String paymentMethod = data.getStringExtra("paymentMethod");
                            txtPaymentMethod.setText(paymentMethod); // Display payment method in the cart UI
                        }
                    }
                });

        // Set onClick listeners
        addressArrow.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, addAddressActivity.class);
            addressLauncher.launch(intent);  // Launch with addressLauncher
        });

        paymentArrow.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            paymentLauncher.launch(intent);  // Launch with paymentLauncher
        });

        btnPlaceOrder.setOnClickListener(view -> {
            // Handle order placement logic
        });

        AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app_database").build();
        cartDao = database.cartDao();

        loadCartItems();
    }

    private void loadCartItems() {
        new Thread(() -> {
            List<CartItem> cartItems = cartDao.getCartItems();
            runOnUiThread(() -> {
                if (cartItems.isEmpty()) {
                    findViewById(R.id.txtCartEmpty).setVisibility(View.VISIBLE);
                    txtCartEmpty.setVisibility(View.VISIBLE);
                    cartRecyclerView.setVisibility(View.GONE);
                } else {
                    txtCartEmpty.setVisibility(View.GONE);
                    cartRecyclerView.setVisibility(View.VISIBLE);
                    cartAdapter = new CartAdapter(cartItems, this, cartDao);
                    cartRecyclerView.setAdapter(cartAdapter);

                    // Calculate the initial subtotal and update the UI
                    calculateSubtotal(cartItems);
                    updateOrderSummary();
                }
            });
        }).start();
    }

    public void calculateSubtotal(List<CartItem> cartItems) {
        subtotal = 0.0; // Reset subtotal
        for (CartItem item : cartItems) {
            subtotal += item.getPrice() * item.getQuantity(); // Accumulate price
        }
    }

    public void updateOrderSummary() {
        txtSubtotal.setText(String.format("$%.2f", subtotal)); // Update subtotal
        txtDelivery.setText(String.format("$%.2f", DELIVERY_CHARGE)); // Set delivery charge

        double total = subtotal + DELIVERY_CHARGE; // Calculate total
        txtTotal.setText(String.format("$%.2f", total)); // Update total
    }

}