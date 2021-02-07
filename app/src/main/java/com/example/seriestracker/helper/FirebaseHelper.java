package com.example.seriestracker.helper;

import androidx.annotation.NonNull;

import com.example.seriestracker.R;
import com.example.seriestracker.addSeries.IAddSeriesPresenter;
import com.example.seriestracker.common.IBasePresenter;
import com.example.seriestracker.home.IHomeActivityPresenter;
import com.example.seriestracker.login.ILoginPresenter;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserData;
import com.example.seriestracker.model.UserDataWithKey;
import com.example.seriestracker.model.Users;
import com.example.seriestracker.register.IRegisterPresenter;
import com.example.seriestracker.utils.GlobalValues;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
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

    public void checkIfUserAlreadyAddedTvShow(TvShow tvShow, IAddSeriesPresenter presenter) {
        databaseReference = database.getReference(GlobalValues.TV_SHOWS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exists = false;
                String title = tvShow.getName();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    String showTitle = Objects.requireNonNull(snap.child(GlobalValues.NAME).getValue()).toString();
                    String userId = Objects.requireNonNull(snap.child(GlobalValues.USER_ID).getValue()).toString();

                    if (showTitle.equals(title) && userId.equals(GlobalValues.CURRENT_USER_ID)) {
                        exists = true;
                        presenter.onFailure(R.string.series_already_added, R.color.primaryColor);
                        break;
                    }
                }

                if (!exists) {
                    addTvShow(tvShow, presenter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addUserData(IAddSeriesPresenter presenter, List<UserData> data) {
        databaseReference = database.getReference(GlobalValues.USER_DATA);

        for (UserData userData : data) {
            String id = databaseReference.push().getKey();

            if (userData.getName().equals(data.get(data.size() - 1).getName())) {
                databaseReference.child(Objects.requireNonNull(id)).setValue(userData).addOnFailureListener(e -> presenter.onFailure(R.string.fail, R.color.red))
                        .addOnCompleteListener(task -> presenter.onSuccess(R.string.data_successfully_added, R.color.green));
            } else {
                databaseReference.child(Objects.requireNonNull(id)).setValue(userData).addOnFailureListener(e -> presenter.onFailure(R.string.fail, R.color.red));
            }

        }
    }

    public void getUserTvShows(IHomeActivityPresenter presenter) {
        final List<TvShow> tvShows = new ArrayList<>();
        databaseReference = database.getReference(GlobalValues.TV_SHOWS);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (tvShows.size() > 0) {
                    tvShows.clear();
                }

                for (DataSnapshot data : snapshot.getChildren()) {
                    String userId = Objects.requireNonNull(data.child(GlobalValues.USER_ID).getValue()).toString();
                    if (userId.equals(GlobalValues.CURRENT_USER_ID)) {
                        int dbId = Integer.parseInt(Objects.requireNonNull(data.child(GlobalValues.DB_ID).getValue()).toString());
                        String name = Objects.requireNonNull(data.child(GlobalValues.NAME).getValue()).toString();
                        String image = Objects.requireNonNull(data.child(GlobalValues.IMAGE_URL).getValue()).toString();
                        int seasonNumber = Integer.parseInt(Objects.requireNonNull(data.child(GlobalValues.SEASON_NUMBER).getValue()).toString());

                        tvShows.add(new TvShow(userId, name, dbId, image, seasonNumber));
                    }
                }

                getUserSeriesDetails(tvShows, presenter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void changeSeenProperty(String id, boolean isSeen){
        databaseReference = database.getReference(GlobalValues.USER_DATA);
        databaseReference.child(id).child(GlobalValues.SEEN).setValue(String.valueOf(isSeen));
    }

    public void changeLikedProperty(String id, boolean isLiked){
        databaseReference = database.getReference(GlobalValues.USER_DATA);
        databaseReference.child(id).child(GlobalValues.LIKED).setValue(String.valueOf(isLiked));
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

    private void addTvShow(TvShow tvShow, IAddSeriesPresenter presenter) {
        databaseReference = database.getReference(GlobalValues.TV_SHOWS);

        String id = databaseReference.push().getKey();

        databaseReference.child(Objects.requireNonNull(id)).setValue(tvShow).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                presenter.addShowSeasonsAndEpisodesToFirebase(tvShow);
            } else {
                presenter.onFailure(R.string.show_not_added, R.color.red);
            }
        });
    }

    private void getUserSeriesDetails(List<TvShow> tvShows, IHomeActivityPresenter presenter) {
        final List<UserDataWithKey> userData = new ArrayList<>();
        databaseReference = database.getReference(GlobalValues.USER_DATA);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (userData.size() > 0) {
                    userData.clear();
                }

                for (DataSnapshot data : snapshot.getChildren()) {
                    String userId = Objects.requireNonNull(data.child(GlobalValues.USER_ID).getValue()).toString();

                    if (userId.equals(GlobalValues.CURRENT_USER_ID)) {
                        String key = data.getKey();
                        int dbId = Integer.parseInt(Objects.requireNonNull(data.child(GlobalValues.DB_ID).getValue()).toString());
                        String name = Objects.requireNonNull(data.child(GlobalValues.NAME).getValue()).toString();
                        String image = Objects.requireNonNull(data.child(GlobalValues.IMAGE_URL).getValue()).toString();
                        int seasonNumber = Integer.parseInt(Objects.requireNonNull(data.child(GlobalValues.SEASON_NUMBER).getValue()).toString());
                        int episodeNumber = Integer.parseInt(Objects.requireNonNull(data.child(GlobalValues.EPISODE_NUMBER).getValue()).toString());
                        boolean liked = Boolean.parseBoolean(Objects.requireNonNull(data.child(GlobalValues.LIKED).getValue()).toString());
                        boolean seen = Boolean.parseBoolean(Objects.requireNonNull(data.child(GlobalValues.SEEN).getValue()).toString());
                        userData.add(new UserDataWithKey(userId, name, dbId, image, seasonNumber, episodeNumber, seen, liked, key));
                    }
                }

                presenter.fetchTvShowsDone(tvShows, userData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
