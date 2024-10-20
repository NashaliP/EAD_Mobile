package com.example.ead.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead.R;
import com.example.ead.adapter.OrderAdapter;
import com.example.ead.models.OrderModel;
import com.example.ead.models.OrderStatus;
import com.example.ead.network.ApiClient;
import com.example.ead.services.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<OrderModel> completedOrders;

    public CompletedOrdersFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch completed orders
        fetchAllOrders();

        return view;
    }

    private void fetchAllOrders() {
        OrderService orderService = ApiClient.getRetrofitInstance().create(OrderService.class);
        Call<List<OrderModel>> call = orderService.getAllOrders();

        call.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<OrderModel>> call, @NonNull Response<List<OrderModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderModel> allOrders = response.body();
                    filterCompletedOrders(allOrders);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<OrderModel>> call, @NonNull Throwable t) {
                // Handle error
            }
        });
    }

    private void filterCompletedOrders(List<OrderModel> allOrders) {
        completedOrders = new ArrayList<>();
        for (OrderModel order : allOrders) {
            if (order.status == OrderStatus.Delivered) {
                completedOrders.add(order);
            }
        }
        adapter = new OrderAdapter(completedOrders);
        recyclerView.setAdapter(adapter);
    }

}
