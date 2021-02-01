package com.example.seriestracker.register;

import com.example.seriestracker.R;
import com.example.seriestracker.helper.FirebaseHelper;
import com.example.seriestracker.home.HomeActivity;
import com.example.seriestracker.utils.Util;

public class RegisterPresenter implements IRegisterPresenter {
    private final RegisterActivity activity;
    private String name;

    public RegisterPresenter(RegisterActivity activity) {
        this.activity = activity;
    }

    @Override
    public void registerUser(String name) {
        if (Util.isStringLengthOk(name)) {
            FirebaseHelper.getInstance().checkIfUserAlreadyExists(this, name);
            this.name = name;
        } else {
            activity.onActionFailure(activity, R.string.short_name, R.color.primaryColor);
        }
    }

    @Override
    public void onSuccess(int textId, int backgroundColorId) {
        Util.setSharedPref(activity, name);

        activity.onActionSuccess(activity, textId, backgroundColorId);
        activity.nextPage(activity, new HomeActivity());
    }

    @Override
    public void onFailure(int textId, int backgroundColorId) {
        activity.onActionSuccess(activity, textId, backgroundColorId);
    }
}
