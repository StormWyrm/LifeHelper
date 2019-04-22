package com.github.StormWyrm.gank.ui.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.github.StormWyrm.gank.R
import com.github.StormWyrm.library.mvvm.BaseMvvmFragment

class GankDailyFragment : BaseMvvmFragment<GankDailyViewModel>() {
    private var databinding : com.github.StormWyrm.gank.databinding.GankFragmentDailyBinding = null

    override fun getLayoutId(): Int {
        return R.layout.gank_fragment_daily
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        databinding.lifecycleOwner = this

        return databinding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        databinding.setViewModel(mViewModel)
    }
}
