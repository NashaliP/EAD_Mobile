package com.example.ead.models;

public class ProductModel {

    private String id;
    private String title;
//    private String imageUrl;
    private int imageResource;
    private double price;
    private float rating;

    public ProductModel(String id,String title, int imageResource, double price, float rating) {
        this.id = id;
        this.title = title;
//        this.imageUrl = imageUrl;
        this.imageResource = imageResource;
        this.price = price;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }
    // Getters
    public String getTitle() {
        return title;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }

    public int getImageResource() {
        return imageResource;
    }

    public double getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }
}
