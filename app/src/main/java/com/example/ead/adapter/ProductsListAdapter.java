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
import com.example.ead.models.AverageRatingResponse;
import com.example.ead.models.ProductModel;
import com.example.ead.network.ApiClient;
import com.example.ead.services.RatingService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductsViewHolder> {

    private Context context;
    private List<ProductModel> listProducts;
    private RatingService ratingService;

    public ProductsListAdapter(Context context, List<ProductModel> listProducts) {
        this.context = context;
        // Initialize the list, ensuring it's never null
        this.listProducts = (listProducts != null) ? listProducts : new ArrayList<>();

        ratingService = ApiClient.getRetrofitInstance().create(RatingService.class);
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        // Load product image using Picasso/Glide or any image loading library
        Glide.with(context).load(product.getImgurl()).into(holder.imageView4);

        holder.txtRating.setText("Loading...");
        holder.txtRating.setContentDescription("Rating loading");

        fetchAverageRating(product, holder.txtRating);

        // Handle the click on the product card to navigate to ProductActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("productId", product.getId());  // Pass product ID
            context.startActivity(intent);
        });
    }

    private void fetchAverageRating(ProductModel product, TextView ratingTextView) {
        RatingService ratingService = ApiClient.getRetrofitInstance().create(RatingService.class);

        Call<AverageRatingResponse> call = ratingService.getAverageRating(product.getVendorId());
        call.enqueue(new Callback<AverageRatingResponse>() {
            @Override
            public void onResponse(Call<AverageRatingResponse> call, Response<AverageRatingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    double averageRating = response.body().getAverageRating();
                    ratingTextView.setText(String.format("%.1f", averageRating));

                    product.setRating(averageRating);
                } else {
                    ratingTextView.setText("N/A");
                }
            }

            @Override
            public void onFailure(Call<AverageRatingResponse> call, Throwable t) {
                ratingTextView.setText("N/A");
            }
        });
    }

    public void filterList(ArrayList<ProductModel> filteredList) {
        listProducts = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // Return the number of products
        return (listProducts != null) ? listProducts.size() : 0; // Ensure we don't call size() on null
    }

    // ViewHolder class for binding the views of the product card
    public static class ProductsViewHolder extends RecyclerView.ViewHolder {

        TextView productTitleTxt,textView30,txtRating;
        ImageView imageView4, imageView6;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            productTitleTxt = itemView.findViewById(R.id.productTitleTxt);
            textView30 = itemView.findViewById(R.id.textView30);
            imageView4 = itemView.findViewById(R.id.imageView4);
            txtRating = itemView.findViewById(R.id.txtRating);
//            textView29 = itemView.findViewById(R.id.textView29); // "+" button
        }
    }
}
