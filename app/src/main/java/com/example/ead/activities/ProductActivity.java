package com.example.ead.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ead.R;
import com.example.ead.models.ProductModel;
import com.example.ead.network.ApiClient;
import com.example.ead.services.CategoryService;
import com.example.ead.services.ProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    private TextView productTitleTxt, productPriceTxt, productDescriptionTxt,productQty;
    private ImageView productImageView;
//    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Initialize the views
        productTitleTxt = findViewById(R.id.idTVProductName);
        productPriceTxt = findViewById(R.id.idTVProductPrice);
        productDescriptionTxt = findViewById(R.id.idTVProductDescription);
        productImageView = findViewById(R.id.idIVProductImage);
        productQty = findViewById(R.id.IDTIQuantity);

        // Get the product ID from the intent
        String productId = getIntent().getStringExtra("productId");

        // Fetch product details using the product ID
        if (productId != null) {
            fetchProductDetails(productId);
        }// Fetch product details using the product ID
        if (productId != null) {
            fetchProductDetails(productId);
        } else {
            Toast.makeText(this, "Product ID not found", Toast.LENGTH_SHORT).show();
        }
    }
    private void fetchProductDetails(String productId) {
//        // Show loading
//        progressBar.setVisibility(View.VISIBLE);

        ProductService productService = ApiClient.getRetrofitInstance().create(ProductService.class);

        Call<ProductModel> call = productService.getProductById(productId);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductModel product = response.body();

                    // Set product data into views
                    productTitleTxt.setText(product.getName());
                    productPriceTxt.setText("$" + product.getPrice());
                    productDescriptionTxt.setText(product.getDescription());

                    // Load the product image
                    Glide.with(ProductActivity.this).load(product.getImgurl()).into(productImageView);
                } else {
                    Toast.makeText(ProductActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
                }
            }

//                // Hide loading
//                progressBar.setVisibility(View.GONE);


            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Error fetching product details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}