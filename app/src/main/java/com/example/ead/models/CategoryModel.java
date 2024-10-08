package com.example.ead.models;

public class CategoryModel {
    private String id;
    private String categoryName;
    private boolean isActive;

    public CategoryModel(String id, String categoryName, boolean isActive) {
        this.id = id;
        this.categoryName = categoryName;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public boolean isActive() {
        return isActive;
    }
}
