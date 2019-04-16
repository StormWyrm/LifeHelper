package com.github.StormWyrm.library.mvp;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.github.StormWyrm.library.R;
import com.github.StormWyrm.library.state.StateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public abstract class BaseMvpListActivity<P extends IPresenter>
        extends BaseMvpActivity<P>
        implements IListView<P> {
    protected SmartRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected StateView mStateView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.rv);
        mStateView = findViewById(R.id.sv_state);
        mRefreshLayout.setEnableRefresh(setEnableRefresh());
        mRefreshLayout.setEnableLoadMore(setEnableLoadmore());
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

    @Override
    public void loadDataError() {
        mStateView.showError();
    }

    @Override
    public void noData() {
        mStateView.showEmpty();
    }

    @Override
    public void loadMoreError() {
        mRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void noMoreData() {
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    protected void showContent() {
        mStateView.showSuccess();
    }

    protected void showError() {
        mStateView.showError();
    }

    protected void showLoading() {
        mStateView.showLoading();
    }

    protected void showEmpty() {
        mStateView.showEmpty();
    }

    protected boolean setEnableLoadmore() {
        return true;
    }

    protected boolean setEnableRefresh() {
        return false;
    }

    protected abstract void onRetryClick();

}
