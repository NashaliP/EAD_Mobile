package com.example.ead.services;

import com.example.ead.models.NotificationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationService {
    @GET("customer-notifications/unread")
    Call<List<NotificationModel>> getCustomerNotifications();
}
