package com.example.ead.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead.R;
import com.example.ead.models.OrderItemModel;

import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemViewHolder>  {

    private List<OrderItemModel> itemList;

    public OrderItemsAdapter(List<OrderItemModel> itemList) {
        this.itemList = itemList;
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
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemQuantity, tvItemTotal;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemQuantity = itemView.findViewById(R.id.tvItemQty);
            tvItemTotal = itemView.findViewById(R.id.tvItemTotal);
        }
    }
}
