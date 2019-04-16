package com.github.StormWyrm.weather.bean.place

import com.google.gson.annotations.SerializedName

class Now {
    @SerializedName("tmp")
    var temperature = ""
    @SerializedName("cond")
    lateinit var more: More

    inner class More {
        @SerializedName("txt")
        var info = ""
    }
}