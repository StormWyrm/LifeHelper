package com.github.StormWyrm.weather.bean.place

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

class City(
    @SerializedName("name") val cityName: String,
    @SerializedName("id") val cityCode: Int
) : LitePalSupport() {
    @Transient
    val id = 0
    var provinceId = 0
}