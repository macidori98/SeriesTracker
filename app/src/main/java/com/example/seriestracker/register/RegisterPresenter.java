package com.example.seriestracker.register;

import com.example.seriestracker.R;
import com.example.seriestracker.helper.FirebaseHelper;
import com.example.seriestracker.utils.Util;

public class RegisterPresenter implements IRegisterPresenter {
    private final RegisterActivity view;
    private String name;

    public RegisterPresenter(RegisterActivity view) {
        this.view = view;
    }

    @Override
    public void registerUser(String name) {
        if (Util.isStringLengthOk(name)) {
            FirebaseHelper.getInstance().checkIfUserAlreadyExists(this, name);
            this.name = name;
        } else {
            view.onActionFailure(view, R.string.short_name, R.color.primaryColor);
        }
    }

    @Override
    public void onSuccess(int textId, int backgroundColorId) {
        Util.setSharedPref(view, name);

        view.onActionSuccess(view, textId, backgroundColorId);
        view.nextPage();
    }

    @Override
    public void onFailure(int textId, int backgroundColorId) {
        view.onActionSuccess(view, textId, backgroundColorId);
    }
}
