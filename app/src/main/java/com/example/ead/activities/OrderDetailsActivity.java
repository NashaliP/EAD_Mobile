package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ead.R;
import com.example.ead.adapter.OrderItemsAdapter;
import com.example.ead.models.OrderModel;
import com.example.ead.models.OrderStatus;
import com.example.ead.network.ApiClient;
import com.example.ead.services.OrderService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView tvOrderNumber, tvOrderStatus, tvOrderTotal, tvOrderDate, tvShippingAddress, tvPaymentMethod,tvCancellationNote;
    private RecyclerView productRV;
    private ImageView packageIcon;
    private Button btnCancelOrder;
    private OrderModel orderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Initialize the views
        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvOrderTotal = findViewById(R.id.tvOrderTotal);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvShippingAddress = findViewById(R.id.tvAddress);
        tvPaymentMethod = findViewById(R.id.tvPayment);
        productRV = findViewById(R.id.productRV);
        packageIcon = findViewById(R.id.imgPackageIcon);
        btnCancelOrder = findViewById(R.id.btnCancel);
        tvCancellationNote = findViewById(R.id.tvCancellationNote);

        // Get the order passed from the previous activity
        orderDetails = (OrderModel) getIntent().getSerializableExtra("order");

        // Setup the UI with order details
        populateOrderDetails();

        // Setup button actions based on order status
        setupButtonActions();
    }

    private void populateOrderDetails() {

        tvOrderNumber.setText("Order #" + orderDetails.orderId);
        tvOrderStatus.setText(orderDetails.status.name());
        tvOrderTotal.setText("$" + orderDetails.totalAmount);
        tvOrderDate.setText(orderDetails.orderDate);
        tvShippingAddress.setText(orderDetails.shippingAddress);
        tvPaymentMethod.setText(orderDetails.paymentMethod);

        // Setup the RecyclerView to show ordered items
        OrderItemsAdapter adapter = new OrderItemsAdapter(orderDetails.items,orderDetails.status, this);
        productRV.setLayoutManager(new LinearLayoutManager(this));
        productRV.setAdapter(adapter);

        // Set the package icon dynamically based on status
        setPackageIcon(orderDetails.status);

        // Display the cancellation note if the order is canceled
        if (orderDetails.status == OrderStatus.Canceled && orderDetails.cancellationNote != null && !orderDetails.cancellationNote.isEmpty()) {
            tvCancellationNote.setVisibility(View.VISIBLE);  // Make the note visible
            tvCancellationNote.setText("Cancellation Note: " + orderDetails.cancellationNote);
        } else {
            tvCancellationNote.setVisibility(View.GONE);  // Hide it if there's no note or order is not canceled
        }
    }

    private void setPackageIcon(OrderStatus status) {
        switch (status) {
            case Delivered:
                packageIcon.setImageResource(R.drawable.delivered);
                break;
            case Processing:
            case Partially_Delivered:
                packageIcon.setImageResource(R.drawable.processing);
                break;
            case Canceled:
                packageIcon.setImageResource(R.drawable.cancelled);
                break;
        }
    }

    private void setupButtonActions() {
        // Show/Hide buttons based on order status
        if (orderDetails.status == OrderStatus.Processing) {
            // Show cancel button for ongoing orders
            btnCancelOrder.setVisibility(View.VISIBLE);
            btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder();
                }
            });
        } else {
            // Hide cancel button if order is delivered or canceled
            btnCancelOrder.setVisibility(View.GONE);
        }
    }

    private void cancelOrder() {
        // Get the Retrofit instance
        Retrofit retrofit = ApiClient.getRetrofitInstance();
        OrderService orderService = retrofit.create(OrderService.class);

        // Make the cancel order request
        Call<ResponseBody> call = orderService.cancelOrder(orderDetails.orderId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Order cancellation request sent successfully
                    Toast.makeText(OrderDetailsActivity.this,
                            "Cancellation request sent. Awaiting approval.",
                            Toast.LENGTH_LONG).show();

                    // Optionally, disable the Cancel button to prevent repeated requests
                    btnCancelOrder.setEnabled(false);
                } else {
                    // Request failed
                    Toast.makeText(OrderDetailsActivity.this,
                            "Failed to send cancellation request. Try again.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle network errors
                Toast.makeText(OrderDetailsActivity.this,
                        "Network error. Please check your connection.",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void leaveReview() {
        Intent intent = new Intent(OrderDetailsActivity.this, FeedbackActivity.class);
        startActivity(intent);
    }
}