package com.walladog.walladog.models;

import org.json.JSONObject;


public class Detail {

    private double latitude;
    private String facebookToken;
    private double longitude;
    private String avatarUrl;


    public Detail () {

    }

    public Detail (JSONObject json) {

        this.latitude = json.optDouble("latitude");
        this.facebookToken = json.optString("facebook_token");
        this.longitude = json.optDouble("longitude");
        this.avatarUrl = json.optString("avatar_url");

    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getFacebookToken() {
        return this.facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "{facebook_token = "+facebookToken+", avatar_url = "+avatarUrl+", longitude = "+longitude+", latitude = "+latitude+"}";
    }

}
