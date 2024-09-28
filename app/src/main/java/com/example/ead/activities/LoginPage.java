package com.example.ead.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    private EditText etLoginEmail, etLoginPw;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPw = findViewById(R.id.etLoginPw);
        buttonLogin = findViewById(R.id.btnLogin);

        // Switch to signup activity
        TextView tvRegister = findViewById(R.id.tvRegisterLink);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(LoginPage.this, SignupPage.class);
                startActivity(registerActivity);
            }
        });

        // Handle login button click
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = etLoginEmail.getText().toString();
                String textPassword = etLoginPw.getText().toString();

                // Basic field validations
                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(LoginPage.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    etLoginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(LoginPage.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                    etLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(LoginPage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    etLoginPw.requestFocus();
                } else {
                    // Call login API to check approval status
                    User user = new User(textEmail, textPassword);

                    UserService userService = ApiClient.getRetrofitInstance().create(UserService.class);
                    Call<UserResponse> call = userService.loginUser(user); // Assume this endpoint exists

                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful()) {
                                UserResponse userResponse = response.body();
                                String userStatus = userResponse.getUserStatus();

                                // Check user status and respond accordingly
                                switch (userStatus) {
                                    case "Pending":
                                        Toast.makeText(LoginPage.this, "Your account is awaiting approval.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "Rejected":
                                        Toast.makeText(LoginPage.this, "Your account was rejected. Please contact support.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "Approved":
                                        Toast.makeText(LoginPage.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                        Intent profileActivity = new Intent(LoginPage.this, ShoppingActivity.class);
                                        startActivity(profileActivity);
                                        finish(); // Close LoginPage
                                        break;
                                    default:
                                        Toast.makeText(LoginPage.this, "Unknown status. Please contact support.", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            } else {
                                // Handle unsuccessful response (invalid credentials, etc.)
                                Toast.makeText(LoginPage.this, "Account awaiting approval.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Toast.makeText(LoginPage.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
