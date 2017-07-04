package com.cesar.weather.ui.weather;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesar.weather.R;
import com.cesar.weather.ui.model.UICityWeather;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 01/07/2017.
 */

public class WeatherItemViewHolder extends RecyclerView.ViewHolder {

    Context context;

    @BindView(R.id.item_card_view) CardView cardViewCard;
    @BindView(R.id.day_text_view) TextView dateTextView;
    @BindView(R.id.icon_imageView) ImageView iconImageView;
    @BindView(R.id.max_textView) TextView maxTextView;
    @BindView(R.id.min_textView) TextView minTextView;
    @BindView(R.id.group_textView) TextView groupTextView;
    @BindView(R.id.description_textView) TextView descriptionTextView;

    public WeatherItemViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final UICityWeather item, Context context) {
        this.context = context;
        dateTextView.setText(getWeekDay(item.getDate()));
        selectIcon(item.getIcon());
        maxTextView.setText(String.valueOf(item.getMaxTemp().intValue()));
        minTextView.setText(String.valueOf(item.getMinTemp().intValue()));
        groupTextView.setText(String.valueOf(item.getGroup()));
        descriptionTextView.setText(item.getDescription());
    }

    public String getWeekDay(int date){
        int newDate = new Date((long) date * 1000).getDate();
        String weekDay;
        switch(newDate){
            case 0:
                weekDay = context.getString(R.string.sanday);
                break;
            case 1:
                weekDay = context.getString(R.string.monday);
                break;
            case 2:
                weekDay = context.getString(R.string.tuesday);
                break;
            case 3:
                weekDay = context.getString(R.string.wednesday);
                break;
            case 4:
                weekDay = context.getString(R.string.thursday);
                break;
            case 5:
                weekDay = context.getString(R.string.friday);
                break;
            case 6:
                weekDay = context.getString(R.string.saturday);
                break;
            default:
                weekDay = "";
        }
        return weekDay;
    }

    public void selectIcon(String name){
        switch (name) {
            case "01d":
                iconImageView.setImageResource(R.drawable.forecast_01d);
                break;
            case "01n":
                iconImageView.setImageResource(R.drawable.forecast_01n);
                break;
            case "02d":
                iconImageView.setImageResource(R.drawable.forecast_02d);
                break;
            case "02n":
                iconImageView.setImageResource(R.drawable.forecast_02n);
                break;
            case "03d":
            case "03n":
                iconImageView.setImageResource(R.drawable.forecast_03);
                break;
            case "04d":
            case "04n":
                iconImageView.setImageResource(R.drawable.forecast_04);
                break;
            case "09d":
            case "09n":
                iconImageView.setImageResource(R.drawable.forecast_09);
                break;
            case "10d":
                iconImageView.setImageResource(R.drawable.forecast_10d);
                break;
            case "10n":
                iconImageView.setImageResource(R.drawable.forecast_10n);
                break;
            case "11d":
            case "11n":
                iconImageView.setImageResource(R.drawable.forecast_11);
                break;
            case "13d":
            case "13n":
                iconImageView.setImageResource(R.drawable.forecast_13);
                break;
            case "50d":
            case "50n":
                iconImageView.setImageResource(R.drawable.forecast_50);
                break;
            default:
                throw new IllegalArgumentException("Invalid forecast icon: " + name);
        }
    }
}
