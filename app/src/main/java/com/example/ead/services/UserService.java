package com.example.ead.services;

import com.example.ead.models.User;
import com.example.ead.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("User/create") // Endpoint for creating a user
    Call<UserResponse> createUser(@Body User user);
}
