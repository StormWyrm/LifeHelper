package com.github.stormwyrm.lifehelper

import com.github.stormwyrm.library.arounter.ARouterConstant
import com.github.stormwyrm.library.arounter.ARouterUtils
import com.github.stormwyrm.library.base.activity.BaseActivity
import kotlinx.android.synthetic.main.app_activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.app_activity_main
    }

    override fun initListener() {
        super.initListener()
        btnPicture.setOnClickListener {
            ARouterUtils.navigation(ARouterConstant.ACTIVITY_PICTURE,
                    this@MainActivity,
                    ARouterUtils.callback)
        }
        btnWeather.setOnClickListener {
            ARouterUtils.navigation(
                    ARouterConstant.ACTIVITY_WEATHER,
                    this@MainActivity,
                    ARouterUtils.callback)
        }
        btnGank.setOnClickListener {
            ARouterUtils.navigation(ARouterConstant.ACTIVITY_GANK,
                    this@MainActivity,
                    ARouterUtils.callback)
        }
    }
}