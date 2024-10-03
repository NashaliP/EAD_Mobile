package com.example.ead.services;

import com.example.ead.models.OrderModel;
import com.example.ead.models.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {
    @POST("Order/createOrder")
    Call<OrderResponse> createOrder(@Body OrderModel order);
}
