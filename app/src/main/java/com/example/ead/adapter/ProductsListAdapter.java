package com.example.ead.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ead.R;
import com.example.ead.activities.ProductActivity;
import com.example.ead.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductsViewHolder> {

    private Context context;
    private List<ProductModel> listProducts;

    public ProductsListAdapter(Context context, List<ProductModel> listProducts) {
        this.context = context;
        // Initialize the list, ensuring it's never null
        this.listProducts = (listProducts != null) ? listProducts : new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the product card layout (this will be the product card XML layout)
        View view = LayoutInflater.from(context).inflate(R.layout.product_hcard, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        // Bind data to the view holder
        ProductModel product = listProducts.get(position);

        // Set the data in the corresponding views
        holder.productTitleTxt.setText(product.getName());
        holder.textView30.setText("$" + product.getPrice());
        holder.ratingTxt.setText(String.valueOf(product.getRating()));

        // Load product image using Picasso/Glide or any image loading library
        Glide.with(context).load(product.getImgurl()).into(holder.imageView4);

        // Handle the click on the product card to navigate to ProductActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("productId", product.getId());  // Pass product ID
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Return the number of products
        return (listProducts != null) ? listProducts.size() : 0; // Ensure we don't call size() on null
    }

    // ViewHolder class for binding the views of the product card
    public static class ProductsViewHolder extends RecyclerView.ViewHolder {

        TextView productTitleTxt, ratingTxt, textView30;
        ImageView imageView4, imageView6;
        TextView textView29; // For the "+" button

        public ProductsViewHolder(@NonNull View itemView) {
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
