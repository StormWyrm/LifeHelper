package com.github.StormWyrm.library.state;

public interface IStateView {
    int STATE_SUCCESS = 0;
    int STATE_EMPTY = 1;
    int STATE_ERROR = 2;
    int STATE_LOADING = 3;

    void showSuccess();

    void showError();

    void showEmpty();

    void showLoading();
}
