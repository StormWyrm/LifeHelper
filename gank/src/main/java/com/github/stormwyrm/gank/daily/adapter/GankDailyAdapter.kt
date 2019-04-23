package com.github.stormwyrm.gank.daily.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.stormwyrm.gank.R
import com.github.stormwyrm.picture.bean.GankFilterResult

class GankDailyAdapter
    : BaseQuickAdapter<GankFilterResult, BaseViewHolder>(R.layout.gank_item_filter) {

    init {
        openLoadAnimation()
    }

    override fun convert(helper: BaseViewHolder?, item: GankFilterResult?) {

    }
}