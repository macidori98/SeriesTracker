package com.example.seriestracker.login;

import com.example.seriestracker.common.IBasePresenter;

public interface ILoginPresenter extends IBasePresenter {
    void login(String name);

    void onSuccess(int textId, int backgroundColorId, String id);
}
