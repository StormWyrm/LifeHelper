package com.github.stormwyrm.gank.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.stormwyrm.gank.R
import com.github.stormwyrm.library.arounter.ARouterConstant
import com.github.stormwyrm.library.base.activity.BaseActivity
import kotlinx.android.synthetic.main.gank_activity_gank.*

@Route(path = ARouterConstant.ACTIVITY_GANK)
class GankActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.gank_activity_gank
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initToolbar("Gank")
        initDrawlayout()
    }

    override fun initListener() {
        super.initListener()
        navLeftView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_navi_today -> {

                }
                R.id.menu_navi_android -> {

                }
                R.id.menu_navi_ios -> {
                }
                R.id.menu_navi_app -> {
                }
                R.id.menu_navi_web -> {
                }
                R.id.menu_navi_extra -> {
                }
                R.id.menu_navi_welfare -> {
                }
                R.id.menu_navi_about -> {
                }
            }
            true
        }
    }

    private fun initDrawlayout() {
        val toggle = object : ActionBarDrawerToggle(this, drawLayout, toolbar, 0, 0) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }.apply {
            syncState()//将开关和侧边栏关联
        }
        drawLayout.addDrawerListener(toggle)
    }
}