package com.github.StormWyrm.library.livedata;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.ConcurrentHashMap;

public class LiveDataBus {
    private ConcurrentHashMap<String, LiveBusData<Object>> bus;

    private LiveDataBus() {
        bus = new ConcurrentHashMap<>();
    }

    public static LiveDataBus getDefault() {
        return Holder.LIVE_DATA_BUS;
    }

    public <T> void postEvent(Object eventKey, T value) {
        postEvent(eventKey, "", value);
    }

    public <T> void postEvent(Object event, String tag, T value) {
        MutableLiveData<Object> liveData = subscribe(event, tag);
        liveData.postValue(value);
    }

    public <T> MutableLiveData<T> subscribe(Object event) {
        return subscribe(event, "");
    }

    public <T> MutableLiveData<T> subscribe(Object event, String tag) {
        String key = mergeEventKey(event, tag);
        LiveBusData liveBusData = bus.get(key);
        if (liveBusData == null) {
            liveBusData = new LiveBusData(true);
            bus.put(key, liveBusData);
        }else{
            liveBusData.isFristSubscribe = false;
        }
        return (MutableLiveData<T>) liveBusData;
    }

    private String mergeEventKey(Object eventKey, String tag) {
        String mergeKey;
        if (!TextUtils.isEmpty(tag)) {
            mergeKey = eventKey.toString() + tag;
        } else {
            mergeKey = eventKey.toString();
        }
        return mergeKey;
    }

    private class LiveBusData<T> extends MutableLiveData<T> {
        private boolean isFristSubscribe;

        public LiveBusData(boolean isFristSubscribe) {
            this.isFristSubscribe = isFristSubscribe;
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, new ObserverWrapper<>(observer,isFristSubscribe));
        }
    }

    private class ObserverWrapper<T> implements Observer<T> {

        private boolean isChanged;
        private Observer<T> observer;

        public ObserverWrapper(Observer<T> observer,boolean isFirstSubscribe) {
            this.isChanged = isFirstSubscribe;
            this.observer = observer;
        }

        @Override
        public void onChanged(T t) {
            if (isChanged) {
                if (observer != null) {
                    observer.onChanged(t);
                }
            } else {
                isChanged = true;
            }
        }
    }

    private static class Holder {
        private static final LiveDataBus LIVE_DATA_BUS = new LiveDataBus();
    }
}
