package com.example.ead.models;

public class OrderResponse {
    public String id;
    public String message;  // Depending on what your API returns

    public OrderResponse(String id, String message) {
        this.id = id;
        this.message = message;
    }
}
