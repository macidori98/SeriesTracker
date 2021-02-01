package com.example.seriestracker.common;

public interface IBasePresenter {
    void onSuccess(int textId, int backgroundColorId);

    void onFailure(int textId, int backgroundColorId);
}
