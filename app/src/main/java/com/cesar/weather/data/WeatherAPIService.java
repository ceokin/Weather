package com.cesar.weather.data;

import com.cesar.weather.data.pojo.WeatherApiObject;
import com.cesar.weather.domain.utils.Constants;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Cesar on 01/07/2017.
 */

public interface WeatherAPIService {
    //RxAndroid
    @GET(Constants.OPEN_WEATHER_URL_GET_WEATHER)
    Observable<WeatherApiObject> getTownWeather(@Query("q") String cityName,
                                                @Query("APPID") String appId,
                                                @Query("cnt") String cnt,
                                                @Query("units") String metric);

    @GET(Constants.OPEN_WEATHER_URL_GET_WEATHER)
    Observable<WeatherApiObject> getCoorWeather(@Query("lat") String lat,
                                                @Query("lon") String lon,
                                                @Query("APPID") String appId,
                                                @Query("cnt") String cnt,
                                                @Query("units") String metric);
}
