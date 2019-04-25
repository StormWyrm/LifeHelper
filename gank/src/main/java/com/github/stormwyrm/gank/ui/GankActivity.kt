package com.github.stormwyrm.gank.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.stormwyrm.gank.R
import com.github.stormwyrm.gank.ext.replaceFragment
import com.github.stormwyrm.gank.ext.transparentStatusBar
import com.github.stormwyrm.gank.ui.about.GankAboutFragment
import com.github.stormwyrm.gank.ui.daily.GankDailyFragment
import com.github.stormwyrm.gank.ui.filter.GankFilterFragment
import com.github.stormwyrm.gank.ui.filter.GankFilterType
import com.github.stormwyrm.library.arounter.ARouterConstant
import com.github.stormwyrm.library.base.activity.BaseActivity
import com.github.stormwyrm.library.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.gank_activity_gank.*

@Route(path = ARouterConstant.ACTIVITY_GANK)
class GankActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.gank_activity_gank
    }

    override fun initStatusBar() {
        super.initStatusBar()
        transparentStatusBar()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initToolbar("Gank")
        initDrawlayout()
        replaceOtherFragment(GankDailyFragment::class.java,GankDailyFragment.TAG)
    }

    override fun initListener() {
        super.initListener()
        navLeftView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_navi_today -> {
                    replaceOtherFragment(GankDailyFragment::class.java,GankDailyFragment.TAG)
                }
                R.id.menu_navi_android -> {
                    filterChange(GankFilterType.ANDROID)
                }
                R.id.menu_navi_ios -> {
                    filterChange(GankFilterType.IOS)
                }
                R.id.menu_navi_app -> {
                    filterChange(GankFilterType.APP)
                }
                R.id.menu_navi_web -> {
                    filterChange(GankFilterType.WEB)
                }
                R.id.menu_navi_extra -> {
                    filterChange(GankFilterType.EXTRA_SOURCES)
                }
                R.id.menu_navi_welfare -> {
//                    filterChange(GankFilterType.WELFARE)
                }
                R.id.menu_navi_about -> {
                    replaceOtherFragment(GankAboutFragment::class.java,GankAboutFragment.TAG)
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


    private fun <T : BaseFragment> replaceOtherFragment(fragmentClass: Class<T>, tag: String) {
        val fragment = supportFragmentManager.findFragmentByTag(tag)
                ?: fragmentClass.newInstance()
        replaceFragment(fragment, R.id.flContainer, tag)
    }

    private fun filterChange(filterType: String) {
        val fragment = supportFragmentManager.findFragmentByTag(filterType)
                ?: GankFilterFragment.newInstance()

        replaceFragment(fragment, R.id.flContainer, filterType)
    }

}