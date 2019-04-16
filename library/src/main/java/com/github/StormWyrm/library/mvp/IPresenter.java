package com.github.StormWyrm.library.mvp;

import androidx.annotation.NonNull;

public abstract class IPresenter<V extends IView, M extends IModel> {
    protected V mView;
    protected M mModel;

    protected void attach(@NonNull V view) {
        mView = view;
        mModel = initModel();
    }

    protected void dettach() {
        mView = null;
        if (mModel != null) {
            mModel.unDisposable();
            mModel = null;
        }
    }

    protected abstract M initModel();
}
