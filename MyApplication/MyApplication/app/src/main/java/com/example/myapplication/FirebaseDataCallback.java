package com.example.myapplication;

public interface FirebaseDataCallback  {
    void onDataReceived(double latitude, double longitude, String city, String rainfall);
    void onError(String error);










}