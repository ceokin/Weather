package com.cesar.weather.data;

import com.cesar.weather.data.pojo.WeatherApiObject;

import rx.Observable;

/**
 * Created by Cesar on 01/07/2017.
 */

public interface WeatherRepository {
    Observable<WeatherApiObject> townWeather(String cityName);
    Observable<WeatherApiObject> coorWeather(String lat, String lon);
}
