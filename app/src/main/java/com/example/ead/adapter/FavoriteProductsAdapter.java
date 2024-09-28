package com.example.ead.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ead.R;
import com.example.ead.models.ProductModel;

import java.util.List;

public class FavoriteProductsAdapter extends RecyclerView.Adapter<FavoriteProductsAdapter.FavoritesViewHolder> {

    private Context context;
    private List<ProductModel> favoriteProducts; // List to hold favorite products

    public FavoriteProductsAdapter(Context context, List<ProductModel> favoriteProducts) {
        this.context = context;
        this.favoriteProducts = favoriteProducts;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the product card layout (this will be the product card XML layout)
        View view = LayoutInflater.from(context).inflate(R.layout.product_hcard, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        // Bind data to the view holder
        ProductModel product = favoriteProducts.get(position);

        // Set the data in the corresponding views
        holder.productTitleTxt.setText(product.getTitle());
        holder.textView30.setText("$" + product.getPrice());
        holder.ratingTxt.setText(String.valueOf(product.getRating()));

        // Load product image using Picasso/Glide or any image loading library
        Glide.with(context).load(product.getImageResource()).into(holder.imageView4);
    }

    @Override
    public int getItemCount() {
        // Return the number of favorite products
        return favoriteProducts.size();
    }

    // ViewHolder class for binding the views of the product card
    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        TextView productTitleTxt, ratingTxt, textView30;
        ImageView imageView4, imageView6;
        TextView textView29; // For the "+" button

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            productTitleTxt = itemView.findViewById(R.id.productTitleTxt);
            ratingTxt = itemView.findViewById(R.id.ratingTxt);
            textView30 = itemView.findViewById(R.id.textView30);
            imageView4 = itemView.findViewById(R.id.imageView4);
            imageView6 = itemView.findViewById(R.id.imageView6); // Rating star icon
//            textView29 = itemView.findViewById(R.id.textView29); // "+" button
        }
    }
}
