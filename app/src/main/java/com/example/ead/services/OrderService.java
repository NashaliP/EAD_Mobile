package com.example.ead.services;

import com.example.ead.models.OrderModel;
import com.example.ead.models.OrderResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OrderService {
    //customer places order endpoint
    @POST("Order/createOrder")
    Call<OrderResponse> createOrder(@Body OrderModel order);

    //fetch all orders
    @GET("Order/getAllOrders")
    Call<List<OrderModel>> getAllOrders();
}
