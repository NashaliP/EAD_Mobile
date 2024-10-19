package com.example.ead.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.ead.R;
import com.example.ead.models.AverageRatingResponse;
import com.example.ead.models.ProductModel;
import com.example.ead.network.ApiClient;
import com.example.ead.persistence.AppDatabase;
import com.example.ead.persistence.CartDao;
import com.example.ead.persistence.CartItem;
import com.example.ead.services.CategoryService;
import com.example.ead.services.ProductService;
import com.example.ead.services.RatingService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    private TextView productTitleTxt, productPriceTxt, productDescriptionTxt,productRatingTxt;

    private EditText productQty;
    private Button addToCartButton;
    private ImageView productImageView;

    private AppDatabase database;
    private CartDao cartDao;

    private ImageButton cartButton;


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
        addToCartButton = findViewById(R.id.idAddToCartBtn);
        cartButton = findViewById(R.id.idIBCart);
        productRatingTxt = findViewById(R.id.idTVProductRating);

        // Initialize Room Database
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app_database").build();
        cartDao = database.cartDao();

        // Get the product ID from the intent
        String productId = getIntent().getStringExtra("productId");

        // Fetch product details using the product ID
        if (productId != null) {
            fetchProductDetails(productId);
            fetchAverageRating(productId);
        } else {
            Toast.makeText(this, "Product ID not found", Toast.LENGTH_SHORT).show();
        }

        // Handle Add to Cart button click
        addToCartButton.setOnClickListener(v -> {
            String qtyString = productQty.getText().toString();
            if (qtyString.isEmpty()) {
                Toast.makeText(ProductActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                return;
            }
            int quantity = Integer.parseInt(qtyString);

            CartItem cartItem = new CartItem(
                    productTitleTxt.getText().toString(),
                    Double.parseDouble(productPriceTxt.getText().toString().replace("$", "")),
                    quantity

            );

            new Thread(() -> {
                cartDao.insertCartItem(cartItem);
                runOnUiThread(() -> Toast.makeText(ProductActivity.this, "Item added to cart!", Toast.LENGTH_SHORT).show());
            }).start();
        });

        // Navigate to CartActivity when the cart button is clicked
        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }
    private void fetchProductDetails(String productId) {

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
                    fetchAverageRating(product.getVendorId());

                } else {
                    Toast.makeText(ProductActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Error fetching product details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAverageRating(String vendorId) {
        RatingService ratingService = ApiClient.getRetrofitInstance().create(RatingService.class);

        Call<AverageRatingResponse> call = ratingService.getAverageRating(vendorId);
        call.enqueue(new Callback<AverageRatingResponse>() {
            @Override
            public void onResponse(Call<AverageRatingResponse> call, Response<AverageRatingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    double averageRating = response.body().getAverageRating();
                    productRatingTxt.setText(String.valueOf(averageRating)); // Update your UI here
                } else {
                    Log.e("API Response", "Error: " + response.errorBody()); // Log the error response
                    Toast.makeText(ProductActivity.this, "No ratings found for this vendor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AverageRatingResponse> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Error fetching average rating: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}