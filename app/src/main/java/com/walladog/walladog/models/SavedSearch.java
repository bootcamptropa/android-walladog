package com.walladog.walladog.models;

import org.json.JSONObject;


public class SavedSearch {

    private double raceId;
    private double userId;
    private double longitude;
    private double id;
    private String keywords;
    private double categoryId;
    private double latitude;


    public SavedSearch () {

    }

    public SavedSearch (JSONObject json) {

        this.raceId = json.optDouble("raceId");
        this.userId = json.optDouble("userId");
        this.longitude = json.optDouble("longitude");
        this.id = json.optDouble("id");
        this.keywords = json.optString("keywords");
        this.categoryId = json.optDouble("categoryId");
        this.latitude = json.optDouble("latitude");

    }

    public double getRaceId() {
        return this.raceId;
    }

    public void setRaceId(double raceId) {
        this.raceId = raceId;
    }

    public double getUserId() {
        return this.userId;
    }

    public void setUserId(double userId) {
        this.userId = userId;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getId() {
        return this.id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public double getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(double categoryId) {
        this.categoryId = categoryId;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "{id = "+id+", keywords = "+keywords+", raceId = "+raceId+", categoryId = "+categoryId+", userId = "+userId+", longitude = "+longitude+", latitude = "+latitude+"}";
    }
}