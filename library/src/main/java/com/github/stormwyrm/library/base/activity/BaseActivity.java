package com.github.stormwyrm.library.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.github.stormwyrm.library.R;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected BaseActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mActivity = this;
        initStatusBar();//初始化状态栏
        setContentView(getLayoutId());
        initView(savedInstanceState);
        initListener();
        initData(savedInstanceState);
    }

    protected void initStatusBar() {

    }

    protected void initListener() {

    }

    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    protected void initData(@Nullable Bundle savedInstanceState) {

    }

    protected void initToolbar(@StringRes int titleId) {
        initToolbar(getString(titleId));
    }

    protected void initToolbar(String title) {
        initToolbar(title, false);
    }

    protected void initToolbar(String title, boolean canIReturn) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (canIReturn) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int getLayoutId();


}
