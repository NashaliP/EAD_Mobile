package com.example.ead.models;

public class CategoryModel {
    private String id;  // Unique ID for each category
    private String categoryName;
    private int imageResource;

    public CategoryModel(String id, String categoryName, int imageResource) {
        this.id = id;
        this.categoryName = categoryName;
        this.imageResource = imageResource;
    }

    // Getter and setter for ID
    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getImageResource() {
        return imageResource;
    }
}
