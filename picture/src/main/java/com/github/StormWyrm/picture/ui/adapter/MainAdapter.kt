package com.github.StormWyrm.picture.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.picture.R
import com.github.StormWyrm.picture.bean.GankFilterResult

class MainAdapter : BaseQuickAdapter<GankFilterResult, BaseViewHolder>(R.layout.picture_item_main) {
    init {
        openLoadAnimation()
    }

    override fun convert(helper: BaseViewHolder, item: GankFilterResult) {
        Glide.with(mContext)
            .load(item.url)
            .asBitmap()
            .error(R.drawable.picture_img_default_meizi)
            .placeholder(R.drawable.picture_img_default_meizi)
            .centerCrop()
            .into(helper.getView(R.id.iv_image))
    }
}
