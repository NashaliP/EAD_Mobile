package com.example.ead.models;

import java.io.Serializable;

public class OrderItemModel implements Serializable {
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

    // Method to calculate the total price for this item
    public double getTotalPrice() {
        return this.quantity * this.price;
    }
}
