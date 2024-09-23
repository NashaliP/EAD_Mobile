package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ead.R;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ead.adapter.OrdersPagerAdapter;
import com.example.ead.models.Order;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.example.ead.adapter.OrderAdapter;

public class OrderHistoryActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private OrdersPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Set up ViewPager Adapter
        pagerAdapter = new OrdersPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Attach TabLayout to ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Ongoing");
                    break;
                case 1:
                    tab.setText("Completed");
                    break;
                case 2:
                    tab.setText("Canceled");
                    break;
            }
        }).attach();
    }
}