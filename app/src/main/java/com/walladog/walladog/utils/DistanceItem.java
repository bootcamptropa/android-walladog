package com.walladog.walladog.utils;

/**
 * Created by hadock on 30/01/16.
 *
 */
public class DistanceItem {
    private int distance;
    private String distanceName;

    public DistanceItem(int distance) {
        this.distance = distance;
        this.distanceName = String.valueOf(distance)+" Km";
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
        this.distanceName = String.valueOf(distance)+" Km";
    }

    public String getDistanceName() {
        return distanceName;
    }
}
