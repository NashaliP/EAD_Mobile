package com.example.ead.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ead.R;
import com.example.ead.models.User;
import com.example.ead.network.ApiClient;
import com.example.ead.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private UserService userService;
    private String userEmail;
    private ImageView imgOrderHistory,imgNotifications;
    private Button btnDeactivateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize Retrofit service
        userService = ApiClient.getRetrofitInstance().create(UserService.class);

        // Retrieve user email
        userEmail = getUserEmail();

        btnDeactivateProfile = findViewById(R.id.btnDeactivateProfile);

        // Handle button click to deactivate account
        btnDeactivateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog(); // Show confirmation dialog
            }
        });

        imgOrderHistory = findViewById(R.id.imgOrderHistory);
        imgNotifications=findViewById(R.id.imgNotifications);

        // Set click listener for Order History
        imgOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        imgNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });

    }
    private String getUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null); // Retrieve user email
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deactivation");
        builder.setMessage("Are you sure you want to deactivate your account? This action cannot be undone.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deactivateAccount(); // Call the deactivate function
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


            private void deactivateAccount() {
                String email = getUserEmail();
                Call<Void> call = userService.deactivateUser(email);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(UserProfileActivity.this, "Account deactivated successfully!", Toast.LENGTH_SHORT).show();
                            // Navigate back to LoginPage
                            Intent intent = new Intent(UserProfileActivity.this, LoginPage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear back stack
                            startActivity(intent);
                            finish(); // Close UserProfileActivity
                        } else {
                            Toast.makeText(UserProfileActivity.this, "Failed to deactivate account. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}