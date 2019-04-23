package com.github.stormwyrm.library.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stormwyrm.library.base.activity.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected BaseActivity mActivity;

    protected View rootView;
    private boolean isLoaded;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof BaseActivity)
            mActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(),container);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
        initLisenter();
        boolean isVisible = isHidden() || getUserVisibleHint();
        if(isVisible && !isLoaded){
            isLoaded = true;
            lazyLoad();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            onVisible();
        }else{
            onInvisible();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            onVisible();
        }else{
            onInvisible();
        }
    }

    protected abstract int getLayoutId();

    protected void lazyLoad() {

    }

    protected void initView(Bundle savedInstanceState) {

    }

    protected void initLisenter() {

    }

    protected void onInvisible() {

    }

    private void onVisible() {
        if (!isLoaded && isResumed()) {
            lazyLoad();
            isLoaded = true;
        }
    }
}
