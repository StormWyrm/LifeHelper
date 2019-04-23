package com.github.stormwyrm.library.rx.convert;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ExceptionConvert<T> implements Function<Throwable, ObservableSource<T>> {

    @Override
    public ObservableSource<T> apply(Throwable throwable) {
        return Observable.error(throwable);
    }
}
