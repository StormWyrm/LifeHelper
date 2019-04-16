package com.github.StormWyrm.library.base.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.github.StormWyrm.library.R;

public abstract class BaseToolbarActivity extends BaseActivity {
    protected FrameLayout flContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_toolbar;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        flContainer = findViewById(R.id.fl_container);
        View.inflate(this,getContainerId(),flContainer);
    }

    protected abstract int getContainerId();
}
