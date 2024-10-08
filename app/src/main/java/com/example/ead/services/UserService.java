package com.example.ead.services;

import com.example.ead.models.User;
import com.example.ead.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("User/signup")
    Call<UserResponse> signupUser(@Body User user);

    // Login endpoint
    @POST("User/login")
    Call<UserResponse> loginUser(@Body User user);

    // Deactivate account endpoint
    @POST("User/{userId}/deactivate")
    Call<Void> deactivateUser(@Path("userId") String userId);
}
