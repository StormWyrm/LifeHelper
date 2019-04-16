package com.github.StormWyrm.library.mvp;

import com.github.StormWyrm.library.base.activity.BaseActivity;

import java.lang.ref.SoftReference;

public interface IView<P extends IPresenter> {
    P initPresenter();

    void finishActivity();

    SoftReference<BaseActivity> getSoftActivity();
}
