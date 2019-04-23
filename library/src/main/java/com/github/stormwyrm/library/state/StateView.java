package com.github.stormwyrm.library.state;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.stormwyrm.library.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StateView extends FrameLayout implements IStateView {
    private static final String TAG = "StateView";

    private View successView;
    private View emptyView;
    private View errorView;
    private View loadingView;
    private ImageView ivEmpty;
    private TextView tvEmpty;
    private ImageView ivError;
    private TextView tvErrorMsg;
    private TextView tvErrorDesMsg;
    private Button btnTry;
    private TextView tvLoadingMsg;

    private int errorImg;
    private String errorMsg;
    private String errorDesMsg;
    private String errorTryMsg;
    private int emptyImg;
    private String emptyMsg;
    private int loadingColor;
    private String loadingMsg;
    private int textColor;
    private int subTextColor;

    private int curState = STATE_SUCCESS;//当前状态
    private OnRetryClickLisenter onRetryClickLisenter;
    private OnStateChangeLisenter onStateChangeLisenter;

    public StateView(@NonNull Context context) {
        this(context, null);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        int childCount = getChildCount();
        if (childCount > 4) {
            throw new RuntimeException("StateView only allow have a child view!");
        }
        if (childCount == 4) {
            initSuccessView();
        }
    }

    @Override
    public void showSuccess() {
        if (curState != STATE_SUCCESS) {
            hideAll();
            successView.setVisibility(View.VISIBLE);
            curState = STATE_SUCCESS;
            onStateChange(curState);
        }
    }

    @Override
    public void showError() {
        if (curState != STATE_ERROR) {
            initErrorView();
            hideAll();
            errorView.setVisibility(View.VISIBLE);
            curState = STATE_ERROR;
            onStateChange(curState);
        }
    }

    @Override
    public void showEmpty() {
        if (curState != STATE_EMPTY) {
            initEmptyView();
            hideAll();
            emptyView.setVisibility(View.VISIBLE);
            curState = STATE_EMPTY;
            onStateChange(curState);
        }
    }

    @Override
    public void showLoading() {
        if (curState != STATE_LOADING) {
            initLoadingView();
            hideAll();
            loadingView.setVisibility(View.VISIBLE);
            curState = STATE_LOADING;
            onStateChange(curState);
        }
    }

    public void setOnRetryClickLisenter(OnRetryClickLisenter onRetryClickLisenter) {
        this.onRetryClickLisenter = onRetryClickLisenter;
    }

    public void setOnStateChangeLisenter(OnStateChangeLisenter onStateChangeLisenter) {
        this.onStateChangeLisenter = onStateChangeLisenter;
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateView, defStyleAttr, 0);
        errorImg = typedArray.getResourceId(R.styleable.StateView_errorImg, -1);
        errorMsg = typedArray.getString(R.styleable.StateView_errorMsg);
        errorDesMsg = typedArray.getString(R.styleable.StateView_errorDesMsg);
        errorTryMsg = typedArray.getString(R.styleable.StateView_errorTryMsg);
        emptyImg = typedArray.getResourceId(R.styleable.StateView_emptyImg, -1);
        emptyMsg = typedArray.getString(R.styleable.StateView_emptyMsg);
        loadingColor = typedArray.getColor(R.styleable.StateView_loadingColor, -1);
        loadingMsg = typedArray.getString(R.styleable.StateView_loadingMsg);
        textColor = typedArray.getColor(R.styleable.StateView_textColor, getResources().getColor(R.color.textTitleColor));
        subTextColor = typedArray.getColor(R.styleable.StateView_subTextColor, getResources().getColor(R.color.subtextTitleColor));
        typedArray.recycle();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.layout_state_view, this);
    }

    private void initSuccessView() {
        int childCount = getChildCount();
        successView = getChildAt(childCount - 1);
    }

    private void initLoadingView() {
        if (loadingView != null)
            return;
        ViewStub vsLoading = findViewById(R.id.sv_loading);
        loadingView = vsLoading.inflate();
        tvLoadingMsg = findViewById(R.id.tv_loading_msg);

        if (!TextUtils.isEmpty(loadingMsg)) {
            tvLoadingMsg.setText(loadingMsg);
            tvLoadingMsg.setTextColor(subTextColor);
        }
    }

    private void initErrorView() {
        if (errorView != null)
            return;

        ViewStub vsError = findViewById(R.id.sv_error);
        errorView = vsError.inflate();
        ivError = findViewById(R.id.iv_error);
        tvErrorMsg = findViewById(R.id.tv_error);
        tvErrorDesMsg = findViewById(R.id.tv_errDes);
        btnTry = findViewById(R.id.btn_retry);
        btnTry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRetryClickLisenter != null) {
                    onRetryClickLisenter.onRetry();
                }
            }
        });
        if (errorImg != -1) {
            ivError.setImageResource(errorImg);
        }
        if (!TextUtils.isEmpty(errorMsg)) {
            tvErrorMsg.setText(errorMsg);
            tvErrorMsg.setTextColor(subTextColor);
        }
        if (!TextUtils.isEmpty(errorDesMsg)) {
            tvErrorDesMsg.setText(errorDesMsg);
            tvErrorDesMsg.setTextColor(subTextColor);
        }
        if (!TextUtils.isEmpty(errorTryMsg)) {
            btnTry.setText(errorTryMsg);
            btnTry.setTextColor(textColor);
        }
    }

    private void initEmptyView() {
        if (emptyView != null)
            return;

        ViewStub vsEmpty = findViewById(R.id.sv_empty);
        emptyView = vsEmpty.inflate();
        ivEmpty = findViewById(R.id.iv_empty);
        tvEmpty = findViewById(R.id.tv_empty);
        if (emptyImg != -1) {
            ivEmpty.setImageResource(emptyImg);
        }
        if (!TextUtils.isEmpty(emptyMsg)) {
            tvEmpty.setText(emptyMsg);
            tvEmpty.setTextColor(subTextColor);
        }
    }

    private void onStateChange(int state) {
        if (onStateChangeLisenter != null) {
            onStateChangeLisenter.onStateChange(state);
        }
    }

    private void hideAll() {
        if (successView != null)
            successView.setVisibility(View.GONE);
        if (emptyView != null)
            emptyView.setVisibility(View.GONE);
        if (errorView != null)
            errorView.setVisibility(View.GONE);
        if (loadingView != null)
            loadingView.setVisibility(View.GONE);
    }

    public static interface OnRetryClickLisenter {
        void onRetry();
    }

    public static interface OnStateChangeLisenter {
        void onStateChange(int state);
    }
}
