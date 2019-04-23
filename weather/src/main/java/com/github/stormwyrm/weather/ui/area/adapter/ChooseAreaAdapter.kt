package com.github.stormwyrm.weather.ui.area.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.stormwyrm.weather.R

class ChooseAreaAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.weather_item_city, null) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.run {
            item?.let {
                setText(R.id.tvCityName, it)
            }
        }
    }
}