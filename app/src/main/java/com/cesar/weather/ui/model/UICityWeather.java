package com.cesar.weather.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UICityWeather implements Parcelable {

    private String cityName;
    private Double maxTemp;
    private Double minTemp;
    private String description;
    private String icon;
    private int date;
    private String group;

    public UICityWeather(){}

    private UICityWeather(Parcel in){
        this.cityName = in.readString();
        this.maxTemp = in.readDouble();
        this.minTemp = in.readDouble();
        this.description = in.readString();
        this.icon = in.readString();
        this.date = in.readInt();
        this.group = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cityName);
        parcel.writeDouble(maxTemp);
        parcel.writeDouble(minTemp);
        parcel.writeString(description);
        parcel.writeString(icon);;
        parcel.writeInt(date);
        parcel.writeString(group);
    }

    public static final Parcelable.Creator<UICityWeather> CREATOR = new Parcelable.Creator<UICityWeather>(){
        public UICityWeather createFromParcel(Parcel in){
            return new UICityWeather(in);
        }

        public UICityWeather[] newArray(int size){
            return new UICityWeather[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
