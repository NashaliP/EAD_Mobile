package com.example.ead.services;

import com.example.ead.models.OrderModel;
import com.example.ead.models.OrderResponse;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    //customer places order
    @POST("Order/createOrder")
    Call<OrderResponse> createOrder(@Body OrderModel order);

    //fetch all orders
    @GET("Order/getAllOrders")
    Call<List<OrderModel>> getAllOrders();

    //customer requests order cancellation
    @POST("customer-notifications/cancel-order/{orderId}")
    Call<ResponseBody> cancelOrder(@Path("orderId") String orderId);
}
