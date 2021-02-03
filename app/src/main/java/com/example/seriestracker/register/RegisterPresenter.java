package com.example.seriestracker.register;

import com.example.seriestracker.R;
import com.example.seriestracker.helper.FirebaseHelper;
import com.example.seriestracker.utils.GlobalValues;
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
    public void onSuccess(int textId, int backgroundColorId, String userId) {
        Util.setSharedPref(activity, GlobalValues.NAME, name);
        Util.setSharedPref(activity, GlobalValues.USER_ID, userId);

        GlobalValues.CURRENT_USER_ID = userId;
        GlobalValues.CURRENT_USER = name;

        activity.onActionSuccess(activity, textId, backgroundColorId);
        activity.nextPage();
    }

    @Override
    public void onSuccess(int textId, int backgroundColorId) {

    }

    @Override
    public void onFailure(int textId, int backgroundColorId) {
        activity.onActionSuccess(activity, textId, backgroundColorId);
    }
}
