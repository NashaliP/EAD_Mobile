package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.ead.R;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup radioGroupPayment;
    private Button btnMakePayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize views
        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        btnMakePayment = findViewById(R.id.btnMakePayment);

        // Set click listener for the Make Payment button
        btnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupPayment.getCheckedRadioButtonId(); // Get selected radio button ID
                String paymentMethod = ""; // Variable to hold payment method

                // Determine which payment method was selected
                if (selectedId == R.id.radioVisa) {
                    paymentMethod = "Visa";
                } else if (selectedId == R.id.radioMasterCard) {
                    paymentMethod = "MasterCard";
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