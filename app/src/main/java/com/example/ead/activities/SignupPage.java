package com.example.ead.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ead.R;
import com.example.ead.models.User;
import com.example.ead.models.UserResponse;
import com.example.ead.network.ApiClient;
import com.example.ead.services.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupPage extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnRegisterSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        TextView tvLogin = findViewById(R.id.tvLoginLink);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginActivity = new Intent(SignupPage.this, LoginPage.class);
                startActivity(loginActivity);
            }
        });

        btnRegisterSignup = findViewById(R.id.btnRegisterSignup);

        // Handles the signup button click
        btnRegisterSignup.setOnClickListener(new View.OnClickListener() {
            // Validates user input and initiates signup process if valid.
            @Override
            public void onClick(View view) {
                String textEmail = etEmail.getText().toString();
                String textPassword = etPassword.getText().toString();

                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(SignupPage.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignupPage.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(SignupPage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                } else if (textPassword.length() < 6) {
                    Toast.makeText(SignupPage.this, "Password is too short!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                } else {
                    User user = new User(textEmail, textPassword);

                    UserService userService = ApiClient.getRetrofitInstance().create(UserService.class);
                    Call<UserResponse> call = userService.signupUser(user);

                    call.enqueue(new Callback<UserResponse>() {
                        // Handles the response from the signup API call
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful()) {
                                UserResponse userResponse = response.body();
                                String userStatus = userResponse.getUserStatus();

                                // Show success message and navigate to login page
                                Toast.makeText(SignupPage.this, "Signup successful! Awaiting approval.", Toast.LENGTH_SHORT).show();
                                Intent loginActivity = new Intent(SignupPage.this, LoginPage.class);
                                startActivity(loginActivity);
                                finish(); // Close SignupPage
                            }else {
                                try {
                                    String errorBody = response.errorBody().string();
                                    Log.e("Signup Error", errorBody);
                                    Toast.makeText(SignupPage.this, "Signup failed", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        // Handles failure of the API call
                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Toast.makeText(SignupPage.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
