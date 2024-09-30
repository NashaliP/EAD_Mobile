package com.example.ead.services;

import com.example.ead.models.CategoryModel;
import com.example.ead.models.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("Category/getAllCategories")
    Call<List<CategoryModel>> getAllCategories();

    @GET("Product/getProductByCategory/{categoryName}")
    Call<List<ProductModel>> getProductsByCategoryName(@Path("categoryName") String categoryName);
}
