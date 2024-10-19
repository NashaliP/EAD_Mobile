package com.example.ead.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead.R;
import com.example.ead.adapter.ProductsListAdapter;
import com.example.ead.adapter.ShopProductAdapter;
import com.example.ead.models.CategoryModel;
import com.example.ead.models.ProductModel;
import com.example.ead.network.ApiClient;
import com.example.ead.services.CategoryService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingActivity extends AppCompatActivity {

    private RecyclerView featuredProductsRecyclerView;
    private ShopProductAdapter productAdapter;
    private List<ProductModel> productList;
    private Spinner spinnerCategories;
    private List<CategoryModel> categoryList = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private boolean hasSelectedCategory = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.shop); // Set default selected item

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.shop:
                        return true; // Current activity, do nothing

                    case R.id.explore:
                        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        // Set up the category spinner
        // Initialize Spinner
        spinnerCategories = findViewById(R.id.spinnerCategories);

        // Set up featured products RecyclerView
        featuredProductsRecyclerView = findViewById(R.id.featuredProductsRecyclerView);
        featuredProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Load dummy products into the RecyclerView
        loadDummyData();


        // Setup the adapter with an empty list (will populate later)
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(categoryAdapter);

        // Fetch categories from the web service
        fetchCategories();
//        categoryAdapter = new CategoryAdapter(this, categoryList);
//        categoriesRecyclerView.setAdapter(categoryAdapter);

        // Spinner item selection listener
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Check if this is the first selection
                if (hasSelectedCategory) {
                    String selectedCategory = (String) parent.getItemAtPosition(position);
                    // Create an Intent to start ProductListActivity
                    Intent intent = new Intent(ShoppingActivity.this, ProductListActivity.class);
                    intent.putExtra("categoryName", selectedCategory); // Pass the selected category name
                    startActivity(intent); // Start the new activity
                } else {
                    // First selection made, set the flag
                    hasSelectedCategory = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
            }
        });
    }

    // Populate static dummy data for products
    // Load dummy data for featured products
    private void loadDummyData() {
        productList = new ArrayList<>();

        // Adding dummy products to the list
        productList.add(new ProductModel("1", "Jacket", "https://firebasestorage.googleapis.com/v0/b/ead-vendora-30b06.appspot.com/o/products%2Ftestshirt.png?alt=media&token=b293727b-5771-42cf-a64e-ffe0d8e7340f", "Stylish Jacket", 59.99, 4.5f, "vendor1"));
        productList.add(new ProductModel("2", "T-shirt", "https://firebasestorage.googleapis.com/v0/b/ead-vendora-30b06.appspot.com/o/products%2Fdress3.png?alt=media&token=3751d132-d515-4256-acb7-041d4fbd21ba", "Cool T-shirt", 19.99, 4.0f, "vendor2"));
        productList.add(new ProductModel("3", "Skirt", "https://firebasestorage.googleapis.com/v0/b/ead-vendora-30b06.appspot.com/o/products%2Fdress2.png?alt=media&token=0dad68eb-8356-4c67-8bba-2c67600ff128", "Trendy Skirt", 29.99, 4.3f, "vendor3"));

        // Set up the adapter and attach it to the RecyclerView
        productAdapter = new ShopProductAdapter(this, productList);
        featuredProductsRecyclerView.setAdapter(productAdapter);
    }

    // Fetch categories dynamically from the API
    private void fetchCategories() {

        // Create an instance of category service
        CategoryService categoryService = ApiClient.getRetrofitInstance().create(CategoryService.class);

        // Call the API to get categories
        Call<List<CategoryModel>> call = categoryService.getAllCategories();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();
                    Log.d("CategoryFetch", "Fetched " + categoryList.size() + " categories.");
                    updateCategorySpinner();
                } else {
                    // Log the response code and message
                    Log.e("API Error", "Error Code: " + response.code() + " Message: " + response.message());
                    // If response body is not null, log it
                    if (response.errorBody() != null) {
                        try {
                            Log.e("API Error Body", response.errorBody().string());
                        } catch (IOException e) {
                            Log.e("API Error", "Error reading error body", e);
                        }
                    }
                    Toast.makeText(ShoppingActivity.this, "Failed to retrieve categories", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Toast.makeText(ShoppingActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCategorySpinner() {
        // Extract category names from the categoryList and populate the spinner
        List<String> categoryNames = new ArrayList<>();
        for (CategoryModel category : categoryList) {
            if (category.isActive()) { // Only include active categories
                categoryNames.add(category.getCategoryName());
            }
        }

        // Update the adapter
        categoryAdapter.clear();
        categoryAdapter.addAll(categoryNames);
        categoryAdapter.notifyDataSetChanged();
    }


}