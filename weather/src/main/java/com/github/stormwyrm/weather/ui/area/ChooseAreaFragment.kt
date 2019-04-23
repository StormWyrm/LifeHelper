package com.github.stormwyrm.weather.ui.area

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.stormwyrm.library.livedata.LiveDataBus
import com.github.stormwyrm.library.mvvm.BaseMvvmLoadFragment
import com.github.stormwyrm.weather.R
import com.github.stormwyrm.weather.bean.place.City
import com.github.stormwyrm.weather.bean.place.County
import com.github.stormwyrm.weather.bean.place.Province
import com.github.stormwyrm.weather.config.Constants
import com.github.stormwyrm.weather.ui.area.adapter.ChooseAreaAdapter
import com.github.stormwyrm.weather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.weather_activity_weather.*
import kotlinx.android.synthetic.main.weather_fragment_choose_area.*

class ChooseAreaFragment : BaseMvvmLoadFragment<ChooseAreaViewModel>() {
    private lateinit var mAdapter: ChooseAreaAdapter

    override fun getStateEventTag(): String = ""

    override fun getStateEventKey(): Any = Constants.EVENT_KEY_CHOOSE_AREA_STATE

    override fun getChildLayoutId(): Int = R.layout.weather_fragment_choose_area

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mAdapter = ChooseAreaAdapter().apply {
            setOnItemClickListener { _, _, position ->
                when {
                    mViewModel.currentLevel == LEVEL_PROVINCE -> {
                        mViewModel.selectedProvince = mViewModel.provinceList?.get(position)?.apply {
                            mViewModel.getCityList(this.provinceCode)
                        }
                    }
                    mViewModel.currentLevel == LEVEL_CITY -> {
                        mViewModel.selectedCity = mViewModel.cityList?.get(position)?.apply {
                            val privinceCode = mViewModel.selectedProvince?.provinceCode
                            mViewModel.getCountyList(privinceCode!!, cityCode)
                        }
                    }
                    mViewModel.currentLevel == LEVEL_COUNTY -> {
                        val weatherId = mViewModel.countyList!![position].weatherId
                        if (mActivity is ChooseAreaActivity) {
                            val intent = Intent(activity, WeatherActivity::class.java)
                            intent.putExtra("weather_id", weatherId)
                            startActivity(intent)
                            mActivity.finish()
                        } else if (activity is WeatherActivity) {
                            val weatherActivity = activity as WeatherActivity
                            weatherActivity.drawerLayout.closeDrawers()
                            weatherActivity.swipeRefresh.isRefreshing = true
                            weatherActivity.refreshWeather(weatherId)
                        }
                    }
                }
            }
        }
        recyclerView.run {
            layoutManager = LinearLayoutManager(mActivity)
            addItemDecoration(DividerItemDecoration(mActivity, RecyclerView.HORIZONTAL))
            adapter = mAdapter
        }
    }

    override fun initLisenter() {
        super.initLisenter()
        back_button.setOnClickListener {
            if (mViewModel.currentLevel == LEVEL_COUNTY) {
                mViewModel.run {
                    selectedCity.let {
                        getCityList(it?.cityCode!!)
                    }
                }
            } else if (mViewModel.currentLevel == LEVEL_CITY) {
                mViewModel.run {
                    selectedCity.let {
                        getProvinceList()
                    }
                }
            }
        }
    }

    override fun lazyLoad() {
        super.lazyLoad()
        if (mViewModel.dataList.isEmpty()) {
            mViewModel.getProvinceList()
        } else {

        }
    }

    override fun dataObserver() {
        super.dataObserver()
        LiveDataBus.getDefault()
                .subscribe<List<Province>>(Constants.EVENT_KEY_CHOOSE_AREA_PROVICE)
                .observe(this, Observer {
                    title_text.text = "中国"
                    back_button.visibility = View.GONE
                    mViewModel.dataList.clear()
                    mViewModel.dataList.addAll(it.map { province ->
                        province.provinceName
                    })
                    mViewModel.provinceList = it
                    mViewModel.currentLevel = LEVEL_PROVINCE
                    mAdapter.setNewData(mViewModel.dataList)
                })

        LiveDataBus.getDefault()
                .subscribe<List<City>>(Constants.EVENT_KEY_CHOOSE_AREA_CITY)
                .observe(this, Observer {
                    back_button.visibility = View.VISIBLE
                    title_text.text = mViewModel.selectedProvince?.provinceName
                    mViewModel.dataList.clear()
                    mViewModel.dataList.addAll(it.map { city -> city.cityName })
                    mViewModel.cityList = it
                    mViewModel.currentLevel = LEVEL_CITY
                    mAdapter.setNewData(mViewModel.dataList)
                })

        LiveDataBus.getDefault()
                .subscribe<List<County>>(Constants.EVENT_KEY_CHOOSE_AREA_COUNTRY)
                .observe(this, Observer {
                    back_button.visibility = View.VISIBLE
                    title_text.text = mViewModel.selectedCity?.cityName
                    mViewModel.dataList.clear()
                    mViewModel.dataList.addAll(it.map { county -> county.countyName })
                    mViewModel.countyList = it
                    mViewModel.currentLevel = LEVEL_COUNTY
                    mAdapter.setNewData(mViewModel.dataList)
                })
    }

    companion object {
        const val LEVEL_PROVINCE = 0
        const val LEVEL_CITY = 1
        const val LEVEL_COUNTY = 2
    }
}
