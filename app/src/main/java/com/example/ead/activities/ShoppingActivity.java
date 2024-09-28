package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ead.R;
import com.example.ead.adapter.CategoryAdapter;
import com.example.ead.adapter.FavoriteProductsAdapter;
import com.example.ead.models.CategoryModel;
import com.example.ead.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ShoppingActivity extends AppCompatActivity {

    //featured products
    private RecyclerView featuredProductsRecyclerView;
    private FavoriteProductsAdapter productAdapter;
    private List<ProductModel> productList;

    //categories
    private RecyclerView categoriesRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        // Set up featured products RecyclerView
        featuredProductsRecyclerView = findViewById(R.id.featuredProductsRecyclerView);
        featuredProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Fetch products (simulated or from API)
        productList = fetchProducts();

        // Set Adapter
        productAdapter = new FavoriteProductsAdapter(this,productList);
        featuredProductsRecyclerView.setAdapter(productAdapter);

        // Set up categories RecyclerView (4 columns grid)
        categoriesRecyclerView = findViewById(R.id.categoryRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4); // 4 columns
        categoriesRecyclerView.setLayoutManager(gridLayoutManager);

        categoryList = fetchCategories();
        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoriesRecyclerView.setAdapter(categoryAdapter);
    }

    // Fetch products from some data source
    private List<ProductModel> fetchProducts() {
        List<ProductModel> products = new ArrayList<>();

        products.add(new ProductModel("1","Product 1", R.drawable.image1, 10.99, 4.5f));
        products.add(new ProductModel("2","Product 2", R.drawable.image2, 15.49, 3.9f));
        products.add(new ProductModel("3","Product 3", R.drawable.image3, 7.99, 4.7f));

        return products;
    }

    // Simulate fetching categories
    private List<CategoryModel> fetchCategories() {
        List<CategoryModel> categories = new ArrayList<>();

        // Adding static categories with id, name, and icon
        categories.add(new CategoryModel("1", "Dresses", R.drawable.dress));       // Three arguments: id, name, image
        categories.add(new CategoryModel("2", "Shirts", R.drawable.shirt));
        categories.add(new CategoryModel("3", "T-shirt", R.drawable.tshirt));
        categories.add(new CategoryModel("4", "Skirt", R.drawable.skirt));
        categories.add(new CategoryModel("5", "Jeans", R.drawable.jeans));
        categories.add(new CategoryModel("6", "Jackets", R.drawable.jacket));
        categories.add(new CategoryModel("7", "Swimsuits", R.drawable.swimsuit));
        categories.add(new CategoryModel("8", "Undies", R.drawable.underwear));

        return categories;
    }
}