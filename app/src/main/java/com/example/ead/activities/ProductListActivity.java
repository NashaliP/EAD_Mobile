package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ead.R;
import com.example.ead.adapter.ProductsListAdapter;
import com.example.ead.models.ProductModel;
import com.example.ead.network.ApiClient;
import com.example.ead.services.CategoryService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    TextView titleTxt;
    private RecyclerView productListRV;
    private ProductsListAdapter productAdapter;
    private List<ProductModel> productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

       // Initialize RecyclerView and TextView
        productListRV = findViewById(R.id.productListRV);
        titleTxt = findViewById(R.id.titleTxt);
        productListRV.setLayoutManager(new LinearLayoutManager(this));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 columns
        productListRV.setLayoutManager(gridLayoutManager);

        // Initialize the product list and adapter
        productModel = new ArrayList<>();
        productAdapter = new ProductsListAdapter(this, productModel);
        productListRV.setAdapter(productAdapter);

        // Get the selected category from the Intent
        String categoryName = getIntent().getStringExtra("categoryName");
        titleTxt.setText(categoryName);

        // Fetch products for the selected category
        fetchProductsByCategory(categoryName);

    }

    private void fetchProductsByCategory(String categoryName) {
        CategoryService categoryService = ApiClient.getRetrofitInstance().create(CategoryService.class);

        // Call the API to get products by category
        Call<List<ProductModel>> call = categoryService.getProductsByCategoryName(categoryName);
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productModel.clear(); // Clear existing products
                    productModel.addAll(response.body()); // Add new products
                    productAdapter.notifyDataSetChanged(); // Notify adapter of data changes
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                // Handle error
            }
        });
    }
}