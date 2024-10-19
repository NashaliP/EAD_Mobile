package com.example.ead.services;

import com.example.ead.models.EmailRequest;
import com.example.ead.models.User;
import com.example.ead.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    //user signup endpoint
    @POST("User/signup")
    Call<UserResponse> signupUser(@Body User user);

    // Login endpoint
    @POST("User/login")
    Call<UserResponse> loginUser(@Body User user);

    // Deactivate account endpoint
    @PUT("User/deactivateUser")
//    Call<Void> deactivateUser(@Body String email);
    Call<Void> deactivateUser(@Body EmailRequest emailRequest);
}
