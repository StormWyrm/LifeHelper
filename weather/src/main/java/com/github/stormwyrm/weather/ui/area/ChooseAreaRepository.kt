package com.github.stormwyrm.weather.ui.area

import com.github.stormwyrm.library.livedata.LiveDataBus
import com.github.stormwyrm.library.rx.convert.ExceptionConvert
import com.github.stormwyrm.library.rx.observer.BaseObserver
import com.github.stormwyrm.library.rx.scheduler.RxScheduler
import com.github.stormwyrm.library.state.IStateView
import com.github.stormwyrm.weather.base.BaseRepository
import com.github.stormwyrm.weather.bean.place.City
import com.github.stormwyrm.weather.bean.place.County
import com.github.stormwyrm.weather.bean.place.Province
import com.github.stormwyrm.weather.config.Constants
import com.github.stormwyrm.weather.db.PlaceDao
import com.github.stormwyrm.weather.util.CoolWeatherExecutors

class ChooseAreaRepository : BaseRepository() {

    private val placeDao: PlaceDao by lazy {
        PlaceDao()
    }

    fun getProvinceList() {
        LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_STATE, IStateView.STATE_LOADING)
        CoolWeatherExecutors.diskIO.execute {
            val list = placeDao.getProvinceList()
            if (list.isNullOrEmpty()) {
                addDisposable(
                    network.getProvinces()
                        .onErrorResumeNext(ExceptionConvert())
                        .compose(RxScheduler.ioToMain())
                        .subscribeWith(object : BaseObserver<List<Province>>() {
                            override fun onSuccess(t: List<Province>?) {
                                t?.let {
                                    placeDao.saveProvinceList(it)
                                    LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_CHOOSE_AREA_PROVICE, t)
                                    LiveDataBus.getDefault()
                                        .postEvent(Constants.EVENT_KEY_CHOOSE_AREA_STATE, IStateView.STATE_SUCCESS)
                                } ?: LiveDataBus.getDefault().postEvent(
                                    Constants.EVENT_KEY_CHOOSE_AREA_STATE,
                                    IStateView.STATE_EMPTY
                                )
                            }

                            override fun onFailure(t: Throwable?, msg: String?) {
                                LiveDataBus.getDefault()
                                    .postEvent(Constants.EVENT_KEY_CHOOSE_AREA_STATE, IStateView.STATE_ERROR)
                            }
                        })
                )
            } else {
                LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_CHOOSE_AREA_PROVICE, list)
                LiveDataBus.getDefault()
                    .postEvent(Constants.EVENT_KEY_WEATHER_STATE, IStateView.STATE_SUCCESS)            }
        }
    }

    fun getCityList(provinceId: Int) {
        LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_STATE, IStateView.STATE_LOADING)
        CoolWeatherExecutors.diskIO.execute {
            val list = placeDao.getCityList(provinceId)
            if (list.isNullOrEmpty()) {
                addDisposable(
                    network.getCities(provinceId)
                        .onErrorResumeNext(ExceptionConvert())
                        .compose(RxScheduler.ioToMain())
                        .subscribeWith(object : BaseObserver<List<City>>() {
                            override fun onSuccess(t: List<City>?) {
                                t?.let {
                                    placeDao.saveCityList(it)
                                    LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_CHOOSE_AREA_CITY, t)
                                    LiveDataBus.getDefault()
                                        .postEvent(Constants.EVENT_KEY_CHOOSE_AREA_STATE, IStateView.STATE_SUCCESS)
                                } ?: LiveDataBus.getDefault().postEvent(
                                    Constants.EVENT_KEY_CHOOSE_AREA_STATE,
                                    IStateView.STATE_EMPTY
                                )
                            }

                            override fun onFailure(t: Throwable?, msg: String?) {
                                LiveDataBus.getDefault()
                                    .postEvent(Constants.EVENT_KEY_CHOOSE_AREA_STATE, IStateView.STATE_LOADING)
                            }
                        })
                )
            } else {
                LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_CHOOSE_AREA_CITY, list)
            }
        }
    }

    fun getCountyList(provinceId: Int, cityId: Int) {
        LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_WEATHER_STATE, IStateView.STATE_LOADING)
        CoolWeatherExecutors.diskIO.execute {
            val list = placeDao.getCountyList(cityId)
            if (list.isNullOrEmpty()) {
                addDisposable(
                    network.getCounties(provinceId, cityId)
                        .onErrorResumeNext(ExceptionConvert())
                        .compose(RxScheduler.ioToMain())
                        .subscribeWith(object : BaseObserver<List<County>>() {
                            override fun onSuccess(t: List<County>?) {
                                t?.let {
                                    placeDao.saveCountyList(it)
                                    LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_CHOOSE_AREA_COUNTRY, t)
                                    LiveDataBus.getDefault()
                                        .postEvent(Constants.EVENT_KEY_CHOOSE_AREA_STATE, IStateView.STATE_SUCCESS)
                                } ?: LiveDataBus.getDefault().postEvent(
                                    Constants.EVENT_KEY_WEATHER_STATE,
                                    IStateView.STATE_EMPTY
                                )
                            }

                            override fun onFailure(t: Throwable?, msg: String?) {
                                LiveDataBus.getDefault()
                                    .postEvent(Constants.EVENT_KEY_CHOOSE_AREA_STATE, IStateView.STATE_LOADING)
                            }
                        })
                )
            } else {
                LiveDataBus.getDefault().postEvent(Constants.EVENT_KEY_CHOOSE_AREA_COUNTRY, list)
            }
        }
    }
}