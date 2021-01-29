package com.example.seriestracker.login;

public class LoginPresenter implements ILoginPresenter{
    private LoginActivity activity;

    public LoginPresenter(LoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public void login(String name) {

    }

    @Override
    public void onSuccess(int textId, int backgroundColorId) {
    }

    @Override
    public void onFailure(int textId, int backgroundColorId) {

    }
}
