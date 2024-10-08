package com.example.ead.models;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    public List<OrderItemModel> items;
    public String shippingAddress;
    public String paymentMethod;
    public double totalAmount;
    public String orderDate;
    public OrderStatus status;

    public OrderModel(List<OrderItemModel> items, String shippingAddress, String paymentMethod, double totalAmount, String orderDate, OrderStatus status) {
        this.items = items;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }
}

