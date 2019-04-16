package com.github.StormWyrm.library.rx.scheduler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxScheduler {

    public static <T> BaseScheduler<T> ioToMain() {
        return new BaseScheduler<T>(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    public static <T> BaseScheduler<T> computationToMain() {
        return new BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread());
    }

    public static <T> BaseScheduler<T> newThreadToMain() {
        return new BaseScheduler<T>(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }
}
