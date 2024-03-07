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

    private NearbyCities getNearbyLocationNames(double latitude, double longitude) {
        double latitudeOffset = 0.01;
        double longitudeOffset = 0.02;

        double nearbyLatitude1 = latitude + latitudeOffset;
        double nearbyLongitude1 = longitude + longitudeOffset;

        double nearbyLatitude2 = latitude - latitudeOffset;
        double nearbyLongitude2 = longitude - longitudeOffset;

        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

        try {
            List<Address> nearbyAddresses1 = geocoder.getFromLocation(nearbyLatitude1, nearbyLongitude1, 1);
            if (nearbyAddresses1 != null && nearbyAddresses1.size() > 0) {
                String nearbyCity1 = nearbyAddresses1.get(0).getLocality();
                Log.d("NearbyLocation", "Nearby City 1: " + nearbyCity1);

                List<Address> nearbyAddresses2 = geocoder.getFromLocation(nearbyLatitude2, nearbyLongitude2, 1);
                if (nearbyAddresses2 != null && nearbyAddresses2.size() > 0) {
                    String nearbyCity2 = nearbyAddresses2.get(0).getLocality();
                    if (!nearbyCity2.equals(nearbyCity1)) {
                        Log.d("NearbyLocation", "Nearby City 2: " + nearbyCity2);
                        return new NearbyCities(nearbyCity1, nearbyCity2);
                    } else {
                        Log.e("NearbyLocation", "City 2 is the same as City 1");
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

        return new NearbyCities("", "");
    }

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

    private void parseAndPredict(double currentWindSpeed, double currentRainfall, List<Double> dailyWindSpeeds) {
        if (addresses != null && addresses.size() > 0) {
            String currentLocality = addresses.get(0).getLocality();
            String subAdminArea = addresses.get(0).getSubAdminArea();
            
            Log.d("ParsedData", "Current Locality: " + currentLocality);
            Log.d("ParsedData", "SubAdminArea: " + subAdminArea);
            Log.d("ParsedData", "Current Wind Speed: " + currentWindSpeed + " m/s");
            Log.d("ParsedData", "Current Rainfall: " + currentRainfall + " mm");

            for (int i = 0; i < dailyWindSpeeds.size(); i++) {
                Log.d("ParsedData", String.format("Day %d Wind Speed: %.2f m/s", i + 1, dailyWindSpeeds.get(i)));
            }
            List<Double> averageDailyRainfalls = getAverageRainfallData(addresses.get(0).getSubAdminArea(), Calendar.getInstance().get(Calendar.MONTH) + 1);

            for (int i = 0; i < averageDailyRainfalls.size(); i++) {
                Log.d("ParsedData", String.format("Day %d Average Rainfall: %.2f mm", i + 1, averageDailyRainfalls.get(i)));
            }

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
                double windSpeedInMetersPerSecond = windJson.get("speed").getAsDouble();
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

    private List<Double> getAverageRainfallData(String district, int currentMonth) {
        List<Double> averageDailyRainfalls = new ArrayList<>();

        try (InputStream inputStream = getResources().getAssets().open("Rainfall_Daily_Average.csv")) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
            List<String[]> csvData = csvReader.readAll();
            for (String[] row : csvData) {
                String csvDistrict = row[0].trim();
                if (csvDistrict.equalsIgnoreCase(district)) {
                    int currentMonthIndex = currentMonth;
                    for (int j = 0; j < 3; j++) {
                        if (currentMonthIndex >= 1 && currentMonthIndex <= 12) {
                            double rainfall = Double.parseDouble(row[currentMonthIndex].trim());
                            averageDailyRainfalls.add(rainfall);
                        } else {
                            averageDailyRainfalls.add(0.0);
                        }
                    }
                    return averageDailyRainfalls;

                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return averageDailyRainfalls;
    }




