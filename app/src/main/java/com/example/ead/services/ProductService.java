package com.example.ead.services;

import com.example.ead.models.ProductModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    //get product by id
    @GET("Product/{productId}")
    Call<ProductModel> getProductById(@Path("productId") String productId);

    //get all products
    @GET("Product/getAllProducts")
    Call<List<ProductModel>> getAllProducts();
}
