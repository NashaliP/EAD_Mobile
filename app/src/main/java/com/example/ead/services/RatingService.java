package com.example.ead.services;

import com.example.ead.models.AverageRatingResponse;
import com.example.ead.models.RatingModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RatingService {
    @POST("rating/AddRating")
    Call<Void> addRating(@Body RatingModel ratingModel);

    @GET("rating/getAverageRating/{vendorId}")
    Call<AverageRatingResponse> getAverageRating(@Path("vendorId") String vendorId);
}
