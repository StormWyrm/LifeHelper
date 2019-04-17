package com.github.StormWyrm.weather.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.StormWyrm.library.arounter.ARouterConstant
import com.github.StormWyrm.library.base.activity.BaseActivity
import com.github.StormWyrm.weather.R
import com.github.StormWyrm.weather.ui.weather.WeatherActivity
import com.github.StormWyrm.weather.ui.weather.WeatherViewModel

@Route(path = ARouterConstant.ACTIVITY_WEATHER)
class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        if (KEY.isNullOrEmpty()) {
            AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_title)
                    .setTitle(R.string.dialog_content)
                    .setPositiveButton(R.string.dialog_ok) { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .create()
        } else {
            val viewModel =
                    ViewModelProviders.of(this).get(WeatherViewModel::class.java)
            val weatherCached = viewModel.isWeatherCached()
            if (weatherCached) {
                val intent = Intent(this, WeatherActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        // 请求天气API的Key，请到http://guolin.tech/api/weather/register申请免费的Key
        const val KEY = "45dd25f63300445e967b461d2037e4f9"
    }
}