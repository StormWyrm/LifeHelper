package com.github.StormWyrm.library.mvvm;

import android.os.Bundle;

import com.github.StormWyrm.library.base.activity.BaseActivity;
import com.github.StormWyrm.library.util.TUtil;

import androidx.lifecycle.ViewModelProviders;


public abstract class BaseMvvmActivity<T extends AbsViewModel> extends BaseActivity {
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
