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

import com.example.ead.R;
import com.example.ead.activities.ProductListActivity;
import com.example.ead.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<CategoryModel> categoryList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // Get the current category from the list
        CategoryModel category = categoryList.get(position);

        // Set the category name
        holder.categoryNameTxt.setText(category.getCategoryName());
        // Set the category icon
        holder.imgCat.setImageResource(category.getImageResource());

        // Set the background dynamically based on position or ID
        holder.imgCat.setBackgroundResource(getBackgroundResourceForPosition(position));

        // Set click listener to navigate to ProductListActivity
        holder.itemView.setOnClickListener(v -> {
            // Create an Intent to navigate to ProductListActivity
            Intent intent = new Intent(context, ProductListActivity.class);
            // Pass the category ID or name to the ProductListActivity
            intent.putExtra("categoryId", category.getId());
            intent.putExtra("categoryName", category.getCategoryName());
            context.startActivity(intent);  // Start the new activity
        });
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCat;
        TextView categoryNameTxt;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views
            imgCat = itemView.findViewById(R.id.imgCat);
            categoryNameTxt = itemView.findViewById(R.id.categoryNameTxt);
        }
    }

    // Helper method to return the correct background resource based on the position or ID
    private int getBackgroundResourceForPosition(int position) {
        // You can map specific positions or IDs to background drawables here.
        switch (position) {
            case 0:
                return R.drawable.cat_1_bg;
            case 1:
                return R.drawable.cat_2_bg;
            case 2:
                return R.drawable.cat_3_bg;
            case 3:
                return R.drawable.cat_4_bg;
            case 4:
                return R.drawable.cat_5_bg;
            case 5:
                return R.drawable.cat_6_bg;
            case 6:
                return R.drawable.cat_7_bg;
            case 7:
                return R.drawable.cat_8_bg;
            default:
                return R.drawable.grey_background;
        }
    }
}
