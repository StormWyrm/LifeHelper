package com.github.StormWyrm.picture.detail

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.github.StormWyrm.picture.R
import com.github.chrisbanes.photoview.PhotoView

class ImageDetailAdapter(val imageList: ArrayList<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return PhotoView(container.context).apply {
            val layoutParams = ViewPager.LayoutParams().apply {
                width = MATCH_PARENT
                height = MATCH_PARENT
            }
            setLayoutParams(layoutParams)
            Glide.with(context)
                .load(imageList[position])
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.picture_img_default_meizi)
                .error(R.drawable.picture_img_default_meizi)
                .into(this)
            container.addView(this)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (`object` is PhotoView) {
            container.removeView(`object`)
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}