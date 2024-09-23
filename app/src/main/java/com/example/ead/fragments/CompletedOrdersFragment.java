package com.example.ead.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead.R;
import com.example.ead.adapter.OrderAdapter;
import com.example.ead.models.Order;

import java.util.ArrayList;
import java.util.List;

public class CompletedOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<Order> completedOrders;

    public CompletedOrdersFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data for Completed Orders
        completedOrders = new ArrayList<>();
        completedOrders.add(new Order("12347", "2023-09-22", "$75.50", "Delivered"));

        adapter = new OrderAdapter(completedOrders);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
