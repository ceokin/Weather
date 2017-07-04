package com.cesar.weather.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Cesar on 04/07/2017.
 */

public class SaveCityUseCase {

    private static final String LIST_NAME = "cityList";

    private Context context;

    public SaveCityUseCase(Context context){
        this.context = context;
    }

    public void save(String city){
        List<String> objects = getCities();
        objects.add(city);

        Gson gson = new Gson();
        String jsonList = gson.toJson(objects);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LIST_NAME, jsonList);
        editor.commit();
    }

    public List<String> getCities(){
        Gson gson = new Gson();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String savedList = preferences.getString(LIST_NAME, "nothing");
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> objects = gson.fromJson(savedList, type);

        return  objects;
    }
}
