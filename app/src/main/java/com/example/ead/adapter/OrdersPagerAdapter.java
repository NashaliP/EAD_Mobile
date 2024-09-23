package com.example.ead.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ead.fragments.CancelledOrdersFragment;
import com.example.ead.fragments.CompletedOrdersFragment;
import com.example.ead.fragments.OngoingOrdersFragment;

public class OrdersPagerAdapter extends FragmentStateAdapter {

    public OrdersPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new CompletedOrdersFragment();
            case 2:
                return new CancelledOrdersFragment();
            default:
                return new OngoingOrdersFragment(); // Default to ongoing
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}
