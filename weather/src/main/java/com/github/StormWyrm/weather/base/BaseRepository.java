package com.github.StormWyrm.weather.base;

import com.github.StormWyrm.library.mvvm.AbsRepository;
import com.github.StormWyrm.library.network.RetrofitUtils;
import com.github.StormWyrm.weather.network.WeatherApi;

public class BaseRepository extends AbsRepository {
    protected WeatherApi network;

    public BaseRepository() {
        network = RetrofitUtils.getInstance().createClass(WeatherApi.class, WeatherApi.BASE_URL);
    }
}
