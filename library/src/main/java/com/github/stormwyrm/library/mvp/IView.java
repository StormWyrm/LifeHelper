package com.github.stormwyrm.library.mvp;

import com.github.stormwyrm.library.base.activity.BaseActivity;

import java.lang.ref.SoftReference;

public interface IView<P extends IPresenter> {
    P initPresenter();

    void finishActivity();

    SoftReference<BaseActivity> getSoftActivity();
}
