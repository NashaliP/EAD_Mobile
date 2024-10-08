package com.example.ead.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ead.R;
import com.example.ead.activities.CartActivity;
import com.example.ead.persistence.CartDao;
import com.example.ead.persistence.CartItem;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    private Context context;
    private CartDao cartDao;

    public CartAdapter(List<CartItem> cartItemList,Context context, CartDao cartDao) {
        this.cartItemList = cartItemList;
        this.context = context;
        this.cartDao = cartDao;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.txtCartItemName.setText(cartItem.getProductName());
        holder.txtPricePerItem.setText(String.format("$%.2f", cartItem.getPrice()));
        holder.txtCartItemQty.setText(String.valueOf(cartItem.getQuantity()));
        holder.txtTotalPerItem.setText(String.format("$%.2f", cartItem.getPrice() * cartItem.getQuantity()));



        // Handle Plus button action
        holder.itemView.findViewById(R.id.btnPlusCart).setOnClickListener(v -> {
            int newQuantity = cartItem.getQuantity() + 1;
            updateCartItem(cartItem, newQuantity);
        });

        // Handle Minus button action
        holder.itemView.findViewById(R.id.btnMinusCart).setOnClickListener(v -> {
            int newQuantity = cartItem.getQuantity() - 1;
            if (newQuantity <= 0) {
                // Remove the item from the cart
                removeCartItem(cartItem);
            } else {
                updateCartItem(cartItem, newQuantity);
            }
        });
    }

    private void updateCartItem(CartItem cartItem, int newQuantity) {
        cartItem.setQuantity(newQuantity);
        new Thread(() -> {
            cartDao.updateCartItem(cartItem); // Update the database
            ((Activity) context).runOnUiThread(() -> {
                notifyDataSetChanged();
                // Call to update subtotal in CartActivity
                ((CartActivity) context).calculateSubtotal(cartItemList); // Update subtotal
                ((CartActivity) context).updateOrderSummary(); // Update the order summary
            });
        }).start(); // Refresh the item view
    }

    private void removeCartItem(CartItem cartItem) {
        new Thread(() -> {
            cartDao.deleteCartItem(cartItem); // Remove item from the database
            cartItemList.remove(cartItem); // Remove item from the list
            ((Activity) context).runOnUiThread(() -> {
                notifyDataSetChanged(); // Refresh the RecyclerView
                // Call to update subtotal in CartActivity
                ((CartActivity) context).calculateSubtotal(cartItemList); // Update subtotal
                ((CartActivity) context).updateOrderSummary(); // Update the order summary
                Toast.makeText(context, "Item removed from cart!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtCartItemName, txtPricePerItem, txtCartItemQty, txtTotalPerItem;
        ImageView imgItemCart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCartItemName = itemView.findViewById(R.id.txtCartItemName);
            txtPricePerItem = itemView.findViewById(R.id.txtPricePerItem);
            txtCartItemQty = itemView.findViewById(R.id.txtCartItemQty);
            txtTotalPerItem = itemView.findViewById(R.id.txtTotalPerItem);
            imgItemCart = itemView.findViewById(R.id.imgItemCart);
        }
    }
}
