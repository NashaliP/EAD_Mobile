package com.example.ead.models;

public class ProductModel {

    private String id;
    private String name;
    private String imgurl;
    private String description;
    private double price;
    private double rating;
    private String vendorId;

    public ProductModel(String id,String name, String imgurl, String description, double price, float rating,String vendorId) {
        this.id = id;
        this.name = name;
        this.imgurl = imgurl;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.vendorId = vendorId;
    }

    // Getters
    public String getId() {
        return id;
    }

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

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }
    public String getVendorId() { // Add a getter for vendorId
        return vendorId;
    }
}
