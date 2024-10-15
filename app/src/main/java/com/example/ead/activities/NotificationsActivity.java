package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ead.R;
import com.example.ead.adapter.NotificationsAdapter;
import com.example.ead.models.NotificationModel;
import com.example.ead.network.ApiClient;
import com.example.ead.services.NotificationService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private NotificationsAdapter notificationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchNotifications();
    }

    private void fetchNotifications() {
        NotificationService notificationService = ApiClient.getRetrofitInstance().create(NotificationService.class);
        Call<List<NotificationModel>> call = notificationService.getCustomerNotifications();

        call.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NotificationModel> notifications = response.body();
                    notificationsAdapter = new NotificationsAdapter(notifications);
                    notificationsRecyclerView.setAdapter(notificationsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                // Handle failure (e.g., show a toast or log the error)
            }
        });
    }
}