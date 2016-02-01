package com.walladog.walladog.utils;

import android.content.Context;

import com.walladog.walladog.WalladogApp;

/**
 * Created by hadock on 30/01/16.
 *
 */
public class SearchObject {

    private String mLatitude = null;
    private String mLongitude = null;
    private String mSearchString = null;
    private String mDistance = null;
    private String mCategory = null;
    private String mRace = null;



    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getSearchString() {
        return mSearchString;
    }

    public void setSearchString(String searchString) {
        mSearchString = searchString;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getRace() {
        return mRace;
    }

    public void setRace(String race) {
        mRace = race;
    }

    public SearchObject() {

        mLatitude = String.valueOf(WalladogApp.context
                .getSharedPreferences(WalladogApp.class.getSimpleName(), Context.MODE_PRIVATE)
                .getString("WDLat",null));
        mLongitude = String.valueOf(WalladogApp.context
                .getSharedPreferences(WalladogApp.class.getSimpleName(), Context.MODE_PRIVATE)
                .getString("WDLong",null));
    }


}
