package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.ead.R;
import com.example.ead.models.RatingModel;
import com.example.ead.network.ApiClient;
import com.example.ead.services.RatingService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText etFeedback;
    private Button btnSubmitFeedback;
    private String vendorId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar = findViewById(R.id.ratingBar);
        etFeedback = findViewById(R.id.etFeedback);
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);

        // Get vendorId from Intent
        vendorId = getIntent().getStringExtra("vendorId");

        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        int rating = (int) ratingBar.getRating();
        String feedback = etFeedback.getText().toString().trim();

        // Validate input
        if (rating < 1 || rating > 5) {
            Toast.makeText(this, "Please select a rating between 1 and 5", Toast.LENGTH_SHORT).show();
            return;
        }

        RatingModel ratingModel = new RatingModel(vendorId, rating, feedback.isEmpty() ? null : feedback);

        RatingService ratingService = ApiClient.getRetrofitInstance().create(RatingService.class);
        Call<Void> call = ratingService.addRating(ratingModel);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FeedbackActivity.this, "Rating submitted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity or navigate back
                } else {
                    Toast.makeText(FeedbackActivity.this, "Failed to submit rating", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FeedbackActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}