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

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductModel> productList;

    // Constructor
    public ShopProductAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the product card layout
        View view = LayoutInflater.from(context).inflate(R.layout.product_hcard, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Get the product at the current position
        ProductModel product = productList.get(position);

        // Set the product data
        holder.productTitle.setText(product.getName());
        holder.productPrice.setText("$" + product.getPrice());

        // Use Glide to load the image from URL
        Glide.with(context)
                .load(product.getImgurl())
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder for each product card
    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productTitle, productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageView4);
            productTitle = itemView.findViewById(R.id.productTitleTxt);
            productPrice = itemView.findViewById(R.id.textView30);
        }
    }
}
