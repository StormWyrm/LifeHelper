package com.github.StormWyrm.library.rx.rxbus;

import io.reactivex.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtils {
    private final Map<Class, List<TagMessage>> stickyEventsMap = new ConcurrentHashMap<>();
    private final Map<Object, List<Disposable>> disposablesMap = new ConcurrentHashMap<>();

    private CacheUtils() {
    }

    public static CacheUtils getInstance() {
        return Holder.CACHE_UTILS;
    }

    void addStickyEvent(TagMessage stickyEvent) {
        Class eventType = stickyEvent.getEventType();
        synchronized (stickyEventsMap) {
            List<TagMessage> stickyEvents = stickyEventsMap.get(eventType);
            if (stickyEvents == null) {
                stickyEvents = new ArrayList<>();
                stickyEvents.add(stickyEvent);
                stickyEventsMap.put(eventType, stickyEvents);
            } else {
                int indexOf = stickyEvents.indexOf(stickyEvent);
                if (indexOf == -1)
                    stickyEvents.add(stickyEvent);
                else
                    stickyEvents.set(indexOf, stickyEvent);
            }
        }
    }

    TagMessage findStickyEvent(Class eventType, String tag) {
        TagMessage res = null;
        synchronized (stickyEventsMap) {
            List<TagMessage> stickyEvents = stickyEventsMap.get(eventType);
            if (stickyEvents != null) {
                int size = stickyEvents.size();
                for (int i = size - 1; i >= 0; i--) {
                    TagMessage tagMessage = stickyEvents.get(i);
                    if (tagMessage.isSameType(eventType, tag)) {
                        res = stickyEvents.get(i);
                        break;
                    }
                }
            }
            return res;
        }
    }

    void addDisposable(Object subscriber, Disposable disposable) {
        synchronized (disposablesMap) {
            List<Disposable> disposables = disposablesMap.get(subscriber);
            if (disposables == null) {
                disposables = new ArrayList<>();
                disposables.add(disposable);
                disposablesMap.put(subscriber, disposables);
            } else {
                disposables.add(disposable);
            }
        }
    }

    void remvoeDisposables(Object subscriber) {
        synchronized (disposablesMap) {
            List<Disposable> disposables = disposablesMap.get(subscriber);
            if (disposables != null) {
                for (Disposable disposable : disposables) {
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
                disposables.clear();
            }
            disposablesMap.remove(subscriber);
        }
    }

    private static class Holder {
        public static final CacheUtils CACHE_UTILS = new CacheUtils();
    }
}
