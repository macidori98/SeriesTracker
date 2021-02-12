package com.example.seriestracker.details;

import com.example.seriestracker.R;
import com.example.seriestracker.model.UserDataWithKey;
import com.example.seriestracker.utils.GlobalValues;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivityPresenter implements IDetailsActivityPresenter {
    private final DetailsActivity activity;

    public DetailsActivityPresenter(DetailsActivity activity) {
        this.activity = activity;
    }

    @Override
    public List<String> getStringArrayFromList() {
        List<String> array = new ArrayList<>();
        int seasonNumber = GlobalValues.TVSHOW.getSeasonNumber();

        array.add(activity.getResources().getString(R.string.choose_season));
        for (int season = 1; season <= seasonNumber; ++season) {
            array.add(activity.getResources().getString(R.string.season).concat(String.valueOf(season)));
        }

        return array;
    }

    @Override
    public List<UserDataWithKey> getSeasonEpisodes(int season) {
        int id = GlobalValues.TVSHOW.getDbId();

        List<UserDataWithKey> userDataWithKeys = new ArrayList<>();
        for (UserDataWithKey udwk : GlobalValues.USERDATAS) {
            if (udwk.getSeasonNumber() == season && udwk.getDbId() == id) {
                userDataWithKeys.add(udwk);
            }
        }

        userDataWithKeys.sort((o1, o2) -> {
            Integer x1 = o1.getEpisodeNumber();
            Integer x2 = o2.getEpisodeNumber();

            return x1.compareTo(x2);
        });

        return userDataWithKeys;
    }
}
