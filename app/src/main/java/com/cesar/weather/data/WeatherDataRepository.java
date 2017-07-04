package com.cesar.weather.data;

import com.cesar.weather.data.pojo.WeatherApiObject;
import com.cesar.weather.domain.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Cesar on 01/07/2017.
 */

public class WeatherDataRepository implements WeatherRepository {

    WeatherAPIService service;

    //singleton pattern
    private static final WeatherDataRepository INSTANCE = new WeatherDataRepository();

    public static WeatherRepository getInstance(){
        return INSTANCE;
    }

    private WeatherDataRepository(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        //Retrofit object with a few amendments to work with RxJava
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.OPEN_WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        //API service
        service = retrofit.create(WeatherAPIService.class);
    }

    @Override
    public Observable<WeatherApiObject> townWeather(String cityName) {
        return service.getTownWeather(cityName,Constants.OPEN_WEATHER_KEY,
                Constants.OPEN_WEATHER_URL_CNT, Constants.OPEN_WEATHER_URL_UNIT);
    }

    @Override
    public Observable<WeatherApiObject> coorWeather(String lat, String lon) {
        return service.getCoorWeather(lat,lon,Constants.OPEN_WEATHER_KEY,
                Constants.OPEN_WEATHER_URL_CNT, Constants.OPEN_WEATHER_URL_UNIT);
    }

}
