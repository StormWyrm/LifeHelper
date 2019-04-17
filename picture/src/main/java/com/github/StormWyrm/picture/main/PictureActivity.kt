package com.github.StormWyrm.picture.main

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.github.StormWyrm.library.arounter.ARouterConstant
import com.github.StormWyrm.library.mvp.BaseMvpListActivity
import com.github.StormWyrm.picture.R
import com.github.StormWyrm.picture.bean.GankFilterResult
import com.github.StormWyrm.picture.detail.ImageDetailActivity
import com.github.StormWyrm.picture.main.adapter.MainAdapter

@Route(path = ARouterConstant.ACTIVITY_PICTURE)
class PictureActivity : BaseMvpListActivity<PictureContract.Presenter>(), PictureContract.View {
    private var pageNum: Int = 0
    private var pageCount: Int = 20
    private lateinit var mAdapter: MainAdapter

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initToolbar(R.string.picture)
        initRecyclerView()
    }

    override fun initListener() {
        super.initListener()
        mRefreshLayout.setOnLoadMoreListener {
            mPresenter.getImages(pageCount, pageNum)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        onRetryClick()
    }

    override fun onRetryClick() {
        showLoading()
        mPresenter.getImages(pageCount, pageNum)
    }

    override fun initPresenter(): PictureContract.Presenter {
        return PicturePresenter()
    }

    override fun onLoadImagesSuccess(results: List<GankFilterResult>) {
        if (pageNum++ == 0) {
            mAdapter.setNewData(results)
            showContent()
        } else {
            mAdapter.addData(results)
            mRefreshLayout.finishLoadMore()
        }
    }

    private fun initRecyclerView() {
        mAdapter = MainAdapter().apply {
            setOnItemClickListener { _, _, position ->
                val gankList = data.map {
                    it.url
                } as ArrayList<String>
                ImageDetailActivity.start(mContext, position,gankList)
            }
        }
        mRecyclerView.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        Glide.with(context).resumeRequests()
                    } else {
                        Glide.with(context).pauseRequests()
                    }
                }
            })
            layoutManager = GridLayoutManager(mContext, 2)
            adapter = mAdapter
        }
    }
}
