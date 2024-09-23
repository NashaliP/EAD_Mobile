package com.example.ead.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.ead.R;
import com.example.ead.adapter.OrderAdapter;
import com.example.ead.models.Order;

public class CancelledOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<Order> canceledOrders;

    public CancelledOrdersFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data for Canceled Orders
        canceledOrders = new ArrayList<>();
        canceledOrders.add(new Order("12348", "2023-09-23", "$120.50", "Canceled"));

        adapter = new OrderAdapter(canceledOrders);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
