package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

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
    private List<ProductModel> filteredProductList;  // To store filtered products
    private SearchView idSVExplore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // Initialize RecyclerView and set GridLayoutManager
        exploreRecyclerView = findViewById(R.id.ExploreRecyclerView);
        productList = new ArrayList<>();
        filteredProductList = new ArrayList<>();  // Initialize filtered list
        productsListAdapter = new ProductsListAdapter(this, filteredProductList);

        // Set up the RecyclerView to use a GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 columns
        exploreRecyclerView.setLayoutManager(gridLayoutManager);
        exploreRecyclerView.setAdapter(productsListAdapter);

        // Initialize SearchView
        idSVExplore = findViewById(R.id.idSVExplore);

        // Fetch all products from the API
        fetchAllProducts();

        // Set up search functionality
        setupSearchView();
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
                    filteredProductList.addAll(productList);
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

    private void setupSearchView() {
        idSVExplore.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // No action needed on submit, but can handle if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter products as the user types
                filterProducts(newText);
                return true;
            }
        });
    }

    private void filterProducts(String query) {
        // Clear the filtered list first
        filteredProductList.clear();

        if (query.isEmpty()) {
            // If query is empty, show all products
            filteredProductList.addAll(productList);
        } else {
            // Filter based on the query
            for (ProductModel product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredProductList.add(product);
                }
            }
        }

        // Notify the adapter about the changes
        productsListAdapter.notifyDataSetChanged();
    }
}