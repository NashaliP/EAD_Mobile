package com.example.ead.services;

import com.example.ead.models.RatingModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RatingService {
    @POST("rating/AddRating")
    Call<Void> addRating(@Body RatingModel ratingModel);
}
