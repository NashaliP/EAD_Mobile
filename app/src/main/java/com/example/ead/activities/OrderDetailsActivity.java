package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ead.R;
import com.example.ead.adapter.OrderItemsAdapter;
import com.example.ead.models.OrderModel;
import com.example.ead.models.OrderStatus;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView tvOrderNumber, tvOrderStatus, tvOrderTotal, tvOrderDate, tvShippingAddress, tvPaymentMethod;
    private RecyclerView productRV;
    private ImageView packageIcon;
    private Button btnCancelOrder, btnLeaveReview;
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
        btnLeaveReview = findViewById(R.id.btnSubmitReview);

        // Get the order passed from the previous activity (fragments)
        orderDetails = (OrderModel) getIntent().getSerializableExtra("order");

        // Setup the UI with order details
        populateOrderDetails();

        // Setup button actions based on order status
        setupButtonActions();
    }

    private void populateOrderDetails() {
        // Set order details
//        tvOrderNumber.setText("Order #" + order.orderNumber);
        tvOrderStatus.setText(orderDetails.status.name());
        tvOrderTotal.setText("$" + orderDetails.totalAmount);
        tvOrderDate.setText(orderDetails.orderDate);
        tvShippingAddress.setText(orderDetails.shippingAddress);
        tvPaymentMethod.setText(orderDetails.paymentMethod);

        // Setup the RecyclerView to show ordered items
        OrderItemsAdapter adapter = new OrderItemsAdapter(orderDetails.items);
        productRV.setLayoutManager(new LinearLayoutManager(this));
        productRV.setAdapter(adapter);

        // Set the package icon dynamically based on status
        setPackageIcon(orderDetails.status);
    }

    private void setPackageIcon(OrderStatus status) {
        switch (status) {
            case Delivered:
                packageIcon.setImageResource(R.drawable.delivered); // Replace with your actual drawable
                break;
            case Processing:
            case Partially_Delivered:
                packageIcon.setImageResource(R.drawable.processing); // Replace with your actual drawable
                break;
            case Cancelled:
                packageIcon.setImageResource(R.drawable.cancelled); // Replace with your actual drawable
                break;
        }
    }

    private void setupButtonActions() {
        // Show/Hide buttons based on order status
        if (orderDetails.status == OrderStatus.Processing || orderDetails.status == OrderStatus.Partially_Delivered) {
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

        if (orderDetails.status == OrderStatus.Delivered) {
            // Show "Leave a Review" button for completed orders
            btnLeaveReview.setVisibility(View.VISIBLE);
            btnLeaveReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leaveReview();
                }
            });
        } else {
            // Hide review button for ongoing or canceled orders
            btnLeaveReview.setVisibility(View.GONE);
        }
    }

    private void cancelOrder() {
        // Handle the order cancellation logic here
        // You could call an API to cancel the order, then refresh the status or notify the user
    }

    private void leaveReview() {
        // Handle leaving a review, possibly open a new activity or dialog for submitting a review
    }
}