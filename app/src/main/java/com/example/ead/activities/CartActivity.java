package com.example.ead.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ead.models.OrderStatus;
import com.example.ead.models.ProductModel;
import com.example.ead.network.ApiClient;

import com.example.ead.R;
import com.example.ead.adapter.CartAdapter;
import com.example.ead.models.OrderItemModel;
import com.example.ead.models.OrderModel;
import com.example.ead.models.OrderResponse;
import com.example.ead.persistence.AppDatabase;
import com.example.ead.persistence.CartDao;
import com.example.ead.persistence.CartItem;
import com.example.ead.services.OrderService;
import com.example.ead.services.ProductService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class CartActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> addressLauncher;
    private ActivityResultLauncher<Intent> paymentLauncher;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private CartDao cartDao;
    private TextView txtCartEmpty,txtSubtotal, txtDelivery, txtTotal,txtAddress, txtPaymentMethod;
    private ImageView addressArrow, paymentArrow;
    private Button btnPlaceOrder;
    private static final double DELIVERY_CHARGE = 22.00;
    private double subtotal = 0.0;
    private ProductService productService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRV);
        txtCartEmpty = findViewById(R.id.txtCartEmpty);
        txtSubtotal = findViewById(R.id.txtSubtotal);
        txtDelivery = findViewById(R.id.txtDelivery);
        txtTotal = findViewById(R.id.tvOrderTotal);
        txtAddress = findViewById(R.id.tvAddress);
        txtPaymentMethod = findViewById(R.id.tvPayment);
        addressArrow = findViewById(R.id.addressArrow);
        paymentArrow = findViewById(R.id.paymentArrow);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Initialize ProductService
        productService = ApiClient.getRetrofitInstance().create(ProductService.class);


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
                            Log.d("CartActivity", "Received Payment Method: " + paymentMethod); // Debug log
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
            if (txtAddress.getText().toString().isEmpty() || txtPaymentMethod.getText().toString().isEmpty()) {
                Toast.makeText(CartActivity.this, "Please enter address and payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                List<CartItem> cartItems = cartDao.getCartItems();

                if (!cartItems.isEmpty()) {
                    List<OrderItemModel> orderItems = new ArrayList<>();

                    for (CartItem item : cartItems) {

                        // Fetch product details using name
                        Call<ProductModel> call = productService.getProductByName(item.getProductName());
                        call.enqueue(new retrofit2.Callback<ProductModel>() {
                            @Override
                            public void onResponse(Call<ProductModel> call, retrofit2.Response<ProductModel> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    ProductModel product = response.body();

                                    // Create OrderItemModel without productId
                                    OrderItemModel orderItem = new OrderItemModel(
                                            product.getName(),
                                            product.getVendorId(),
                                            item.getQuantity(),
                                            item.getPrice()
                                    );

                                    orderItems.add(orderItem);


                                    // After processing all cart items, prepare the order model
                                    if (orderItems.size() == cartItems.size()) {

                                        // Get current date as a formatted string
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        String orderDate = sdf.format(new Date());

                                        // Generate a random order ID
                                        String generatedOrderId = OrderModel.generateOrderId();

                                        OrderModel orderModel = new OrderModel(
                                                orderItems,
                                                txtAddress.getText().toString(),
                                                txtPaymentMethod.getText().toString(),
                                                subtotal + DELIVERY_CHARGE,
                                                orderDate,
                                                OrderStatus.Processing,
                                                generatedOrderId
                                        );


                                        sendOrderToServer(orderModel);
                                    }
                                } else {
                                    runOnUiThread(() -> {
                                        Toast.makeText(CartActivity.this, "Failed to fetch product details", Toast.LENGTH_SHORT).show();
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<ProductModel> call, Throwable t) {
                                runOnUiThread(() -> {
                                    Toast.makeText(CartActivity.this, "Failed to fetch product details: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(CartActivity.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        });

        AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app_database").build();
        cartDao = database.cartDao();

        loadCartItems();
    }

    private void sendOrderToServer(OrderModel orderModel) {

        OrderService orderService = ApiClient.getRetrofitInstance().create(OrderService.class);

        // Create a map to wrap the orderModel under the "order" key
//        Map<String, OrderModel> orderMap = new HashMap<>();
//        orderMap.put("order", orderModel);

        // Create the API call for placing an order
        Call<OrderResponse> call = orderService.createOrder(orderModel);

        // Enqueue the call to handle the async response
        call.enqueue(new retrofit2.Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, retrofit2.Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        // Notify user that the order was successfully placed
                        Toast.makeText(CartActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

                        // Clear the cart (assuming you have a method to clear the cart in your local database)
                        new Thread(() -> cartDao.clearCart()).start();

                        // Optionally, redirect user to another activity or simply finish the CartActivity
                        finish();
                    });
                } else {
                    runOnUiThread(() -> {
                        // Handle the error response and show an error message
                        Toast.makeText(CartActivity.this, "Order placement failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                runOnUiThread(() -> {
                    // Handle failure (e.g., network error) and notify the user
                    Toast.makeText(CartActivity.this, "Failed to place order: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
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