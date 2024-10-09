package com.example.ead.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class OrderModel implements Serializable {
    public List<OrderItemModel> items;
    public String shippingAddress;
    public String paymentMethod;
    public double totalAmount;
    public String orderDate;

    public OrderStatus status;
    public String orderId;

    public OrderModel(List<OrderItemModel> items, String shippingAddress, String paymentMethod, double totalAmount, String orderDate, OrderStatus status,String orderId) {
        this.items = items;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
        this.orderId = orderId;
    }

    // Static method to generate an order ID
    public static String generateOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 6);
    }
}

