package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.ead.R;


public class addAddressActivity extends AppCompatActivity {

    private EditText editAddressLine1, editAddressLine2, editCity;
    private Button btnAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        editAddressLine1 = findViewById(R.id.editAddressLine1);
        editAddressLine2 = findViewById(R.id.editAddressLine2);
        editCity = findViewById(R.id.editCity);
        btnAddAddress = findViewById(R.id.btnAddAddress);


        btnAddAddress.setOnClickListener(view -> {
            String address = editAddressLine1.getText().toString() + ", "
                    + editAddressLine2.getText().toString() + ", "
                    + editCity.getText().toString();
            // Return address to CartActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("address", address);
            setResult(RESULT_OK, resultIntent);
            finish(); // Close this activity
        });


    }
}