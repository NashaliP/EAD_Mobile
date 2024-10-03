package com.example.ead.models;

import java.util.List;

public class OrderModel {
    public List<OrderItemModel> items;
    public String shippingAddress;
    public String paymentMethod;
    public double totalAmount;

    public OrderModel(List<OrderItemModel> items, String shippingAddress, String paymentMethod, double totalAmount) {
        this.items = items;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }
}

