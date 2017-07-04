package com.cesar.weather.domain;


import com.cesar.weather.data.WeatherDataRepository;
import com.cesar.weather.data.WeatherRepository;
import com.cesar.weather.data.pojo.WeatherApiObject;
import com.cesar.weather.domain.utils.ModeMapper;
import com.cesar.weather.ui.model.UICityWeather;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;

public class CityWeatherUseCase extends UseCase {

    private final String cityName;

    public CityWeatherUseCase(String cityName) {
        this.cityName = cityName;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        WeatherRepository repo = WeatherDataRepository.getInstance();
        return repo.townWeather(cityName).map(new Func1<WeatherApiObject, ArrayList<UICityWeather>>() {
            @Override
            public ArrayList<UICityWeather> call(WeatherApiObject weatherApiObject) {
                return  ModeMapper.weatherApi2View(weatherApiObject);
            }
        });
    }
}