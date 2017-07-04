package com.cesar.weather.domain.utils;

import com.cesar.weather.data.pojo.List;
import com.cesar.weather.data.pojo.WeatherApiObject;
import com.cesar.weather.ui.model.UICityWeather;

import java.util.ArrayList;

/**
 * Created by Cesar on 01/07/2017.
 */

public class ModeMapper {

    public static ArrayList<UICityWeather> weatherApi2View(WeatherApiObject weatherApiObject) {
        ArrayList<UICityWeather> result = new ArrayList<>();

        for (List item : weatherApiObject.getList()) {
            UICityWeather uICityWeather = new UICityWeather();
            uICityWeather.setCityName(weatherApiObject.getCity().getName());
            uICityWeather.setDate(item.getDt());
            uICityWeather.setDescription(item.getWeather().get(0).getDescription());
            uICityWeather.setGroup(item.getWeather().get(0).getMain());
            uICityWeather.setMaxTemp(item.getTemp().getMax());
            uICityWeather.setMinTemp(item.getTemp().getMin());
            uICityWeather.setIcon(item.getWeather().get(0).getIcon());

            result.add(uICityWeather);
        }
        return result;
    }
}
