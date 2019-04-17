package com.github.StormWyrm.library.util;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.github.StormWyrm.library.BuildConfig;
import com.github.StormWyrm.library.arounter.ARouterUtils;

import org.litepal.LitePal;

public class LibApplication extends Application {
    private static Context applicationContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
//        LitePal.initialize(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouterUtils.destroy();
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

    public static Context getContext() {
        return applicationContext;
    }
}
