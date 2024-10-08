package com.example.ead.models;

public class OrderItemModel {
    public String productName;
    public String vendorId;
    public int quantity;
    public double price;

    public OrderItemModel(String productName, String vendorId, int quantity, double price) {
        this.productName = productName;
        this.vendorId = vendorId;
        this.quantity = quantity;
        this.price = price;
    }
}
