package com.github.StormWyrm.library.mvp;

import android.os.Bundle;

import com.github.StormWyrm.library.base.activity.BaseActivity;

import java.lang.ref.SoftReference;

import androidx.annotation.Nullable;

public abstract class BaseMvpActivity<P extends IPresenter> extends BaseActivity
        implements IView<P> {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mPresenter.attach(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public SoftReference<BaseActivity> getSoftActivity() {
        return new SoftReference<BaseActivity>(this);
    }
}
