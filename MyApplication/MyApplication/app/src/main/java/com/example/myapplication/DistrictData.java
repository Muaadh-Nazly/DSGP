package com.example.live_location_two;

import java.util.List;

public class DistrictData {
    private String districtName;
    private List<Double> monthlyRainfalls;

    public String getDistrictName() {
        return districtName;
    }

    public List<Double> getMonthlyRainfalls() {
        return monthlyRainfalls;
    }

    public void setMonthlyRainfalls(List<Double> monthlyRainfalls) {
        this.monthlyRainfalls = monthlyRainfalls;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public DistrictData(String districtName, List<Double> monthlyRainfalls) {
        this.districtName = districtName;
        this.monthlyRainfalls = monthlyRainfalls;
    }

    // You can add getters and setters as needed
}
