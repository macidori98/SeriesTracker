package com.example.seriestracker.register;

public interface IRegisterPresenter {
    void registerUser(String name);
    void onSuccess(int textId, int backgroundColorId);
    void onFailure(int textId, int backgroundColorId);
}
