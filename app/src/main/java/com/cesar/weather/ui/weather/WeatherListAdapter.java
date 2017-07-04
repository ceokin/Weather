package com.cesar.weather.ui.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesar.weather.R;
import com.cesar.weather.ui.model.UICityWeather;

import java.util.List;

/**
 * Created by Cesar on 01/07/2017.
 */


public class WeatherListAdapter extends RecyclerView.Adapter<WeatherItemViewHolder> {

    private Context context;
    private List<UICityWeather> weatherItemList;

    public WeatherListAdapter(Context context, List<UICityWeather> weatherItemList) {
        this.context = context;
        this.weatherItemList = weatherItemList;
    }

    @Override
    public WeatherItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card, parent, false);
        final WeatherItemViewHolder weatherItemViewHolder = new WeatherItemViewHolder(view);
        return weatherItemViewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherItemViewHolder holder, int position) {
        holder.bind(weatherItemList.get(position), context);
    }

    @Override
    public int getItemCount() {
        return weatherItemList.size();
    }
}
