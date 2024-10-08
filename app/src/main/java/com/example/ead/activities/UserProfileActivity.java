package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ead.R;
import com.example.ead.network.ApiClient;
import com.example.ead.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView imgOrderHistory;
    private Button btnDeactivateProfile;
    private String userId; // Assume this is set to the current user's ID after login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imgOrderHistory = findViewById(R.id.imgOrderHistory);

        // Set click listener for Order History
        imgOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        btnDeactivateProfile = findViewById(R.id.btnDeactivateProfile);

        btnDeactivateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deactivateUserAccount();
            }
        });

    }
    private void deactivateUserAccount() {
        UserService userService = ApiClient.getRetrofitInstance().create(UserService.class);
        Call<Void> call = userService.deactivateUser(userId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserProfileActivity.this, "Account deactivated successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, redirect to login or main activity
                    Intent intent = new Intent(UserProfileActivity.this, LoginPage.class);
                    startActivity(intent);
                    finish(); // Close UserProfileActivity
                } else {
                    Toast.makeText(UserProfileActivity.this, "Failed to deactivate account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}