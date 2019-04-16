package com.github.StormWyrm.weather.ui.area.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.StormWyrm.weather.R

class ChooseAreaAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_city, null) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.run {
            item?.let {
                setText(R.id.tvCityName, it)
            }
        }
    }
}