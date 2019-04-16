package com.github.StormWyrm.library.mvvm;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.github.StormWyrm.library.R;
import com.github.StormWyrm.library.livedata.LiveDataBus;
import com.github.StormWyrm.library.state.IStateView;
import com.github.StormWyrm.library.state.StateView;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;

public abstract class BaseMvvmLoadFragment<T extends AbsViewModel> extends BaseMvvmFragment<T> {
    protected StateView mStateView;
    protected Object mStateEventKey;
    protected String mStateEventTag;
    private List eventKeys = new ArrayList<Object>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_load;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mStateView = rootView.findViewById(R.id.sv_state);
        FrameLayout flContainer = rootView.findViewById(R.id.fl_container);
        getLayoutInflater().inflate(getChildLayoutId(), flContainer);
    }

    @Override
    protected void initLisenter() {
        super.initLisenter();
        mStateView.setOnRetryClickLisenter(new StateView.OnRetryClickLisenter() {
            @Override
            public void onRetry() {
                onRetryClick();
            }
        });
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        mStateEventKey = getStateEventKey();
        mStateEventTag = getStateEventTag();
        eventKeys.add(new StringBuilder((String) mStateEventKey).append(mStateEventTag).toString());
        LiveDataBus.getDefault()
                .subscribe(mStateEventKey, mStateEventTag)
                .observe(this, observer);
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

    protected abstract String getStateEventTag();

    protected abstract Object getStateEventKey();

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
