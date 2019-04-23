package com.github.stormwyrm.weather.ui.area

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.stormwyrm.library.arounter.ARouterConstant
import com.github.stormwyrm.library.base.activity.BaseActivity
import com.github.stormwyrm.weather.R
import com.github.stormwyrm.weather.ui.weather.WeatherActivity
import com.github.stormwyrm.weather.ui.weather.WeatherViewModel

@Route(path = ARouterConstant.ACTIVITY_WEATHER)
class ChooseAreaActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.weather_activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        if (KEY.isNullOrEmpty()) {
            AlertDialog.Builder(this)
                    .setTitle(R.string.weather_dialog_title)
                    .setMessage(R.string.weather_dialog_message)
                    .setPositiveButton(R.string.weather_dialog_confirm) { dialogInterface: DialogInterface, _: Int ->
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
                finish()
            }
        }
    }

    companion object {
        // 请求天气API的Key，请到http://guolin.tech/api/weather/register申请免费的Key
        const val KEY = "45dd25f63300445e967b461d2037e4f9"
    }
}