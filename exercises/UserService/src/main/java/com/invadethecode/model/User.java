package com.invadethecode.model;

public class User {
    public User(String firstName) {
        this.firstName = firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    private String firstName;
}
