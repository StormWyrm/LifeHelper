package com.github.stormwyrm.weather.base;

import com.github.stormwyrm.library.mvvm.AbsRepository;
import com.github.stormwyrm.library.network.RetrofitUtils;
import com.github.stormwyrm.weather.network.WeatherApi;

public class BaseRepository extends AbsRepository {
    protected WeatherApi network;

    public BaseRepository() {
        network = RetrofitUtils.getInstance().createClass(WeatherApi.class, WeatherApi.BASE_URL);
    }
}
