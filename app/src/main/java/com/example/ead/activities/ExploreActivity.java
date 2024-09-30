package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.ead.R;
import com.example.ead.adapter.ProductsListAdapter;
import com.example.ead.models.ProductModel;
import com.example.ead.network.ApiClient;
import com.example.ead.services.ProductService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreActivity extends AppCompatActivity {
    private RecyclerView exploreRecyclerView;
    private ProductsListAdapter productsListAdapter;
    private List<ProductModel> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // Initialize RecyclerView and set GridLayoutManager
        exploreRecyclerView = findViewById(R.id.ExploreRecyclerView);
        productList = new ArrayList<>();
        productsListAdapter = new ProductsListAdapter(this, productList);

        // Set up the RecyclerView to use a GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 columns
        exploreRecyclerView.setLayoutManager(gridLayoutManager);
        exploreRecyclerView.setAdapter(productsListAdapter);

        fetchAllProducts();
    }
    private void fetchAllProducts() {
        ProductService productService = ApiClient.getRetrofitInstance().create(ProductService.class);

        Call<List<ProductModel>> call = productService.getAllProducts();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());
                    productsListAdapter.notifyDataSetChanged();
                } else {
                    Log.e("ExploreActivity", "Response not successful or body is null");
                }
            }
            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Log.e("ExploreActivity", "Error fetching products", t);
            }
        });
    }
}