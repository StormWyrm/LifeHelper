package com.github.StormWyrm.lifehelper

import com.github.StormWyrm.library.arounter.ARouterConstant
import com.github.StormWyrm.library.arounter.ARouterUtils
import com.github.StormWyrm.library.base.activity.BaseActivity
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