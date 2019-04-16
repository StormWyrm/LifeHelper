package com.github.StormWyrm.library.rx.scheduler;

import io.reactivex.*;
import org.reactivestreams.Publisher;

public class BaseScheduler<T> implements
        ObservableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer,
        FlowableTransformer<T, T> {
    private Scheduler subscribeOnSheduler;
    private Scheduler observeOnSheduler;

    public BaseScheduler(Scheduler subscribeOnSheduler, Scheduler observeOnSheduler) {
        this.subscribeOnSheduler = subscribeOnSheduler;
        this.observeOnSheduler = observeOnSheduler;
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream) {
        return upstream.subscribeOn(subscribeOnSheduler).observeOn(observeOnSheduler);
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(subscribeOnSheduler).observeOn(observeOnSheduler);
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream.subscribeOn(subscribeOnSheduler).observeOn(observeOnSheduler);
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.subscribeOn(subscribeOnSheduler).observeOn(observeOnSheduler);
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return upstream.subscribeOn(subscribeOnSheduler).observeOn(observeOnSheduler);
    }
}
