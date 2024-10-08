package com.example.ead.models;

public class CartItemModel {

    private String productId;  // Reference to the actual product ID
    private int quantity;      // Quantity of the product in the cart

    public CartItemModel(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
