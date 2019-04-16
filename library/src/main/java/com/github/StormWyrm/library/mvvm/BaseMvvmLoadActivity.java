package com.github.StormWyrm.library.mvvm;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.github.StormWyrm.library.R;
import com.github.StormWyrm.library.state.IStateView;
import com.github.StormWyrm.library.state.StateView;

import androidx.lifecycle.Observer;

public abstract class BaseMvvmLoadActivity<T extends AbsViewModel> extends BaseMvvmActivity<T> {
    protected StateView mStateView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_load;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mStateView = findViewById(R.id.sv_state);
        FrameLayout flContainer = findViewById(R.id.fl_container);
        getLayoutInflater().inflate(getChildLayoutId(), flContainer);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mStateView.setOnRetryClickLisenter(new StateView.OnRetryClickLisenter() {
            @Override
            public void onRetry() {
                onRetryClick();
            }
        });
    }

    protected void showError() {
        mStateView.showError();
    }

    protected void showEmpty() {
        mStateView.showEmpty();
    }

    protected void showLoading() {
        mStateView.showLoading();
    }

    protected void showSuccess() {
        mStateView.showSuccess();
    }

    protected void onRetryClick() {

    }

    protected abstract int getChildLayoutId();

    protected Observer observer = new Observer<Integer>() {
        @Override
        public void onChanged(Integer state) {
            switch (state) {
                case IStateView.STATE_EMPTY:
                    showEmpty();
                    break;
                case IStateView.STATE_LOADING:
                    showLoading();
                    break;
                case IStateView.STATE_ERROR:
                    showError();
                    break;
                case IStateView.STATE_SUCCESS:
                    showSuccess();
                    break;
                default:
            }
        }
    };
}
