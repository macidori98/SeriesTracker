package com.example.seriestracker.helper;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.example.seriestracker.R;
import com.example.seriestracker.model.Users;
import com.example.seriestracker.register.IRegisterPresenter;
import com.example.seriestracker.utils.GlobalValues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FirebaseHelper {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference;
    private static FirebaseHelper databaseInstance;

    public static FirebaseHelper getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new FirebaseHelper();
        }

        return databaseInstance;
    }

    public void checkIfUserAlreadyExists(IRegisterPresenter presenter, String name) {
        databaseReference = database.getReference(GlobalValues.USERS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exists = false;

                for (DataSnapshot snap: snapshot.getChildren()) {
                    String userName = Objects.requireNonNull(snap.child(GlobalValues.NAME).getValue()).toString();

                    if (userName.equals(name)){
                        exists = true;
                        presenter.onFailure(R.string.already_exists, R.color.red);
                        break;
                    }
                }

                if (!exists) {
                    insertUser(presenter, name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*public void login(IRegisterPresenter presenter, String name) {
        databaseReference = database.getReference(GlobalValues.USERS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exists = false;

                for (DataSnapshot snap: snapshot.getChildren()) {
                    String userName = Objects.requireNonNull(snap.child(GlobalValues.NAME).getValue()).toString();

                    if (userName.equals(name)){
                        exists = true;
                        presenter.onSuccess();
                        break;
                    }
                }

                if (!exists) {
                    presenter.onFailure(R.string.already_exists, R.color.red);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    private void insertUser(IRegisterPresenter presenter, String name) {
        databaseReference = database.getReference(GlobalValues.USERS);

        String id = databaseReference.push().getKey();
        Users user = new Users(id, name);

        databaseReference.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    presenter.onSuccess(R.string.success, R.color.green);
                } else {
                    presenter.onFailure(R.string.fail, R.color.red);
                }
            }
        });
    }

}
