package com.example.ead.models;

public class UserResponse {
    private String id;
    private String email;

    private String role;
    private String userStatus;
    private boolean isActive;

    // Getters
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

//    public String getPassword() {
//        return password; // consider removing
//    }

    public String getRole() {
        return role;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public boolean isActive() {
        return isActive;
    }
}