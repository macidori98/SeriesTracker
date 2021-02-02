package com.example.seriestracker.helper;

import androidx.annotation.NonNull;

import com.example.seriestracker.R;
import com.example.seriestracker.addSeries.IAddSeriesPresenter;
import com.example.seriestracker.common.IBasePresenter;
import com.example.seriestracker.login.ILoginPresenter;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.Users;
import com.example.seriestracker.register.IRegisterPresenter;
import com.example.seriestracker.utils.GlobalValues;
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

    public void checkIfUserAlreadyExists(IBasePresenter presenter, String name) {
        databaseReference = database.getReference(GlobalValues.USERS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean exists = false;
                String userId = "";

                for (DataSnapshot snap : snapshot.getChildren()) {
                    String userName = Objects.requireNonNull(snap.child(GlobalValues.NAME).getValue()).toString();
                    userId = Objects.requireNonNull(snap.child(GlobalValues.USER_ID).getValue()).toString();

                    if (userName.equals(name)) {
                        exists = true;
                        break;
                    }
                }

                if (presenter instanceof IRegisterPresenter) {
                    //registration user must not exists
                    if (!exists) {
                        insertUser((IRegisterPresenter) presenter, name);
                    } else {
                        presenter.onFailure(R.string.already_exists, R.color.red);
                    }
                } else {
                    //login user must exists
                    if (exists) {
                        ILoginPresenter loginPresenter = (ILoginPresenter) presenter;
                        loginPresenter.onSuccess(R.string.login_successfully, R.color.green, userId);
                    } else {
                        presenter.onFailure(R.string.does_not_exist, R.color.red);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addTvShow(TvShow tvShow, IAddSeriesPresenter presenter) {
        databaseReference = database.getReference(GlobalValues.TV_SHOWS);

        String id = databaseReference.push().getKey();

        databaseReference.child(id).setValue(tvShow).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //presenter.addShowSeasonsAndEpisodesToFirebase();
                presenter.onSuccess(R.string.success, R.color.green);
            } else {
                presenter.onFailure(R.string.show_not_added, R.color.red);
            }
        });
    }

    private void insertUser(IRegisterPresenter presenter, String name) {
        databaseReference = database.getReference(GlobalValues.USERS);

        String id = databaseReference.push().getKey();
        Users user = new Users(id, name);

        databaseReference.child(Objects.requireNonNull(id)).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                presenter.onSuccess(R.string.registration_success, R.color.green, id);
            } else {
                presenter.onFailure(R.string.fail, R.color.red);
            }
        });
    }

}
