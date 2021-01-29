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

    @PropertyName("user_id")
    public String getId() {
        return id;
    }

    @PropertyName("user_id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }
}
