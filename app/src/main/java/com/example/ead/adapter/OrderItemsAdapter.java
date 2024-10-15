package com.example.ead.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead.R;
import com.example.ead.activities.FeedbackActivity;
import com.example.ead.models.OrderItemModel;
import com.example.ead.models.OrderStatus;

import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder>  {

    private List<OrderItemModel> itemList;
    private OrderStatus orderStatus;
    private Context context;

    public OrderItemsAdapter(List<OrderItemModel> itemList, OrderStatus orderStatus, Context context) {
        this.itemList = itemList;
        this.orderStatus = orderStatus;
        this.context = context;
    }

    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_orderstatus, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItemModel item = itemList.get(position);
        holder.tvItemName.setText(item.productName);
        holder.tvItemQuantity.setText("Qty: " + item.quantity);
        holder.tvItemTotal.setText("$" + String.format("%.2f", item.getTotalPrice()));

        // If you have an image, you can load it here, placeholder used for now
        // You can use libraries like Picasso or Glide to load images into ImageView
        // holder.imgItemCart.setImageResource(R.drawable.placeholder_image);

        // Show or hide the "Leave a Review" button based on the order status
        if (orderStatus == OrderStatus.Delivered) {
            holder.txtSubmitReview.setVisibility(View.VISIBLE);  // Show if delivered
        } else {
            holder.txtSubmitReview.setVisibility(View.GONE);  // Hide if not delivered
        }

        holder.txtSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to FeedbackActivity
                Intent intent = new Intent(context, FeedbackActivity.class);
                intent.putExtra("vendorId", item.vendorId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemQuantity, tvItemTotal,txtSubmitReview;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvMessage);
            tvItemQuantity = itemView.findViewById(R.id.tvItemQty);
            tvItemTotal = itemView.findViewById(R.id.tvItemTotal);
            txtSubmitReview = itemView.findViewById(R.id.txtSubmitReview);
        }
    }
}
