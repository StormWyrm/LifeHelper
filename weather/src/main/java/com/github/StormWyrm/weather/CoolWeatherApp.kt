package com.github.StormWyrm.weather

import android.content.Context
import com.github.StormWyrm.library.util.LibApplication
import org.litepal.LitePal

class CoolWeatherApp : LibApplication() {

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}