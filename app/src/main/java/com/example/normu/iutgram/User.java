package com.example.normu.iutgram;

public class User {
    public String username;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
