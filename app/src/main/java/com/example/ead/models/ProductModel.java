package com.example.ead.models;

public class ProductModel {

    private String id;
    private String name;
    private String imgurl;
    private String description;
    private double price;
    private float rating;

    public ProductModel(String id,String name, String imgurl, String description, double price, float rating) {
        this.id = id;
        this.name = name;
        this.imgurl = imgurl;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }
    // Getters
    public String getName() {
        return name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }
}
