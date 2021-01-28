package com.example.seriestracker.helper;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    private FirebaseDatabase database;

    private FirebaseHelper(FirebaseDatabase database) {
        this.database = database;
    }

}
