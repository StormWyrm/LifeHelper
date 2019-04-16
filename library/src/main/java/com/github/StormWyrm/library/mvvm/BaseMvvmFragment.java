package com.github.StormWyrm.library.mvvm;

import android.os.Bundle;

import com.github.StormWyrm.library.base.fragment.BaseFragment;
import com.github.StormWyrm.library.util.TUtil;

import androidx.lifecycle.ViewModelProviders;

public abstract class BaseMvvmFragment<T extends AbsViewModel> extends BaseFragment {
    protected T mViewModel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get((Class<T>) TUtil.getInstance(this, 0));
        dataObserver();
    }

    protected void dataObserver() {

    }
}
