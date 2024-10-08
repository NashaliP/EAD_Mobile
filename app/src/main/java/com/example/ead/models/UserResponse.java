package com.example.ead.models;

public class UserResponse {
    private String id;
    private String email;
    private String role;
    private String userStatus;
    private boolean isActive;

    // Returns the user's ID
    public String getId() {
        return id;
    }

    // Returns the user's email
    public String getEmail() {
        return email;
    }

    // Returns the user's role
    public String getRole() {
        return role;
    }

    // Returns the user's status
    public String getUserStatus() {
        return userStatus;
    }

    // Returns whether the user is active
    public boolean isActive() {
        return isActive;
    }
}
