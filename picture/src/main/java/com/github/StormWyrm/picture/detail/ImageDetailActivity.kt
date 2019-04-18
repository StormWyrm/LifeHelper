package com.github.StormWyrm.picture.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.github.StormWyrm.library.base.activity.BaseActivity
import com.github.StormWyrm.picture.R
import kotlinx.android.synthetic.main.picture_activity_image_detail.*

class ImageDetailActivity : BaseActivity() {
    private var gankIndex: Int = 0
    private lateinit var gankList: ArrayList<String>

    override fun getLayoutId(): Int {
        return R.layout.picture_activity_image_detail
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initToolbar("Gallery", true)
        intent.run {
            gankIndex = getIntExtra(EXTRA_GANK_INDEX, 0)
            gankList = getStringArrayListExtra(EXTRA_GANK_LIST)
            if (gankList.isNullOrEmpty()) {
                throw IllegalArgumentException("ganklist is empty !")
            }
        }
        progressBar.max = gankList.size
        progressBar.progress = gankIndex + 1
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        vp.run {
            adapter = ImageDetailAdapter(gankList)
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    progressBar.progress = position + 1
                }
            })
            currentItem = gankIndex
        }
    }

    companion object {
        const val EXTRA_GANK_INDEX = "index"
        const val EXTRA_GANK_LIST = "gank_list"

        fun start(context: Context, index: Int, gankList: ArrayList<String>) {
            val intent = Intent().apply {
                setClass(context,ImageDetailActivity::class.java)
                putExtra(EXTRA_GANK_INDEX, index)
                putStringArrayListExtra(EXTRA_GANK_LIST, gankList)
            }
            context.startActivity(intent)
        }
    }
}
