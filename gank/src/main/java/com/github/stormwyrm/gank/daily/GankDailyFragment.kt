package com.github.stormwyrm.gank.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.stormwyrm.gank.R
import com.github.stormwyrm.gank.daily.adapter.GankDailyAdapter
import com.github.stormwyrm.gank.databinding.GankFragmentDailyBinding
import com.github.stormwyrm.library.mvvm.BaseMvvmFragment

class GankDailyFragment : BaseMvvmFragment<GankDailyViewModel>() {
    private lateinit var databinding: GankFragmentDailyBinding
    private lateinit var madapter : GankDailyAdapter

    override fun getLayoutId(): Int {
        return R.layout.gank_fragment_daily
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        databinding.lifecycleOwner = this
        return databinding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        databinding.viewModel = mViewModel


        databinding.rvGankDaily.apply {

        }
    }
}
