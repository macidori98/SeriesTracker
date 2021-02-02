package com.example.seriestracker.register;

import com.example.seriestracker.common.IBasePresenter;

public interface IRegisterPresenter extends IBasePresenter {
    void registerUser(String name);

    void onSuccess(int textId, int backgroundColorId, String userID);
}
