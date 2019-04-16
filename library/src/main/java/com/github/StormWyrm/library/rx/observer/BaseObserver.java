package com.github.StormWyrm.library.rx.observer;

import com.github.StormWyrm.library.exception.ExceptionHandler;
import com.github.StormWyrm.library.exception.ResponseException;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseObserver<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        ResponseException responseException = ExceptionHandler.handleException(t);
        onFailure(responseException, responseException.getErrorMessage());
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(Throwable t, String msg);
}
