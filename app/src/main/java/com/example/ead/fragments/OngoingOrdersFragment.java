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

public class OngoingOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<Order> ongoingOrders;

    public OngoingOrdersFragment() {
        // Required empty constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data for Ongoing Orders
        ongoingOrders = new ArrayList<>();
        ongoingOrders.add(new Order("12345", "2023-09-20", "$50.00", "Processing"));
        ongoingOrders.add(new Order("12346", "2023-09-21", "$99.99", "Shipped"));

        adapter = new OrderAdapter(ongoingOrders);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
