package com.example.myapplication;

public class WeatherResponse {
    private Wind wind;
    private Rain rain;

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public static class Wind {
        private double speed; // Wind speed in meters per second

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }

    public static class Rain {
        private double rainVolume; // Rain volume in millimeters

        public double getRainVolume() {
            return rainVolume;
        }

        public void setRainVolume(double rainVolume) {
            this.rainVolume = rainVolume;
        }
    }
}

