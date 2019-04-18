package com.github.StormWyrm.gank.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.StormWyrm.gank.R
import com.github.StormWyrm.library.arounter.ARouterConstant
import com.github.StormWyrm.library.base.activity.BaseToolbarActivity

@Route(path = ARouterConstant.ACTIVITY_GANK)
class GankActivity : BaseToolbarActivity() {
    override fun getContainerId(): Int {
        return R.layout.gank_activity_gank
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initToolbar("Gank")
    }
}