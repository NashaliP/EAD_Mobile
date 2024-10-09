package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ead.R;

public class PaymentActivity extends AppCompatActivity {

    private RadioButton radioMastercard, radioVisa, radioAmex, radioPaypal;
    private LinearLayout linearMastercard, linearVisa, linearAmex, linearPaypal;
    private Button btnMakePayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize views

        // Initialize RadioButtons
        radioMastercard = findViewById(R.id.radioMastercard);
        radioVisa = findViewById(R.id.radioVisa);
        radioAmex = findViewById(R.id.radioAmex);
        radioPaypal = findViewById(R.id.radioPaypal);

        // Initialize LinearLayouts
        linearMastercard = findViewById(R.id.linearMastercard);
        linearVisa = findViewById(R.id.linearVisa);
        linearAmex = findViewById(R.id.linearAmex);
        linearPaypal = findViewById(R.id.linearPaypal);
        btnMakePayment = findViewById(R.id.btnMakePayment);

        // Set click listeners for each LinearLayout to check the corresponding RadioButton
        linearMastercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioMastercard.setChecked(true);
            }
        });

        linearVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioVisa.setChecked(true);
            }
        });

        linearAmex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAmex.setChecked(true);
            }
        });

        linearPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioPaypal.setChecked(true);
            }
        });

        // Set click listener for the Make Payment button
        btnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = -1; // Initialize selectedId

                // Determine which payment method was selected
                if (radioMastercard.isChecked()) {
                    selectedId = R.id.radioMastercard;
                } else if (radioVisa.isChecked()) {
                    selectedId = R.id.radioVisa;
                } else if (radioAmex.isChecked()) {
                    selectedId = R.id.radioAmex;
                } else if (radioPaypal.isChecked()) {
                    selectedId = R.id.radioPaypal;
                }

                String paymentMethod = ""; // Variable to hold payment method

                // Check which RadioButton is checked and assign the appropriate payment method
                if (selectedId == R.id.radioMastercard) {
                    paymentMethod = "Mastercard";
                } else if (selectedId == R.id.radioVisa) {
                    paymentMethod = "Visa";
                } else if (selectedId == R.id.radioAmex) {
                    paymentMethod = "Amex";
                } else if (selectedId == R.id.radioPaypal) {
                    paymentMethod = "Paypal";
                }

                // Check if a payment method was selected
                if (selectedId == -1) {
                    Toast.makeText(PaymentActivity.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Return selected payment method to CartActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("paymentMethod", paymentMethod);
                setResult(RESULT_OK, resultIntent);
                finish(); // Close this activity
            }
        });
    }
}