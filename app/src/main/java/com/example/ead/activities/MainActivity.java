package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ead.R;
import com.example.ead.activities.ShoppingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.shop);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.shop:
                        startActivity(new Intent(getApplicationContext(), ShoppingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.explore:
//                        startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
////                    case R.id.favorites:
////                        startActivity(new Intent(getApplicationContext(),FavoritesActivity.class));
////                        overridePendingTransition(0,0);
////                        return true;
//                    case R.id.profile:
//                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
                }
                return false;
            }
        });
    }

}