package com.example.ead.models;

public class RatingModel {

    private String vendorId;
    private int rating;
    private String review;

    public RatingModel(String vendorId, int rating, String review) {
        this.vendorId = vendorId;
        this.rating = rating;
        this.review = review;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
