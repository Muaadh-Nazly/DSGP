package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
//
//public class UserLocation extends AppCompatActivity {
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    TextView country, city, district, address, latitude, longitude;
//    Button getLocation;
//    private TextView windSpeedTextView, rainfallTextView;
//    private final static int REQUEST_CODE = 100;
//    private WeatherApi weatherApi;
//    private Retrofit retrofit;
//    private List<Address> addresses;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user_location);
//
//        Log.d("******************************","Loaded 1");
//
//        country = findViewById(R.id.country);
//        city = findViewById(R.id.city);
//        address = findViewById(R.id.address);
//        longitude = findViewById(R.id.longitude);
//        latitude = findViewById(R.id.latitude);
//        getLocation = findViewById(R.id.get_location_btn);
//
//        Log.d("******************************","Loaded 2");
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        Log.d("******************************","Loaded 3");
//
//
//        getLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("******************************","Loaded 4");
//
//                getLastLocation();
//                Log.d("******************************","getLastLocation");
//
//
//            }
//        });
//
//
//    }
//
//    public void getLastLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            fusedLocationProviderClient.getLastLocation()
//                    .addOnSuccessListener(new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if (location != null) {
//                                Log.d("********************************************************Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
//                                Geocoder geocoder = new Geocoder(UserLocation.this, Locale.getDefault());
//                                //List<Address> addresses = null;
//                                try {
//                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//                                    if (addresses != null && addresses.size() > 0) {
//                                        Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
//                                        Log.d("Location", "Country: " + addresses.get(0).getCountryName());
//                                        Log.d("Location", "Address: " + addresses.get(0).getAddressLine(0));
//
//                                        Log.d("***************************************** ", "before setting"  +String.valueOf(city));
//
//
////                                        city.setText("City: " + addresses.get(0).getLocality());
//                                        city.setText("Maharagama");
//                                        //district.setText("District: " + addresses.get(0).getSubAdminArea());
//
//                                        getWeatherData(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//
//                                        NearbyCities nearbyCities = getNearbyLocationNames(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//                                        updateNearbyCitiesUI(nearbyCities.getCity1(), nearbyCities.getCity2());
//                                    } else {
//                                        Log.e("Location", "No address found for the location");
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                //processWeatherData(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//                            }
//
//                            else {
//                                Log.d("**************************", String.valueOf(location));
//                            }
//
//                        }
//
//
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // Handle failure to get last location
//                            Log.e("Location", "Failed to get last location: " + e.getMessage());
//                        }
//                    });
//        } else {
//            askPermission();
//        }
//    }
//
//    private void askPermission() {
//        ActivityCompat.requestPermissions(UserLocation.this, new String[]
//                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
//            } else {
//                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private NearbyCities getNearbyLocationNames(double latitude, double longitude) {
//        double latitudeOffset = 0.01;
//        double longitudeOffset = 0.02;
//
//        double nearbyLatitude1 = latitude + latitudeOffset;
//        double nearbyLongitude1 = longitude + longitudeOffset;
//
//        double nearbyLatitude2 = latitude - latitudeOffset;
//        double nearbyLongitude2 = longitude - longitudeOffset;
//
//        Geocoder geocoder = new Geocoder(UserLocation.this, Locale.getDefault());
//
//        try {
//            List<Address> nearbyAddresses1 = geocoder.getFromLocation(nearbyLatitude1, nearbyLongitude1, 1);
//            if (nearbyAddresses1 != null && nearbyAddresses1.size() > 0) {
//                String nearbyCity1 = nearbyAddresses1.get(0).getLocality();
//                Log.d("NearbyLocation", "Nearby City 1: " + nearbyCity1);
//
//                List<Address> nearbyAddresses2 = geocoder.getFromLocation(nearbyLatitude2, nearbyLongitude2, 1);
//                if (nearbyAddresses2 != null && nearbyAddresses2.size() > 0) {
//                    String nearbyCity2 = nearbyAddresses2.get(0).getLocality();
//                    if (!nearbyCity2.equals(nearbyCity1)) {
//                        Log.d("NearbyLocation", "Nearby City 2: " + nearbyCity2);
//                        return new NearbyCities(nearbyCity1, nearbyCity2);
//                    } else {
//                        Log.e("NearbyLocation", "City 2 is the same as City 1");
//                    }
//                } else {
//                    Log.e("NearbyLocation", "One or both of the cities is null");
//                }
//            } else {
//                Log.e("NearbyLocation", "No nearby addresses found for City 2");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new NearbyCities("", "");
//    }
//
//    private void updateNearbyCitiesUI(String city1, String city2) {
//        TextView nearbyCity1TextView = findViewById(R.id.nearbyCity1TextView);
//        TextView nearbyCity2TextView = findViewById(R.id.nearbyCity2TextView);
//
//        // Update UI with the two nearby cities
//        nearbyCity1TextView.setText("Nearby City 1: " + city1);
//        nearbyCity2TextView.setText("Nearby City 2: " + city2);
//    }
//
//    public interface WeatherApi {
//        @GET("data/2.5/weather")
//        Call<JsonObject> getCurrentWeather(
//                @Query("lat") double latitude,
//                @Query("lon") double longitude,
//                @Query("appid") String apiKey
//        );
//
//        @GET("data/2.5/forecast")
//        Call<JsonObject> getWeatherForecast(
//                @Query("lat") double latitude,
//                @Query("lon") double longitude,
//                @Query("appid") String apiKey
//        );
//    }
//
//    public void getWeatherData(double latitude, double longitude) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
//        String apiKey = "3747c6412373b23436ca611b8963f61c";
//
//        Call<JsonObject> currentWeatherCall = weatherApi.getCurrentWeather(latitude, longitude, apiKey);
//        currentWeatherCall.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    JsonObject weatherJson = response.body();
//                    Log.d("WeatherData", "Current Weather JSON: " + weatherJson);
//                    double currentWindSpeed = getCurrentWindSpeed(weatherJson);
//                    double currentRainfall = getCurrentRainfall(weatherJson);
//                    Log.d("WeatherData", "Current Wind Speed: " + currentWindSpeed + " m/s");
//                    Log.d("WeatherData", "Current Rainfall: " + currentRainfall + " mm");
//
//                    Call<JsonObject> forecastCall = weatherApi.getWeatherForecast(latitude, longitude, apiKey);
//                    forecastCall.enqueue(new Callback<JsonObject>() {
//                        @Override
//                        public void onResponse(Call<JsonObject> call, Response<JsonObject> forecastResponse) {
//                            if (forecastResponse.isSuccessful()) {
//                                JsonObject forecastJson = forecastResponse.body();
//                                Log.d("WeatherData", "Forecast JSON: " + forecastJson);
//
//                                if (forecastJson != null && forecastJson.has("list")) {
//                                    JsonArray forecastList = forecastJson.getAsJsonArray("list");
//
//                                    List<Double> dailyWindSpeeds = new ArrayList<>();
//                                    List<Double> dailyRainfalls = new ArrayList<>();
//
//                                    for (int i = 0; i < Math.min(3, forecastList.size()); i++) {
//                                        JsonObject dayForecast = forecastList.get(i).getAsJsonObject();
//
//                                        if (dayForecast.has("wind")) {
//                                            JsonObject windJson = dayForecast.getAsJsonObject("wind");
//                                            if (windJson.has("speed")) {
//                                                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();
//                                                double windSpeedInMilesPerHour = windSpeedInMetersPerSecond * 2.23694;
//                                                dailyWindSpeeds.add(windSpeedInMilesPerHour);
//                                            }
//                                        } else {
//                                            dailyWindSpeeds.add(0.0);
//                                        }
//
//                                    }
//
//                                    parseAndPredict(currentWindSpeed, currentRainfall, dailyWindSpeeds);
//                                } else {
//                                    Toast.makeText(UserLocation.this, "Forecast data is empty", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(UserLocation.this, "Failed to fetch forecast data", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<JsonObject> call, Throwable t) {
//                            Toast.makeText(UserLocation.this, "Network error", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(UserLocation.this, "Failed to fetch current weather data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(UserLocation.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void parseAndPredict(double currentWindSpeed, double currentRainfall, List<Double> dailyWindSpeeds) {
//        if (addresses != null && addresses.size() > 0) {
//            String currentLocality = addresses.get(0).getLocality();
//            String subAdminArea = addresses.get(0).getSubAdminArea();
//
//            Log.d("ParsedData", "Current Locality: " + currentLocality);
//            Log.d("ParsedData", "SubAdminArea: " + subAdminArea);
//            Log.d("ParsedData", "Current Wind Speed: " + currentWindSpeed + " m/s");
//            Log.d("ParsedData", "Current Rainfall: " + currentRainfall + " mm");
//
//            for (int i = 0; i < dailyWindSpeeds.size(); i++) {
//                Log.d("ParsedData", String.format("Day %d Wind Speed: %.2f m/s", i + 1, dailyWindSpeeds.get(i)));
//            }
//            List<Double> averageDailyRainfalls = getAverageRainfallData(addresses.get(0).getSubAdminArea(), Calendar.getInstance().get(Calendar.MONTH) + 1);
//
//            for (int i = 0; i < averageDailyRainfalls.size(); i++) {
//                Log.d("ParsedData", String.format("Day %d Average Rainfall: %.2f mm", i + 1, averageDailyRainfalls.get(i)));
//            }
//
//            TextView nearbyCity1TextView = findViewById(R.id.nearbyCity1TextView);
//            TextView nearbyCity2TextView = findViewById(R.id.nearbyCity2TextView);
//            String nearbyCity1 = nearbyCity1TextView.getText().toString();
//            String nearbyCity2 = nearbyCity2TextView.getText().toString();
//
//            if (nearbyCity1.startsWith("Nearby City 1: ")) {
//                nearbyCity1 = nearbyCity1.substring("Nearby City 1: ".length());
//            }
//
//            if (nearbyCity2.startsWith("Nearby City 2: ")) {
//                nearbyCity2 = nearbyCity2.substring("Nearby City 1: ".length());
//            }
//
//            Log.d("ParsedData", nearbyCity1);
//            Log.d("ParsedData", nearbyCity2);
//        } else {
//
//            Log.e("ParsedData", "Addresses is null or empty");
//        }
//    }
//
//
//    private double getCurrentWindSpeed(JsonObject weatherJson) {
//        if (weatherJson != null && weatherJson.has("wind")) {
//            JsonObject windJson = weatherJson.getAsJsonObject("wind");
//            if (windJson.has("speed")) {
//                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();
//                double windSpeedInMilesPerHour = windSpeedInMetersPerSecond * 2.23694;
//                return windSpeedInMilesPerHour;
//            }
//        }
//        return 0.0;
//    }
//
//    private double getCurrentRainfall(JsonObject weatherJson) {
//        if (weatherJson != null && weatherJson.has("rain")) {
//            JsonObject rainJson = weatherJson.getAsJsonObject("rain");
//            if (rainJson.has("1h")) {
//                return rainJson.get("1h").getAsDouble();
//            }
//        }
//        return 0.0;
//    }
//
//    private List<Double> getAverageRainfallData(String district, int currentMonth) {
//        List<Double> averageDailyRainfalls = new ArrayList<>();
//
//        try (InputStream inputStream = getResources().getAssets().open("Rainfall_Daily_Average.csv")) {
//            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
//            List<String[]> csvData = csvReader.readAll();
//            for (String[] row : csvData) {
//                String csvDistrict = row[0].trim();
//                if (csvDistrict.equalsIgnoreCase(district)) {
//                    int currentMonthIndex = currentMonth;
//                    for (int j = 0; j < 3; j++) {
//                        if (currentMonthIndex >= 1 && currentMonthIndex <= 12) {
//                            double rainfall = Double.parseDouble(row[currentMonthIndex].trim());
//                            averageDailyRainfalls.add(rainfall);
//                        } else {
//                            averageDailyRainfalls.add(0.0);
//                        }
//                    }
//                    return averageDailyRainfalls;
//
//                }
//            }
//        } catch (IOException | CsvException e) {
//            e.printStackTrace();
//        }
//        return averageDailyRainfalls;
//    }
//
//}


/**
 *
 */



//public class UserLocation extends AppCompatActivity {
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    TextView country, city, district, address, latitude, longitude;
//    Button getLocation;
//    private TextView windSpeedTextView, rainfallTextView;
//    private final static int REQUEST_CODE = 100;
//    private WeatherApi weatherApi;
//    private Retrofit retrofit;
//    private List<Address> addresses;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user_location);
//
//        Log.d("******************************","Loaded 1");
//
//        country = findViewById(R.id.country);
//        city = findViewById(R.id.city);
//        address = findViewById(R.id.address);
//        longitude = findViewById(R.id.longitude);
//        latitude = findViewById(R.id.latitude);
//        getLocation = findViewById(R.id.get_location_btn);
//
//        Log.d("******************************","Loaded 2");
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        Log.d("******************************","Loaded 3");
//
//
//        getLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("******************************","Loaded 4");
//
//                getLastLocation();
//                Log.d("******************************","getLastLocation");
//
//
//            }
//        });
//
//
//    }
//
//    public void getLastLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            fusedLocationProviderClient.getLastLocation()
//                    .addOnSuccessListener(new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if (location != null) {
//                                Log.d("********************************************************Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
//                                Geocoder geocoder = new Geocoder(UserLocation.this, Locale.getDefault());
//                                //List<Address> addresses = null;
//                                try {
//                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//                                    if (addresses != null && addresses.size() > 0) {
//                                        Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
//                                        Log.d("Location", "Country: " + addresses.get(0).getCountryName());
//                                        Log.d("Location", "Address: " + addresses.get(0).getAddressLine(0));
//
//                                        Log.d("***************************************** ", "before setting"  +String.valueOf(city));
//
//
////                                        city.setText("City: " + addresses.get(0).getLocality());
//                                        city.setText("Maharagama");
//                                        //district.setText("District: " + addresses.get(0).getSubAdminArea());
//
//                                        getWeatherData(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//
//                                        NearbyCities nearbyCities = getNearbyLocationNames(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//                                        updateNearbyCitiesUI(nearbyCities.getCity1(), nearbyCities.getCity2());
//                                    } else {
//                                        Log.e("Location", "No address found for the location");
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                //processWeatherData(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//                            }
//
//                            else {
//                                Log.d("**************************", String.valueOf(location));
//                            }
//
//                        }
//
//
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // Handle failure to get last location
//                            Log.e("Location", "Failed to get last location: " + e.getMessage());
//                        }
//                    });
//        } else {
//            askPermission();
//        }
//    }
//
//    private void askPermission() {
//        ActivityCompat.requestPermissions(UserLocation.this, new String[]
//                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
//            } else {
//                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private NearbyCities getNearbyLocationNames(double latitude, double longitude) {
//        double latitudeOffset = 0.01;
//        double longitudeOffset = 0.02;
//
//        double nearbyLatitude1 = latitude + latitudeOffset;
//        double nearbyLongitude1 = longitude + longitudeOffset;
//
//        double nearbyLatitude2 = latitude - latitudeOffset;
//        double nearbyLongitude2 = longitude - longitudeOffset;
//
//        Geocoder geocoder = new Geocoder(UserLocation.this, Locale.getDefault());
//
//        try {
//            List<Address> nearbyAddresses1 = geocoder.getFromLocation(nearbyLatitude1, nearbyLongitude1, 1);
//            if (nearbyAddresses1 != null && nearbyAddresses1.size() > 0) {
//                String nearbyCity1 = nearbyAddresses1.get(0).getLocality();
//                Log.d("NearbyLocation", "Nearby City 1: " + nearbyCity1);
//
//                List<Address> nearbyAddresses2 = geocoder.getFromLocation(nearbyLatitude2, nearbyLongitude2, 1);
//                if (nearbyAddresses2 != null && nearbyAddresses2.size() > 0) {
//                    String nearbyCity2 = nearbyAddresses2.get(0).getLocality();
//                    if (!nearbyCity2.equals(nearbyCity1)) {
//                        Log.d("NearbyLocation", "Nearby City 2: " + nearbyCity2);
//                        return new NearbyCities(nearbyCity1, nearbyCity2);
//                    } else {
//                        Log.e("NearbyLocation", "City 2 is the same as City 1");
//                    }
//                } else {
//                    Log.e("NearbyLocation", "One or both of the cities is null");
//                }
//            } else {
//                Log.e("NearbyLocation", "No nearby addresses found for City 2");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new NearbyCities("", "");
//    }
//
//    private void updateNearbyCitiesUI(String city1, String city2) {
//        TextView nearbyCity1TextView = findViewById(R.id.nearbyCity1TextView);
//        TextView nearbyCity2TextView = findViewById(R.id.nearbyCity2TextView);
//
//        // Update UI with the two nearby cities
//        nearbyCity1TextView.setText("Nearby City 1: " + city1);
//        nearbyCity2TextView.setText("Nearby City 2: " + city2);
//    }
//
//    public interface WeatherApi {
//        @GET("data/2.5/weather")
//        Call<JsonObject> getCurrentWeather(
//                @Query("lat") double latitude,
//                @Query("lon") double longitude,
//                @Query("appid") String apiKey
//        );
//
//        @GET("data/2.5/forecast")
//        Call<JsonObject> getWeatherForecast(
//                @Query("lat") double latitude,
//                @Query("lon") double longitude,
//                @Query("appid") String apiKey
//        );
//    }
//
//    public void getWeatherData(double latitude, double longitude) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
//        String apiKey = "3747c6412373b23436ca611b8963f61c";
//
//        Call<JsonObject> currentWeatherCall = weatherApi.getCurrentWeather(latitude, longitude, apiKey);
//        currentWeatherCall.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    JsonObject weatherJson = response.body();
//                    Log.d("WeatherData", "Current Weather JSON: " + weatherJson);
//                    double currentWindSpeed = getCurrentWindSpeed(weatherJson);
//                    double currentRainfall = getCurrentRainfall(weatherJson);
//                    Log.d("WeatherData", "Current Wind Speed: " + currentWindSpeed + " m/s");
//                    Log.d("WeatherData", "Current Rainfall: " + currentRainfall + " mm");
//
//                    Call<JsonObject> forecastCall = weatherApi.getWeatherForecast(latitude, longitude, apiKey);
//                    forecastCall.enqueue(new Callback<JsonObject>() {
//                        @Override
//                        public void onResponse(Call<JsonObject> call, Response<JsonObject> forecastResponse) {
//                            if (forecastResponse.isSuccessful()) {
//                                JsonObject forecastJson = forecastResponse.body();
//                                Log.d("WeatherData", "Forecast JSON: " + forecastJson);
//
//                                if (forecastJson != null && forecastJson.has("list")) {
//                                    JsonArray forecastList = forecastJson.getAsJsonArray("list");
//
//                                    List<Double> dailyWindSpeeds = new ArrayList<>();
//                                    List<Double> dailyRainfalls = new ArrayList<>();
//
//                                    for (int i = 0; i < Math.min(3, forecastList.size()); i++) {
//                                        JsonObject dayForecast = forecastList.get(i).getAsJsonObject();
//
//                                        if (dayForecast.has("wind")) {
//                                            JsonObject windJson = dayForecast.getAsJsonObject("wind");
//                                            if (windJson.has("speed")) {
//                                                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();
//                                                double windSpeedInMilesPerHour = windSpeedInMetersPerSecond * 2.23694;
//                                                dailyWindSpeeds.add(windSpeedInMilesPerHour);
//                                            }
//                                        } else {
//                                            dailyWindSpeeds.add(0.0);
//                                        }
//
//                                    }
//
//                                    parseAndPredict(currentWindSpeed, currentRainfall, dailyWindSpeeds);
//                                } else {
//                                    Toast.makeText(UserLocation.this, "Forecast data is empty", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(UserLocation.this, "Failed to fetch forecast data", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<JsonObject> call, Throwable t) {
//                            Toast.makeText(UserLocation.this, "Network error", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(UserLocation.this, "Failed to fetch current weather data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(UserLocation.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void parseAndPredict(double currentWindSpeed, double currentRainfall, List<Double> dailyWindSpeeds) {
//        if (addresses != null && addresses.size() > 0) {
//            String currentLocality = addresses.get(0).getLocality();
//            String subAdminArea = addresses.get(0).getSubAdminArea();
//
//            Log.d("ParsedData", "Current Locality: " + currentLocality);
//            Log.d("ParsedData", "SubAdminArea: " + subAdminArea);
//            Log.d("ParsedData", "Current Wind Speed: " + currentWindSpeed + " m/s");
//            Log.d("ParsedData", "Current Rainfall: " + currentRainfall + " mm");
//
//            for (int i = 0; i < dailyWindSpeeds.size(); i++) {
//                Log.d("ParsedData", String.format("Day %d Wind Speed: %.2f m/s", i + 1, dailyWindSpeeds.get(i)));
//            }
//
//
//            List<Double> averageDailyRainfalls = getAverageRainfallData(addresses.get(0).getSubAdminArea(), Calendar.getInstance().get(Calendar.MONTH) + 1);
//
//            Log.d("ParsedData", String.valueOf(averageDailyRainfalls.size()));
//
//            for (int i = 0; i <averageDailyRainfalls.size() ; i++) {
//                Log.d("ParsedData", String.format("Day %d Average Rainfall: %.2f mm", i + 1, averageDailyRainfalls.get(i)));
////                Log.d("ParsedData", " help " +  String.valueOf(i));
//            }
//            Log.d("ParsedData"," up--");
//
//
//
//            TextView nearbyCity1TextView = findViewById(R.id.nearbyCity1TextView);
//            TextView nearbyCity2TextView = findViewById(R.id.nearbyCity2TextView);
//            String nearbyCity1 = nearbyCity1TextView.getText().toString();
//            String nearbyCity2 = nearbyCity2TextView.getText().toString();
//
//            if (nearbyCity1.startsWith("Nearby City 1: ")) {
//                nearbyCity1 = nearbyCity1.substring("Nearby City 1: ".length());
//            }
//
//            if (nearbyCity2.startsWith("Nearby City 2: ")) {
//                nearbyCity2 = nearbyCity2.substring("Nearby City 1: ".length());
//            }
//
//            Log.d("ParsedData", nearbyCity1);
//            Log.d("ParsedData", nearbyCity2);
//        } else {
//
//            Log.e("ParsedData", "Addresses is null or empty");
//        }
//    }
//
//
//    private double getCurrentWindSpeed(JsonObject weatherJson) {
//        if (weatherJson != null && weatherJson.has("wind")) {
//            JsonObject windJson = weatherJson.getAsJsonObject("wind");
//            if (windJson.has("speed")) {
//                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();
//                double windSpeedInMilesPerHour = windSpeedInMetersPerSecond * 2.23694;
//                return windSpeedInMilesPerHour;
//            }
//        }
//        return 0.0;
//    }
//
//    private double getCurrentRainfall(JsonObject weatherJson) {
//        if (weatherJson != null && weatherJson.has("rain")) {
//            JsonObject rainJson = weatherJson.getAsJsonObject("rain");
//            if (rainJson.has("1h")) {
//                return rainJson.get("1h").getAsDouble();
//            }
//        }
//        return 0.0;
//    }
//
//    private List<Double> getAverageRainfallData(String district, int currentMonth) {
//        List<Double> averageDailyRainfalls = new ArrayList<>();
//
//        try (InputStream inputStream = getResources().getAssets().open("Rainfall_Daily_Average.csv")) {
//            Log.d("***************************************","open file");
//            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
//            List<String[]> csvData = csvReader.readAll();
//            for (String[] row : csvData) {
//                String csvDistrict = row[0].trim();
//                if (csvDistrict.equalsIgnoreCase(district)) {
//                    int currentMonthIndex = currentMonth;
//                    for (int j = 0; j < 3; j++) {
//                        if (currentMonthIndex >= 1 && currentMonthIndex <= 12) {
//                            double rainfall = Double.parseDouble(row[currentMonthIndex].trim());
//                            averageDailyRainfalls.add(rainfall);
//                        } else {
//                            averageDailyRainfalls.add(0.0);
//                        }
//                    }
//                    return averageDailyRainfalls;
//
//                }
//            }
//        } catch (IOException | CsvException e) {
//            e.printStackTrace();
//        }
//        return averageDailyRainfalls;
//    }
//
//}
//

//
//
//public class UserLocation extends AppCompatActivity {
//    FusedLocationProviderClient fusedLocationProviderClient;
//    TextView country, city, district, address, latitude, longitude;
//    Button getLocation;
//    private TextView windSpeedTextView, rainfallTextView;
//    private final static int REQUEST_CODE = 100;
//    private WeatherApi weatherApi;
//    private Retrofit retrofit;
//    private List<Address> addresses;
//
//    int currentMonth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
//        Log.d("CurrentMonth", "Current Month: " + currentMonth);
//
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user_location);
//
//        country = findViewById(R.id.country);
//        district = findViewById(R.id.district);
//        city = findViewById(R.id.city);
//        address = findViewById(R.id.address);
//        latitude = findViewById(R.id.latitude);
//        longitude = findViewById(R.id.longitude);
//        getLocation = findViewById(R.id.get_location_btn);
//
//        windSpeedTextView = findViewById(R.id.windSpeed);
//        rainfallTextView = findViewById(R.id.rainfall);
//
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        getLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                getLastLocation();
//            }
//        });
//
//    }
//
//    public void getLastLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            fusedLocationProviderClient.getLastLocation()
//                    .addOnSuccessListener(new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if (location != null) {
//                                Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
//                                Geocoder geocoder = new Geocoder(UserLocation.this, Locale.getDefault());
//                                //List<Address> addresses = null;
//                                try {
//                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//                                    if (addresses != null && addresses.size() > 0) {
//                                        // Update UI with location details
//                                        Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
//                                        Log.d("Location", "Country: " + addresses.get(0).getCountryName());
//                                        Log.d("Location", "Address: " + addresses.get(0).getAddressLine(0));
//                                        city.setText("City: " + addresses.get(0).getLocality());
//                                        district.setText("District: " + addresses.get(0).getSubAdminArea());
//
//
//                                        // Call the getWeatherData method with obtained latitude and longitude
//                                        //getCurrentLocationInfo(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//                                        getWeatherData(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//
//                                        // Get near locations by adjusting latitude and longitude
////                                        double latitudeOffset = 0.01;
////                                        double longitudeOffset = 0.02;
//
//                                        NearbyCities nearbyCities = getNearbyLocationNames(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//                                        updateNearbyCitiesUI(nearbyCities.getCity1(), nearbyCities.getCity2());
//                                    } else {
//                                        // Handle the case where no address is found
//                                        Log.e("Location", "No address found for the location");
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                // Call the method to process weather data
//                                processWeatherData(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // Handle failure to get last location
//                            Log.e("Location", "Failed to get last location: " + e.getMessage());
//                        }
//                    });
//        } else {
//            // If permission is not granted, request it
//            askPermission();
//        }
//    }
//
//
//    private void askPermission() {
//        ActivityCompat.requestPermissions(UserLocation.this, new String[]
//                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
//            } else {
//                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void processWeatherData(double latitude, double longitude) {
//        // Call the method to get weather data
//        getWeatherData(latitude, longitude);
//
//        // Call the method to get current location information
//        //getCurrentLocationInfo(latitude, longitude);
//    }
//
//    private NearbyCities getNearbyLocationNames(double latitude, double longitude) {
//        // Define the offset values
//        double latitudeOffset = 0.01;
//        double longitudeOffset = 0.02;
//
//        // Calculate the coordinates for the two nearby locations
//        double nearbyLatitude1 = latitude + latitudeOffset;
//        double nearbyLongitude1 = longitude + longitudeOffset;
//
//        double nearbyLatitude2 = latitude - latitudeOffset;
//        double nearbyLongitude2 = longitude - longitudeOffset;
//
//        Geocoder geocoder = new Geocoder(UserLocation.this, Locale.getDefault());
//
//        try {
//            // Get the first nearby location
//            List<Address> nearbyAddresses1 = geocoder.getFromLocation(nearbyLatitude1, nearbyLongitude1, 1);
//            if (nearbyAddresses1 != null && nearbyAddresses1.size() > 0) {
//                String nearbyCity1 = nearbyAddresses1.get(0).getLocality();
//                Log.d("NearbyLocation", "Nearby City 1: " + nearbyCity1);
//
//                // Get the second nearby location
//                List<Address> nearbyAddresses2 = geocoder.getFromLocation(nearbyLatitude2, nearbyLongitude2, 1);
//                if (nearbyAddresses2 != null && nearbyAddresses2.size() > 0) {
//                    String nearbyCity2 = nearbyAddresses2.get(0).getLocality();
//                    if (!nearbyCity2.equals(nearbyCity1)) {
//                        Log.d("NearbyLocation", "Nearby City 2: " + nearbyCity2);
//                        return new NearbyCities(nearbyCity1, nearbyCity2);
//                    } else {
//                        Log.e("NearbyLocation", "City 2 is the same as City 1");
//                        // Handle the case where City 2 is the same as City 1
//                        // You may consider adjusting the offset values to ensure diversity in cities
//                    }
//                } else {
//                    Log.e("NearbyLocation", "One or both of the cities is null");
//                }
//            } else {
//                Log.e("NearbyLocation", "No nearby addresses found for City 2");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Return a default value if the cities cannot be determined
//        return new NearbyCities("", "");
//    }
//
//    // Add this method to your activity to update the UI
//    private void updateNearbyCitiesUI(String city1, String city2) {
//        TextView nearbyCity1TextView = findViewById(R.id.nearbyCity1TextView);
//        TextView nearbyCity2TextView = findViewById(R.id.nearbyCity2TextView);
//
//        // Update UI with the two nearby cities
//        nearbyCity1TextView.setText("Nearby City 1: " + city1);
//        nearbyCity2TextView.setText("Nearby City 2: " + city2);
//    }
//
//    public interface WeatherApi {
//        @GET("data/2.5/weather")
//        Call<JsonObject> getCurrentWeather(
//                @Query("lat") double latitude,
//                @Query("lon") double longitude,
//                @Query("appid") String apiKey
//        );
//
//        @GET("data/2.5/forecast")
//        Call<JsonObject> getWeatherForecast(
//                @Query("lat") double latitude,
//                @Query("lon") double longitude,
//                @Query("appid") String apiKey
//        );
//    }
//
//
//    // Inside your MainActivity
//    public void getWeatherData (double latitude, double longitude){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.openweathermap.org/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
//        String apiKey = "3747c6412373b23436ca611b8963f61c";
//
//        // Call to get current weather
//        Call<JsonObject> currentWeatherCall = weatherApi.getCurrentWeather(latitude, longitude, apiKey);
//        currentWeatherCall.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    JsonObject weatherJson = response.body();
//                    Log.d("WeatherData", "Current Weather JSON: " + weatherJson);
//                    // Extract current weather information
//                    double currentWindSpeed = getCurrentWindSpeed(weatherJson);
//                    double currentRainfall = getCurrentRainfall(weatherJson);
//
//                    // Log the current wind speed and rainfall
//                    Log.d("WeatherData", "Current Wind Speed: " + currentWindSpeed + " m/s");
//                    Log.d("WeatherData", "Current Rainfall: " + currentRainfall + " mm");
//
//
//                    // Call to get weather forecast
//                    Call<JsonObject> forecastCall = weatherApi.getWeatherForecast(latitude, longitude, apiKey);
//                    forecastCall.enqueue(new Callback<JsonObject>() {
//                        @Override
//                        public void onResponse(Call<JsonObject> call, Response<JsonObject> forecastResponse) {
//                            if (forecastResponse.isSuccessful()) {
//                                JsonObject forecastJson = forecastResponse.body();
//                                Log.d("WeatherData", "Forecast JSON: " + forecastJson);
//
//                                if (forecastJson != null && forecastJson.has("list")) {
//                                    JsonArray forecastList = forecastJson.getAsJsonArray("list");
//
//                                    // Initialize variables to store daily forecast data
//                                    List<Double> dailyWindSpeeds = new ArrayList<>();
//                                    List<Double> dailyRainfalls = new ArrayList<>();
//
//                                    // Extract daily forecast data for the next 4 days
//                                    for (int i = 0; i < Math.min(3, forecastList.size()); i++) {
//                                        JsonObject dayForecast = forecastList.get(i).getAsJsonObject();
//
//                                        // Extract wind speed for the day
//                                        if (dayForecast.has("wind")) {
//                                            JsonObject windJson = dayForecast.getAsJsonObject("wind");
//                                            if (windJson.has("speed")) {
//                                                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();
//
//                                                // Convert to miles per hour
//                                                double windSpeedInMilesPerHour = windSpeedInMetersPerSecond * 2.23694;
//
//                                                dailyWindSpeeds.add(windSpeedInMilesPerHour);
//                                            }
//                                        } else {
//                                            // If "wind" object is not present, assume no wind
//                                            dailyWindSpeeds.add(0.0);
//                                        }
//
//                                    }
//
//                                    // Update UI with daily forecast values
//                                    //updateDailyForecastUI(dailyWindSpeeds, dailyRainfalls);
//                                    parseAndPredict(currentWindSpeed, currentRainfall,dailyWindSpeeds);
//                                } else {
//                                    // Handle the case where forecastList is empty
//                                    Toast.makeText(UserLocation.this, "Forecast data is empty", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(UserLocation.this, "Failed to fetch forecast data", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<JsonObject> call, Throwable t) {
//                            Toast.makeText(UserLocation.this, "Network error", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(UserLocation.this, "Failed to fetch current weather data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(UserLocation.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void parseAndPredict(double currentWindSpeed, double currentRainfall, List<Double> dailyWindSpeeds) {
//        if (addresses != null && addresses.size() > 0) {
//            String currentLocality = addresses.get(0).getLocality();
//            String subAdminArea = addresses.get(0).getSubAdminArea();
//
//            // For simplicity, you can display the extracted parameters
//            Log.d("ParsedData", "Current Locality: " + currentLocality);
//            Log.d("ParsedData", "SubAdminArea: " + subAdminArea);
//            Log.d("ParsedData", "Current Wind Speed: " + currentWindSpeed + " m/s");
//            Log.d("ParsedData", "Current Rainfall: " + currentRainfall + " mm");
//
//
//            // The wind speed for the upcoming 4 days
//            for (int i = 0; i < dailyWindSpeeds.size(); i++) {
//                Log.d("ParsedData", String.format("Day %d Wind Speed: %.2f m/s", i + 1, dailyWindSpeeds.get(i)));
//            }
//            // The average rainfall for the upcoming 4 days
//            List<Double> averageDailyRainfalls = getAverageRainfallData(addresses.get(0).getSubAdminArea(), Calendar.getInstance().get(Calendar.MONTH) + 1);
//
//            for (int i = 0; i < averageDailyRainfalls.size(); i++) {
//                Log.d("ParsedData", String.format("Day %d Average Rainfall: %.2f mm", i + 1, averageDailyRainfalls.get(i)));
//            }
//
//            // Get UI values for nearby cities
//            TextView nearbyCity1TextView = findViewById(R.id.nearbyCity1TextView);
//            TextView nearbyCity2TextView = findViewById(R.id.nearbyCity2TextView);
//            String nearbyCity1 = nearbyCity1TextView.getText().toString();
//            String nearbyCity2 = nearbyCity2TextView.getText().toString();
//
//            if (nearbyCity1.startsWith("Nearby City 1: ")) {
//                nearbyCity1 = nearbyCity1.substring("Nearby City 1: ".length());
//            }
//
//            if (nearbyCity2.startsWith("Nearby City 2: ")) {
//                nearbyCity2 = nearbyCity2.substring("Nearby City 1: ".length());
//            }
//
//            // Log UI values for nearby cities
//            Log.d("ParsedData",nearbyCity1);
//            Log.d("ParsedData",nearbyCity2);
//        } else {
//
//            Log.e("ParsedData", "Addresses is null or empty");
//        }
//    }
//
//    private double getCurrentWindSpeed(JsonObject weatherJson) {
//        if (weatherJson != null && weatherJson.has("wind")) {
//            JsonObject windJson = weatherJson.getAsJsonObject("wind");
//            if (windJson.has("speed")) {
//                // Get wind speed in meters per second
//                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();
//
//                // Convert to miles per hour
//                double windSpeedInMilesPerHour = windSpeedInMetersPerSecond * 2.23694;
//
//                return windSpeedInMilesPerHour;
//            }
//        }
//        return 0.0;
//    }
//
//    private double getCurrentRainfall(JsonObject weatherJson) {
//        if (weatherJson != null && weatherJson.has("rain")) {
//            JsonObject rainJson = weatherJson.getAsJsonObject("rain");
//            if (rainJson.has("1h")) {
//                return rainJson.get("1h").getAsDouble();
//            }
//        }
//        return 0.0;
//    }
//
//
//
//
//    private void updateWindSpeedUI(double windSpeed) {
//        windSpeedTextView.setText("Wind Speed: " + windSpeed + " m/s");
//    }
//
//    // Update UI with rainfall information
//    private void updateRainfallUI(double rainfallVolume) {
//        rainfallTextView.setText("Rainfall: " + rainfallVolume + " mm");
//    }
//
//
//    // Inside your MainActivity
//    private void updateDailyForecastUI(List<Double> dailyWindSpeeds, List<Double> dailyRainfalls) {
//        TextView day1Forecast = findViewById(R.id.day1Forecast);
//        TextView day2Forecast = findViewById(R.id.day2Forecast);
//        TextView day3Forecast = findViewById(R.id.day3Forecast);
//        //TextView day4Forecast = findViewById(R.id.day4Forecast);
//
//        // Log the received wind speed values
//        //Log.d("WeatherData", "Received Wind Speeds: " + dailyWindSpeeds);
//
//
//        // Get the current month
//        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
//        Log.d("CurrentMonth", "Current Month: " + currentMonth);
//
//
//        //List<Double> averageDailyRainfalls = getAverageRainfallData(addresses.get(0).getSubAdminArea(), currentMonth);
//
//        // Check if addresses is not null and not empty
//        if (addresses != null && !addresses.isEmpty()) {
//            List<Double> averageDailyRainfalls = getAverageRainfallData(addresses.get(0).getSubAdminArea(), currentMonth);
//
//            // Update UI for Day 1 with average daily rainfall
//            day1Forecast.setText(String.format(Locale.getDefault(), "Day 1: Wind Speed - %.2f m/s, Rainfall - %.2f mm",
//                    dailyWindSpeeds.get(0),averageDailyRainfalls.get(0)));
//
//            // Update UI for Day 2 with average daily rainfall
//            day2Forecast.setText(String.format(Locale.getDefault(), "Day 2: Wind Speed - %.2f m/s, Rainfall - %.2f mm",
//                    dailyWindSpeeds.get(1),averageDailyRainfalls.get(1)));
//
//            // Update UI for Day 3 with average daily rainfall
//            day3Forecast.setText(String.format(Locale.getDefault(), "Day 3: Wind Speed - %.2f m/s, Rainfall - %.2f mm",
//                    dailyWindSpeeds.get(2),averageDailyRainfalls.get(2)));
//
//            // Update UI for Day 4 with average daily rainfall
////            day4Forecast.setText(String.format(Locale.getDefault(), "Day 4: Wind Speed - %.2f m/s, Rainfall - %.2f mm",
////                    dailyWindSpeeds.get(3),averageDailyRainfalls.get(3)));
//
//            // Log the received average daily rainfall values
//            Log.d("WeatherData", "Received Average Daily Rainfalls: " + averageDailyRainfalls);
//        } else {
//            // Handle the case where addresses is null or empty
//            Log.e("WeatherData", "Addresses is null or empty");
//            // You can display an error message or perform any other appropriate actions
//        }
//    }
//
//    private List<Double> getAverageRainfallData(String district, int currentMonth) {
//        List<Double> averageDailyRainfalls = new ArrayList<>();
//
//        // Read and parse your CSV file to get average daily rainfall data
//        // Replace the following lines with your actual logic to read and parse the CSV file
//        try (InputStream inputStream = getResources().getAssets().open("Rainfall_Daily_Average.csv")) {
//            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
//            List<String[]> csvData = csvReader.readAll();
//
//            // Assuming the CSV structure is like: District, Month1, Month2, ..., Month12
//            for (String[] row : csvData) {
//                String csvDistrict = row[0].trim();
//                if (csvDistrict.equalsIgnoreCase(district)) {
//                    // Assuming 12 months of data starting from index 1
//                    int currentMonthIndex = currentMonth; // Use the current month index
//                    for (int j = 0; j < 3; j++) {
//                        if (currentMonthIndex >= 1 && currentMonthIndex <= 12) {
//                            double rainfall = Double.parseDouble(row[currentMonthIndex].trim());
//                            averageDailyRainfalls.add(rainfall);
//                        } else {
//                            // Handle the case where the month index is not in the valid range
//                            averageDailyRainfalls.add(0.0);
//                        }
//                    }
//                    return averageDailyRainfalls;
//
//                }
//            }
//        } catch (IOException | CsvException e) {
//            e.printStackTrace();
//        }
//
//        // Handle the case where the district data is not found
//        return averageDailyRainfalls;
//    }
//}
//


/**
 *
 */


public class UserLocation extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView country, city, district, address, latitude, longitude;
    Button getLocation;
    private TextView windSpeedTextView, rainfallTextView;
    private final static int REQUEST_CODE = 100;
    private WeatherApi weatherApi;
    private Retrofit retrofit;
    private List<Address> addresses;


    List<Double> rainfall_Data = new ArrayList<>();
    List<Double> wind_Speed_Data = new ArrayList<>();

    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_location);

        country = findViewById(R.id.country);
        district = findViewById(R.id.district);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        getLocation = findViewById(R.id.get_location_btn);

        windSpeedTextView = findViewById(R.id.windSpeed);
        rainfallTextView = findViewById(R.id.rainfall);


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
                                Geocoder geocoder = new Geocoder(UserLocation.this, Locale.getDefault());
                                //List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    if (addresses != null && addresses.size() > 0) {
                                        // Update UI with location details
                                        Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
                                        Log.d("Location", "Country: " + addresses.get(0).getCountryName());
                                        Log.d("Location", "Address: " + addresses.get(0).getAddressLine(0));
                                        city.setText("City: " + addresses.get(0).getLocality());
                                        district.setText("District: " + addresses.get(0).getSubAdminArea());


                                        // Call the getWeatherData method with obtained latitude and longitude
                                        //getCurrentLocationInfo(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                                        getWeatherData(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                                        // Get near locations by adjusting latitude and longitude
//                                        double latitudeOffset = 0.01;
//                                        double longitudeOffset = 0.02;

                                        NearbyCities nearbyCities = getNearbyLocationNames(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                                        updateNearbyCitiesUI(nearbyCities.getCity1(), nearbyCities.getCity2());
                                    } else {
                                        // Handle the case where no address is found
                                        Log.e("Location", "No address found for the location");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // Call the method to process weather data
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
            // If permission is not granted, request it
            askPermission();
        }
    }


    private void askPermission() {
        ActivityCompat.requestPermissions(UserLocation.this, new String[]
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

    private void processWeatherData(double latitude, double longitude) {
        // Call the method to get weather data
        getWeatherData(latitude, longitude);

        // Call the method to get current location information
        //getCurrentLocationInfo(latitude, longitude);
    }

    private NearbyCities getNearbyLocationNames(double latitude, double longitude) {
        // Define the offset values
        double latitudeOffset = 0.01;
        double longitudeOffset = 0.02;

        // Calculate the coordinates for the two nearby locations
        double nearbyLatitude1 = latitude + latitudeOffset;
        double nearbyLongitude1 = longitude + longitudeOffset;

        double nearbyLatitude2 = latitude - latitudeOffset;
        double nearbyLongitude2 = longitude - longitudeOffset;

        Geocoder geocoder = new Geocoder(UserLocation.this, Locale.getDefault());

        try {
            // Get the first nearby location
            List<Address> nearbyAddresses1 = geocoder.getFromLocation(nearbyLatitude1, nearbyLongitude1, 1);
            if (nearbyAddresses1 != null && nearbyAddresses1.size() > 0) {
                String nearbyCity1 = nearbyAddresses1.get(0).getLocality();
                Log.d("NearbyLocation", "Nearby City 1: " + nearbyCity1);

                // Get the second nearby location
                List<Address> nearbyAddresses2 = geocoder.getFromLocation(nearbyLatitude2, nearbyLongitude2, 1);
                if (nearbyAddresses2 != null && nearbyAddresses2.size() > 0) {
                    String nearbyCity2 = nearbyAddresses2.get(0).getLocality();
                    if (!nearbyCity2.equals(nearbyCity1)) {
                        Log.d("NearbyLocation", "Nearby City 2: " + nearbyCity2);
                        return new NearbyCities(nearbyCity1, nearbyCity2);
                    } else {
                        Log.e("NearbyLocation", "City 2 is the same as City 1");
                        // Handle the case where City 2 is the same as City 1
                        // You may consider adjusting the offset values to ensure diversity in cities
                    }
                } else {
                    Log.e("NearbyLocation", "One or both of the cities is null");
                }
            } else {
                Log.e("NearbyLocation", "No nearby addresses found for City 2");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return a default value if the cities cannot be determined
        return new NearbyCities("", "");
    }

    // Add this method to your activity to update the UI
    private void updateNearbyCitiesUI(String city1, String city2) {
        TextView nearbyCity1TextView = findViewById(R.id.nearbyCity1TextView);
        TextView nearbyCity2TextView = findViewById(R.id.nearbyCity2TextView);

        // Update UI with the two nearby cities
        nearbyCity1TextView.setText("Nearby City 1: " + city1);
        nearbyCity2TextView.setText("Nearby City 2: " + city2);
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


    // Inside your MainActivity
    public void getWeatherData (double latitude, double longitude){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        String apiKey = "3747c6412373b23436ca611b8963f61c";

        // Call to get current weather
        Call<JsonObject> currentWeatherCall = weatherApi.getCurrentWeather(latitude, longitude, apiKey);
        currentWeatherCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject weatherJson = response.body();
                    Log.d("WeatherData", "Current Weather JSON: " + weatherJson);
                    // Extract current weather information
                    double currentWindSpeed = getCurrentWindSpeed(weatherJson);
                    double currentRainfall = getCurrentRainfall(weatherJson);

                    // Log the current wind speed and rainfall
                    Log.d("WeatherData", "Current Wind Speed: " + currentWindSpeed + " m/s");
                    Log.d("WeatherData", "Current Rainfall: " + currentRainfall + " mm");


                    // Call to get weather forecast
                    Call<JsonObject> forecastCall = weatherApi.getWeatherForecast(latitude, longitude, apiKey);
                    forecastCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> forecastResponse) {
                            if (forecastResponse.isSuccessful()) {
                                JsonObject forecastJson = forecastResponse.body();
                                Log.d("WeatherData", "Forecast JSON: " + forecastJson);

                                if (forecastJson != null && forecastJson.has("list")) {
                                    JsonArray forecastList = forecastJson.getAsJsonArray("list");

                                    // Initialize variables to store daily forecast data
                                    List<Double> dailyWindSpeeds = new ArrayList<>();
                                    List<Double> dailyRainfalls = new ArrayList<>();

                                    // Extract daily forecast data for the next 4 days
                                    for (int i = 0; i < Math.min(3, forecastList.size()); i++) {
                                        JsonObject dayForecast = forecastList.get(i).getAsJsonObject();

                                        // Extract wind speed for the day
                                        if (dayForecast.has("wind")) {
                                            JsonObject windJson = dayForecast.getAsJsonObject("wind");
                                            if (windJson.has("speed")) {
                                                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();

                                                // Convert to miles per hour
                                                double windSpeedInMilesPerHour = windSpeedInMetersPerSecond * 2.23694;

                                                dailyWindSpeeds.add(windSpeedInMilesPerHour);
                                            }
                                        } else {
                                            // If "wind" object is not present, assume no wind
                                            dailyWindSpeeds.add(0.0);
                                        }

                                    }

                                    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

                                    // Update UI with daily forecast values
                                    //updateDailyForecastUI(dailyWindSpeeds, dailyRainfalls);
                                    parseAndPredict(currentWindSpeed, currentRainfall,dailyWindSpeeds);
                                } else {
                                    // Handle the case where forecastList is empty
                                    Toast.makeText(UserLocation.this, "Forecast data is empty", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UserLocation.this, "Failed to fetch forecast data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(UserLocation.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(UserLocation.this, "Failed to fetch current weather data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(UserLocation.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

        UserActivity();

    }

    private void parseAndPredict(double currentWindSpeed, double currentRainfall, List<Double> dailyWindSpeeds) {
        if (addresses != null && addresses.size() > 0) {
            String currentLocality = addresses.get(0).getLocality();
            String subAdminArea = addresses.get(0).getSubAdminArea();

            // For simplicity, you can display the extracted parameters
            Log.d("ParsedData", "Current Locality: " + currentLocality);
            Log.d("ParsedData", "SubAdminArea: " + subAdminArea);
            Log.d("ParsedData", "Current Wind Speed: " + currentWindSpeed + " m/s");
            Log.d("ParsedData", "Current Rainfall: " + currentRainfall + " mm");



            // The wind speed for the upcoming 4 days
            for (int i = 0; i < dailyWindSpeeds.size(); i++) {
                Log.d("ParsedData", String.format("Day %d Wind Speed: %.2f m/s", i + 1, dailyWindSpeeds.get(i)));
                wind_Speed_Data.add(dailyWindSpeeds.get(i));



            }
            Log.d("**********************",  wind_Speed_Data.toString() + "Final");


            // The average rainfall for the upcoming 4 days
            List<Double> averageDailyRainfalls = getAverageRainfallData(addresses.get(0).getSubAdminArea(), Calendar.getInstance().get(Calendar.MONTH) + 1);

            for (int i = 0; i < averageDailyRainfalls.size(); i++) {
                Log.d("ParsedData", String.format("Day %d Average Rainfall: %.2f mm", i + 1, averageDailyRainfalls.get(i)));
                rainfall_Data.add(averageDailyRainfalls.get(i));
            }



            Log.d("**********************",  rainfall_Data.toString()  + "Final");



            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            assert currentUser != null;
            userId = currentUser.getUid();


            DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-4532a.firebaseio.com").getReference().child(userId);
            database2.child("Rainfall data").setValue(rainfall_Data);
            database2.child("WindSpeed data").setValue(wind_Speed_Data);





            // Get UI values for nearby cities
            TextView nearbyCity1TextView = findViewById(R.id.nearbyCity1TextView);
            TextView nearbyCity2TextView = findViewById(R.id.nearbyCity2TextView);
            String nearbyCity1 = nearbyCity1TextView.getText().toString();
            String nearbyCity2 = nearbyCity2TextView.getText().toString();

            if (nearbyCity1.startsWith("Nearby City 1: ")) {
                nearbyCity1 = nearbyCity1.substring("Nearby City 1: ".length());
            }

            if (nearbyCity2.startsWith("Nearby City 2: ")) {
                nearbyCity2 = nearbyCity2.substring("Nearby City 1: ".length());
            }

            // Log UI values for nearby cities
            Log.d("ParsedData",nearbyCity1);
            Log.d("ParsedData",nearbyCity2);
        } else {

            Log.e("ParsedData", "Addresses is null or empty");
        }
    }

    private double getCurrentWindSpeed(JsonObject weatherJson) {
        if (weatherJson != null && weatherJson.has("wind")) {
            JsonObject windJson = weatherJson.getAsJsonObject("wind");
            if (windJson.has("speed")) {
                // Get wind speed in meters per second
                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();

                // Convert to miles per hour
                double windSpeedInMilesPerHour = windSpeedInMetersPerSecond * 2.23694;

                return windSpeedInMilesPerHour;
            }
        }
        return 0.0;
    }

    private double getCurrentRainfall(JsonObject weatherJson) {
        if (weatherJson != null && weatherJson.has("rain")) {
            JsonObject rainJson = weatherJson.getAsJsonObject("rain");
            if (rainJson.has("1h")) {
                return rainJson.get("1h").getAsDouble();
            }
        }
        return 0.0;
    }




    private void updateWindSpeedUI(double windSpeed) {
        windSpeedTextView.setText("Wind Speed: " + windSpeed + " m/s");
    }

    // Update UI with rainfall information
    private void updateRainfallUI(double rainfallVolume) {
        rainfallTextView.setText("Rainfall: " + rainfallVolume + " mm");
    }


    // Inside your MainActivity
    private void updateDailyForecastUI(List<Double> dailyWindSpeeds, List<Double> dailyRainfalls) {
        TextView day1Forecast = findViewById(R.id.day1Forecast);
        TextView day2Forecast = findViewById(R.id.day2Forecast);
        TextView day3Forecast = findViewById(R.id.day3Forecast);
        //TextView day4Forecast = findViewById(R.id.day4Forecast);

        // Log the received wind speed values
        //Log.d("WeatherData", "Received Wind Speeds: " + dailyWindSpeeds);


        // Get the current month
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Log.d("CurrentMonth", "Current Month: " + currentMonth);


        //List<Double> averageDailyRainfalls = getAverageRainfallData(addresses.get(0).getSubAdminArea(), currentMonth);

        // Check if addresses is not null and not empty
        if (addresses != null && !addresses.isEmpty()) {
            List<Double> averageDailyRainfalls = getAverageRainfallData(addresses.get(0).getSubAdminArea(), currentMonth);

            // Update UI for Day 1 with average daily rainfall
            day1Forecast.setText(String.format(Locale.getDefault(), "Day 1: Wind Speed - %.2f m/s, Rainfall - %.2f mm",
                    dailyWindSpeeds.get(0),averageDailyRainfalls.get(0)));

            // Update UI for Day 2 with average daily rainfall
            day2Forecast.setText(String.format(Locale.getDefault(), "Day 2: Wind Speed - %.2f m/s, Rainfall - %.2f mm",
                    dailyWindSpeeds.get(1),averageDailyRainfalls.get(1)));

            // Update UI for Day 3 with average daily rainfall
            day3Forecast.setText(String.format(Locale.getDefault(), "Day 3: Wind Speed - %.2f m/s, Rainfall - %.2f mm",
                    dailyWindSpeeds.get(2),averageDailyRainfalls.get(2)));

            // Update UI for Day 4 with average daily rainfall
//            day4Forecast.setText(String.format(Locale.getDefault(), "Day 4: Wind Speed - %.2f m/s, Rainfall - %.2f mm",
//                    dailyWindSpeeds.get(3),averageDailyRainfalls.get(3)));

            // Log the received average daily rainfall values
            Log.d("WeatherData", "Received Average Daily Rainfalls: " + averageDailyRainfalls);
        } else {
            // Handle the case where addresses is null or empty
            Log.e("WeatherData", "Addresses is null or empty");
            // You can display an error message or perform any other appropriate actions
        }
    }

    private List<Double> getAverageRainfallData(String district, int currentMonth) {
        List<Double> averageDailyRainfalls = new ArrayList<>();

        // Read and parse your CSV file to get average daily rainfall data
        // Replace the following lines with your actual logic to read and parse the CSV file
        try (InputStream inputStream = getResources().getAssets().open("Rainfall_Daily_Average.csv")) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
            List<String[]> csvData = csvReader.readAll();

            // Assuming the CSV structure is like: District, Month1, Month2, ..., Month12
            for (String[] row : csvData) {
                String csvDistrict = row[0].trim();
                if (csvDistrict.equalsIgnoreCase(district)) {
                    // Assuming 12 months of data starting from index 1
                    int currentMonthIndex = currentMonth; // Use the current month index
                    for (int j = 0; j < 3; j++) {
                        if (currentMonthIndex >= 1 && currentMonthIndex <= 12) {
                            double rainfall = Double.parseDouble(row[currentMonthIndex].trim());
                            averageDailyRainfalls.add(rainfall);
                        } else {
                            // Handle the case where the month index is not in the valid range
                            averageDailyRainfalls.add(0.0);
                        }
                    }
                    return averageDailyRainfalls;

                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        // Handle the case where the district data is not found
        return averageDailyRainfalls;
    }






    public void UserActivity(){


//
//        Log.d("**********************", wind_Speed_Data.toString());
//
//        Log.d("**********************", rainfall_Data.toString());




//
//        Intent intent2 = new Intent(this, UserActivity.class);
//
//
//        intent2.putExtra("RAINFALL", String.valueOf(rainfall_Data));
//        intent2.putExtra("WINDSPEED", String.valueOf(rainfall_Data));
//        startActivity(intent2);
//
//        startActivity(intent2);
//        finish();

    }


}