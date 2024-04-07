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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class LocationDetails2 extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView country, city, district, address, latitude, longitude;
    Button getLocation;
    private TextView windSpeedTextView, rainfallTextView;
    private WeatherApi weatherApi;
    private List<Address> addresses;
    List<Double> rainfall_Data = new ArrayList<>();
    List<Double> wind_Speed_Data = new ArrayList<>();

    private String userId;
    String disasterType;


    String string_Latitude;
    String string_Longitude;
    String string_City;
    String string_District;


    double user_selected_longitude;
    double user_selected_latitude;

    private String user_selected_city;
    private String user_selected_district;



    TextView progressNumber;


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
        progressNumber = findViewById(R.id.progressNumber);


        progressNumber = findViewById(R.id.progressNumber);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Intent intent = getIntent();

        disasterType = intent.getStringExtra("DISASTER");
        user_selected_city = intent.getStringExtra("USER_SELECTED_CITY");
        user_selected_district = intent.getStringExtra("USER_SELECTED_DISTRICT");
        user_selected_latitude = Double.parseDouble(Objects.requireNonNull(intent.getStringExtra("USER_SELECTED_LATITUDE")));
        user_selected_longitude = Double.parseDouble(Objects.requireNonNull(intent.getStringExtra("USER_SELECTED_LONGITUDE")));


        Log.d("******************************","DISASTER TYPE IN LOCATION 2" + disasterType);
        Log.d("******************************","LATITUDE in Location2 " + user_selected_latitude);
        Log.d("******************************","LONGITUDE in Location2" + user_selected_longitude);


        getLocationDetails(user_selected_latitude,user_selected_longitude, user_selected_city, user_selected_district);

    }
    public void getLocationDetails(double user_selected_latitude, double user_selected_longitude, String user_selected_city, String user_selected_district){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Log.d("Location", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
                                Geocoder geocoder = new Geocoder(LocationDetails2.this, Locale.getDefault());

                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    if (addresses != null && addresses.size() > 0) {
                                        // Update UI with location details



                                        string_Latitude = String.valueOf(user_selected_latitude);
                                        string_Longitude = String.valueOf(user_selected_longitude);
                                        string_City = user_selected_city;
                                        string_District = user_selected_district;

                                        getWeatherData(user_selected_latitude, user_selected_longitude);

//                                        city.setText("City: " + addresses.get(0).getLocality());
//                                        district.setText("District: " + addresses.get(0).getSubAdminArea());



                                        NearbyCities nearbyCities = getNearbyLocationNames(user_selected_latitude, user_selected_longitude);
                                        updateNearbyCitiesUI(nearbyCities.getCity1(), nearbyCities.getCity2());

                                        progressNumber.setText("50%");


                                    } else {
                                        // Handle the case where no address is found
                                        Log.e("Location", "No ad" +
                                                "dress found for the location");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // Call the method to process weather data
                                getWeatherData(user_selected_latitude, user_selected_longitude);
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
            Toast.makeText(LocationDetails2.this, "Location is required", Toast.LENGTH_SHORT).show();
        }



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

        Geocoder geocoder = new Geocoder(LocationDetails2.this, Locale.getDefault());

        try {
            // Get the first nearby location
            List<Address> nearbyAddresses1 = geocoder.getFromLocation(nearbyLatitude1, nearbyLongitude1, 1);
            if (nearbyAddresses1 != null && nearbyAddresses1.size() > 0) {
                String nearbyCity1 = nearbyAddresses1.get(0).getLocality();

                // Get the second nearby location
                List<Address> nearbyAddresses2 = geocoder.getFromLocation(nearbyLatitude2, nearbyLongitude2, 1);
                if (nearbyAddresses2 != null && nearbyAddresses2.size() > 0) {

                    String nearbyCity2 = nearbyAddresses2.get(0).getLocality();

                    if ( nearbyCity2 != null) {

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

        // Return a default value if the cities cannot be determined
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

    public void getWeatherData(double latitude, double longitude) {
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

                                    List<Double> dailyWindSpeeds = new ArrayList<>();
                                    List<Double> dailyRainfalls = new ArrayList<>();

                                    // Extract daily forecast data for the next 4 days
                                    for (int i = 0; i < Math.min(4, forecastList.size()); i++) {
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
                                    parseAndPredict(currentWindSpeed, currentRainfall, dailyWindSpeeds);
                                } else {
                                    // Handle the case where forecastList is empty
                                    Toast.makeText(LocationDetails2.this, "Forecast data is empty", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Toast.makeText(LocationDetails.this, "Failed to fetch forecast data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(LocationDetails2.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(LocationDetails2.this, "Failed to fetch current weather data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LocationDetails2.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void parseAndPredict(double currentWindSpeed, double currentRainfall, List<Double> dailyWindSpeeds) {
        if (addresses != null && addresses.size() > 0) {
//

            // The wind speed for the upcoming 4 days
            for (int i = 0; i < dailyWindSpeeds.size(); i++) {
                wind_Speed_Data.add(dailyWindSpeeds.get(i));


            }

            // The average rainfall for the upcoming 4 days
            List<Double> averageDailyRainfalls = getAverageRainfallData(string_District, Calendar.getInstance().get(Calendar.MONTH) + 1);

            for (int i = 0; i < averageDailyRainfalls.size(); i++) {
                Log.d("ParsedData", String.format("Day %d Average Rainfall: %.2f mm", i + 1, averageDailyRainfalls.get(i)));
                rainfall_Data.add(averageDailyRainfalls.get(i));
            }

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





            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();
                DatabaseReference database2 = FirebaseDatabase.getInstance("https://natural-disaster-predict-1838a-4532a.firebaseio.com/").getReference().child(userId);

                // Create a list of tasks for all write operations
                List<Task<Void>> tasks = new ArrayList<>();
                tasks.add(database2.child("Rainfall data").setValue(rainfall_Data));
                tasks.add(database2.child("WindSpeed data").setValue(wind_Speed_Data));
                tasks.add(database2.child("Near By City 1").setValue(nearbyCity1));
                tasks.add(database2.child("Near By City 2").setValue(nearbyCity2));
                tasks.add(database2.child("Latitude").setValue(string_Latitude));
                tasks.add(database2.child("Longitude").setValue(string_Longitude));
                tasks.add(database2.child("City").setValue(string_City));
                tasks.add(database2.child("District").setValue(string_District));



                // Wait for all tasks to complete
                Tasks.whenAll(tasks).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressNumber.setText("90%");

                        nextActivity();

                    } else {
                        // Handle the error
                        Log.e("FirebaseWrite", "Failed to write all data to Firebase");

                    }
                });
            } else {
                Log.e("FirebaseUser", "No authenticated user found.");
            }

            // Log UI values for nearby cities
            Log.d("ParsedData", nearbyCity1);
            Log.d("ParsedData", nearbyCity2);
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

        // Handle the case where the district data is not found
        return averageDailyRainfalls;
    }

    public void  nextActivity(){

        if (disasterType.equals("cyclone")){
            CycloneActivity();
        }

        else if (disasterType.equals("landslide")){
            LandslideActivity();
        }

        else if (disasterType.equals("flood")){
            Log.d("************************************************","DIsasrer type is in flood ACTIVITY" + disasterType +"I SHOULD HERE");
            FloodActivity();
        }


        else if (disasterType.equals("full-report")){
            Log.d("************************************************","DIsasrer type is in flood ACTIVITY" + disasterType +"I SHOULD HERE");
            ReportActivity();
        }

    }

    public void  FloodActivity(){
        Intent intent = new Intent(this, FloodActivity.class);
        startActivity(intent);
        finish();

    }

    public void  CycloneActivity(){
        Intent intent = new Intent(this, CycloneActivity.class);
        startActivity(intent);
        finish();

    }

    public void  LandslideActivity(){
        Intent intent = new Intent(this, LandslideActivity.class);
        startActivity(intent);
        finish();
    }

    public void ReportActivity(){
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
        finish();
    }


}