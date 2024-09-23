package com.example.ead.models;

public class Order {
    private String orderId;
    private String orderDate;
    private String totalAmount;
    private String status;

    public Order(String orderId, String orderDate, String totalAmount, String status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }
}
