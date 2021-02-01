package com.example.seriestracker.login;

import com.example.seriestracker.R;
import com.example.seriestracker.helper.FirebaseHelper;
import com.example.seriestracker.utils.Util;

public class LoginPresenter implements ILoginPresenter {
    private final LoginActivity activity;
    private String name;

    public LoginPresenter(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public void login(String name) {
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
        activity.nextPage();
    }

    @Override
    public void onFailure(int textId, int backgroundColorId) {
        activity.onActionSuccess(activity, textId, backgroundColorId);
    }
}
