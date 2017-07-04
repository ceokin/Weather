package com.cesar.weather.domain;

import com.cesar.weather.data.WeatherDataRepository;
import com.cesar.weather.data.WeatherRepository;
import com.cesar.weather.data.pojo.WeatherApiObject;
import com.cesar.weather.domain.utils.ModeMapper;
import com.cesar.weather.ui.model.UICityWeather;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Cesar on 01/07/2017.
 */

public class CoorWeatherUseCase extends UseCase{

    private final String lon;
    private final String lat;

    public CoorWeatherUseCase(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        WeatherRepository repo = WeatherDataRepository.getInstance();
        return repo.coorWeather(lon, lat).map(new Func1<WeatherApiObject, ArrayList<UICityWeather>>() {
            @Override
            public ArrayList<UICityWeather> call(WeatherApiObject weatherApiObject) {
                return  ModeMapper.weatherApi2View(weatherApiObject);
            }
        });
    }
}
