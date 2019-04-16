package com.github.StormWyrm.library.util;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import com.bumptech.glide.Glide;

public class LibApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //当前应用的内容情况,点击home键进入后台时候清理内存
        if (level == Application.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //Android系统低内存时候调用
        Glide.get(this).clearMemory();
    }
}
