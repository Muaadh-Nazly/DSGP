package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class UserLocation extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    TextView country, city, address, longitude, latitude;
    Button getLocation;
    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        longitude = findViewById(R.id.location);
        latitude = findViewById(R.id.lagitude);
        getLocation = findViewById(R.id.get_location_btn);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });


    }
    public void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
                                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                //List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    if (addresses != null && addresses.size() > 0) {
                                        Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
                                        Log.d("Location", "Country: " + addresses.get(0).getCountryName());
                                        Log.d("Location", "Address: " + addresses.get(0).getAddressLine(0));
                                        city.setText("City: " + addresses.get(0).getLocality());
                                        district.setText("District: " + addresses.get(0).getSubAdminArea());

                                        getWeatherData(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                                        NearbyCities nearbyCities = getNearbyLocationNames(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                                        updateNearbyCitiesUI(nearbyCities.getCity1(), nearbyCities.getCity2());
                                    } else {
                                        Log.e("Location", "No address found for the location");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                processWeatherData(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle failure to get last location
                            Log.e("Location", "Failed to get last location: " + e.getMessage());
                        }
                    });
        } else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface WeatherApi {
        @GET("data/2.5/weather")
        Call<JsonObject> getCurrentWeather(
                @Query("lat") double latitude,
                @Query("lon") double longitude,
                @Query("appid") String apiKey
        );

        @GET("data/2.5/forecast")
        Call<JsonObject> getWeatherForecast(
                @Query("lat") double latitude,
                @Query("lon") double longitude,
                @Query("appid") String apiKey
        );
    }

    public void getWeatherData (double latitude, double longitude){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        String apiKey = "3747c6412373b23436ca611b8963f61c";

        Call<JsonObject> currentWeatherCall = weatherApi.getCurrentWeather(latitude, longitude, apiKey);
        currentWeatherCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject weatherJson = response.body();
                    Log.d("WeatherData", "Current Weather JSON: " + weatherJson);
                    double currentWindSpeed = getCurrentWindSpeed(weatherJson);
                    double currentRainfall = getCurrentRainfall(weatherJson);
                    Log.d("WeatherData", "Current Wind Speed: " + currentWindSpeed + " m/s");
                    Log.d("WeatherData", "Current Rainfall: " + currentRainfall + " mm");

                    Call<JsonObject> forecastCall = weatherApi.getWeatherForecast(latitude, longitude, apiKey);
                    forecastCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> forecastResponse) {
                            if (forecastResponse.isSuccessful()) {
                                JsonObject forecastJson = forecastResponse.body();
                                Log.d("WeatherData", "Forecast JSON: " + forecastJson);

                                if (forecastJson != null && forecastJson.has("list")) {
                                    JsonArray forecastList = forecastJson.getAsJsonArray("list");

                                    List<Double> dailyWindSpeeds = new ArrayList<>();
                                    List<Double> dailyRainfalls = new ArrayList<>();

                                    for (int i = 0; i < Math.min(3, forecastList.size()); i++) {
                                        JsonObject dayForecast = forecastList.get(i).getAsJsonObject();

                                        if (dayForecast.has("wind")) {
                                            JsonObject windJson = dayForecast.getAsJsonObject("wind");
                                            if (windJson.has("speed")) {
                                                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();
                                                double windSpeedInMilesPerHour = windSpeedInMetersPerSecond * 2.23694;
                                                dailyWindSpeeds.add(windSpeedInMilesPerHour);
                                            }
                                        } else {
                                            dailyWindSpeeds.add(0.0);
                                        }

                                    }

                                    parseAndPredict(currentWindSpeed, currentRainfall,dailyWindSpeeds);
                                } else {
                                    Toast.makeText(MainActivity.this, "Forecast data is empty", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Failed to fetch forecast data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch current weather data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

    }