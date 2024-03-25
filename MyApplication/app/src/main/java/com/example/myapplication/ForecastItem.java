package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class ForecastItem {
    @SerializedName("dt_txt")
    private String dateTime;

    @SerializedName("wind")
    private WeatherResponse.Wind wind;

    @SerializedName("rain")
    private WeatherResponse.Rain rain;

    public String getDateTime() {
        return dateTime;
    }

    public WeatherResponse.Wind getWind() {
        return wind;
    }

    public WeatherResponse.Rain getRain() {
        return rain;
    }
}
