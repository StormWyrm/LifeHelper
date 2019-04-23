package com.github.stormwyrm.library.rx.rxbus;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class Rxbus {
    private static final String TAG = "Rxbus";

    private Subject<Object> mBus;
    private final Consumer<Throwable> mOnError = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable throwable) {
            Utils.logD(throwable.toString());
        }
    };

    private Rxbus() {
        mBus = PublishSubject.create().toSerialized();
    }

    public static Rxbus getDefault() {
        return Holder.BUS;
    }

    public void post(Object event) {
        post(event, "");
    }

    public void post(Object event, final String tag) {
        post(event, tag, false);
    }

    public void postSticky(Object event) {
        postSticky(event, "");
    }

    public void postSticky(Object event, String tag) {
        post(event, tag, true);
    }

    public void post(Object event, String tag, boolean isSticky) {
        Utils.requireNonNull(event, tag);

        TagMessage msgEvent = new TagMessage(event, tag);
        if (isSticky) {
            CacheUtils.getInstance().addStickyEvent(msgEvent);
        }
        mBus.onNext(msgEvent);
    }

    public <T> void subscribe(final Object subscriber,
                              final Callback<T> callback) {
        subscribe(subscriber, "", false, null, callback);
    }

    public <T> void subscribe(final Object subscriber,
                              final String tag,
                              final Callback<T> callback) {
        subscribe(subscriber, tag, false, null, callback);
    }

    public <T> void subscribe(final Object subscriber,
                              final Scheduler scheduler,
                              final Callback<T> callback) {
        subscribe(subscriber, "", false, scheduler, callback);
    }

    public <T> void subscribe(final Object subscriber,
                              final String tag,
                              final Scheduler scheduler,
                              final Callback<T> callback) {
        subscribe(subscriber, tag, false, scheduler, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final Callback<T> callback) {
        subscribe(subscriber, "", true, null, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final String tag,
                                    final Callback<T> callback) {
        subscribe(subscriber, tag, true, null, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final Scheduler scheduler,
                                    final Callback<T> callback) {
        subscribe(subscriber, "", true, scheduler, callback);
    }

    public <T> void subscribeSticky(final Object subscriber,
                                    final String tag,
                                    final Scheduler scheduler,
                                    final Callback<T> callback) {
        subscribe(subscriber, tag, true, scheduler, callback);
    }

    public <T> void subscribe(Object subscriber,
                              String tag,
                              boolean isSticky,
                              Scheduler observeOnScheduler,
                              final Callback<T> callback) {

        Utils.requireNonNull(subscriber, tag, callback);
        final Class<T> eventType = Utils.getTypeClassFromPara(callback);
        final Consumer<T> onNext = new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                callback.onEvent(t);
            }
        };
        Disposable disposable;
        if (isSticky) {
            final TagMessage stickyEvent = CacheUtils.getInstance().findStickyEvent(eventType, tag);
            if (stickyEvent != null) {
                Observable<T> observable = Observable.create(new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                        emitter.onNext(eventType.cast(stickyEvent.getEvent()));
                    }
                });

                if (observeOnScheduler != null) {
                    observable = observable.observeOn(observeOnScheduler);
                }
                disposable = observable.subscribe(onNext, mOnError);
                CacheUtils.getInstance().addDisposable(subscriber, disposable);
            } else {
                Utils.logD("sticky event is empty.");
            }
        } else {
            disposable = toObservable(eventType, tag, observeOnScheduler)
                    .subscribe(onNext, mOnError);
            CacheUtils.getInstance().addDisposable(subscriber, disposable);
        }

    }

    private <T> Observable<T> toObservable(final Class<T> eventType, final String tag, Scheduler observeOnScheduler) {
        Observable<T> observable = mBus.ofType(TagMessage.class)
                .filter(new Predicate<TagMessage>() {
                    @Override
                    public boolean test(TagMessage tagMessage) throws Exception {
                        return tagMessage.isSameType(eventType, tag);
                    }
                })
                .map(new Function<TagMessage, Object>() {
                    @Override
                    public Object apply(TagMessage tagMessage) throws Exception {
                        return tagMessage.getEvent();
                    }
                })
                .cast(eventType);
        if (observeOnScheduler != null)
            return observable.observeOn(observeOnScheduler);

        return observable;
    }

    public void unregister(Object subscriber) {
        CacheUtils.getInstance().remvoeDisposables(subscriber);
    }

    public abstract static class Callback<T> {
        public abstract void onEvent(T t);
    }

    private static class Holder {
        private static final Rxbus BUS = new Rxbus();
    }
}
