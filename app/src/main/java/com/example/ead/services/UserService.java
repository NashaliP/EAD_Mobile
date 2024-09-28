package com.example.ead.services;

import com.example.ead.models.User;
import com.example.ead.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("User/signup")
    Call<UserResponse> signupUser(@Body User user);

    // Login endpoint
    @POST("User/login")
    Call<UserResponse> loginUser(@Body User user);
}
