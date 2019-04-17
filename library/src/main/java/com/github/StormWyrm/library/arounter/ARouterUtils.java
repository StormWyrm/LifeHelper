package com.github.StormWyrm.library.arounter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ARouterUtils {
    private static final String TAG = "ARouterUtils";
    public static NavigationCallback callback = new NavigationCallback() {
        @Override
        public void onFound(Postcard postcard) {
            Log.d(TAG, "onFound: "+postcard);
        }

        @Override
        public void onLost(Postcard postcard) {
            Log.d(TAG, "onLost: "+postcard);
        }

        @Override
        public void onArrival(Postcard postcard) {
            Log.d(TAG, "onArrival: "+postcard);
        }

        @Override
        public void onInterrupt(Postcard postcard) {
            Log.d(TAG, "onInterrupt: "+postcard);
        }
    };

    public static void navigation(@Nullable Uri uri) {
        ARouter.getInstance()
                .build(uri)
                .navigation();
    }

    public static void navigation(@NonNull String path){
        ARouter.getInstance()
                .build(path)
                .navigation();
    }

    public static void navigation(@NonNull String path,@Nullable Context context) {
        ARouter.getInstance()
                .build(path)
                .navigation(context);
    }


    public static void navigation(@NonNull String path, @NonNull Bundle bundle) {
        ARouter.getInstance()
                .build(path)
                .with(bundle)
                .navigation();
    }

    public static void navigation(
            @NonNull String path,
            @NonNull Activity context,
            @NonNull int requestCode) {
        ARouter.getInstance()
                .build(path)
                .navigation(context, requestCode);
    }

    public static void navigation(
            @Nullable String path,
            @Nullable Context context,
            @Nullable NavigationCallback callback) {
        ARouter.getInstance()
                .build(path)
                .navigation(context, callback);
    }

    public static void injectActivity(@Nullable Activity activity) {
        ARouter.getInstance()
                .inject(activity);
    }

    public static void injectFragment(@Nullable Fragment fragment) {
        ARouter.getInstance()
                .inject(fragment);
    }

    public static void destroy() {
        ARouter.getInstance().destroy();
    }
}
