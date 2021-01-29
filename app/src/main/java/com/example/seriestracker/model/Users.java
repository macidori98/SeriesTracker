package com.example.seriestracker.model;

import com.google.firebase.database.PropertyName;

public class Users {

    @PropertyName("user_id")
    private String id;

    @PropertyName("name")
    private String name;

    public Users(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
