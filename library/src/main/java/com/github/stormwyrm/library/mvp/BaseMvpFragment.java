package com.github.stormwyrm.library.mvp;

import android.os.Bundle;
import android.view.View;

import com.github.stormwyrm.library.base.activity.BaseActivity;
import com.github.stormwyrm.library.base.fragment.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.lang.ref.SoftReference;

public abstract class BaseMvpFragment<P extends IPresenter> extends BaseFragment
        implements IView<P> {
    protected P mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mPresenter.attach(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.dettach();
    }

    @Override
    public void finishActivity() {
        mActivity.finish();
    }

    @Override
    public SoftReference<BaseActivity> getSoftActivity() {
        return new SoftReference<>(mActivity);
    }
}
