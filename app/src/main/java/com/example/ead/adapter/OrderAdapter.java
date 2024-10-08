package com.example.ead.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead.R;
import com.example.ead.activities.OrderDetailsActivity;
import com.example.ead.models.OrderModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderModel> orderList;

    public OrderAdapter(List<OrderModel> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_card, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.tvOrderTotal.setText(String.valueOf("$" + order.totalAmount));
        // Format the order date before displaying it
        String formattedDate = formatOrderDate(order.orderDate);
        holder.tvOrderDate.setText(formattedDate);

        holder.tvOrderStatus.setText(order.status.name());

        // On click listener for navigating to OrderDetailsActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OrderDetailsActivity.class);
            intent.putExtra("order", order);  // Passing the order object
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderDate, tvOrderTotal, tvOrderStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
        }
    }
    // Method to format the order date
    private String formatOrderDate(String orderDate) {
        try {
            // Parse the incoming date
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(orderDate);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return orderDate;
        }
    }

}
