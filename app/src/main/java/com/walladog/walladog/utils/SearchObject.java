package com.walladog.walladog.utils;

/**
 * Created by hadock on 30/01/16.
 *
 */
public class SearchObject {

    private double mLatitude;
    private double mLongitude;
    private String mSearchString;
    private int mDistance;
    private int mCategory;
    private int mRace;

    public SearchObject() {
    }

    public SearchObject(double latitude, double longitude, String searchString, int distance, int category, int race) {
        mLatitude = latitude;
        mLongitude = longitude;
        mSearchString = searchString;
        mDistance = distance;
        mCategory = category;
        mRace = race;
    }

    public SearchObject(double latitude, double longitude, int race, int category, int distance) {
        mLatitude = latitude;
        mLongitude = longitude;
        mRace = race;
        mCategory = category;
        mDistance = distance;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String getSearchString() {
        return mSearchString;
    }

    public void setSearchString(String searchString) {
        mSearchString = searchString;
    }

    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int distance) {
        mDistance = distance;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        mCategory = category;
    }

    public int getRace() {
        return mRace;
    }

    public void setRace(int race) {
        mRace = race;
    }
}
