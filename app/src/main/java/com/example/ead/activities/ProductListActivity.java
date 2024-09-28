package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ead.R;
import com.example.ead.adapter.ProductListAdapter;
import com.example.ead.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView productListRV;
    TextView titleTxt;
    ProgressBar progressBar;
    List<ProductModel> products = new ArrayList<>();
    ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Initialize views
        productListRV = findViewById(R.id.productListRV);
        titleTxt = findViewById(R.id.titleTxt);
        progressBar = findViewById(R.id.progressBar);

        // Get the category info from the Intent
        String categoryId = getIntent().getStringExtra("categoryId");
        String categoryName = getIntent().getStringExtra("categoryName");

        // Set the category name as the title
        titleTxt.setText(categoryName);

        // Set up RecyclerView
        productListAdapter = new ProductListAdapter(this, products);
        productListRV.setAdapter(productListAdapter);
        productListRV.setLayoutManager(new LinearLayoutManager(this));

        // Fetch products based on the category
        fetchProductsByCategory(categoryId);


    }

    private void fetchProductsByCategory(String categoryId) {
        // For now, you can hardcode some static products based on the category ID
        // Later, replace this with real data fetching from the backend
        progressBar.setVisibility(View.VISIBLE);

        if (categoryId.equals("1")) {
            products.add(new ProductModel("1", "Red Dress",  R.drawable.dress1, 59.99, 4.8f));
            products.add(new ProductModel("2", "Blue Dress",  R.drawable.dress2,79.99, 4.5f));
            products.add(new ProductModel("3", "Green Dress",  R.drawable.dress3,79.99, 4.5f));
//        } else if (categoryId.equals("2")) {
//            products.add(new ProductModel("3", "Black Shoes", 99.99, R.drawable.dress3 4.9));
//            products.add(new ProductModel("4", "White Sneakers", 49.99, R.drawable.white_sneakers, 4.7));
        }

        // Hide progress bar and update RecyclerView
        progressBar.setVisibility(View.GONE);
        productListAdapter.notifyDataSetChanged();
    }
}