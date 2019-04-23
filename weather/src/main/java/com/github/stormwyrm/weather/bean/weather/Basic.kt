package com.github.stormwyrm.weather.bean.place

import com.google.gson.annotations.SerializedName

class Basic {
    @SerializedName("city")
    var cityName = ""
    @SerializedName("id")
    var weatherId = ""
    lateinit var update: Update

    inner class Update {
        @SerializedName("loc")
        var updateTime = ""
    }
}