package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {
    @SerializedName("list")
    private List<com.example.myapplication.ForecastItem> list;

    public List<com.example.myapplication.ForecastItem> getList() {
        return list;
    }
}

