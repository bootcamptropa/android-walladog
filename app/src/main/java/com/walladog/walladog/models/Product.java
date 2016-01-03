package com.walladog.walladog.models;

import org.json.JSONObject;

import java.io.Serializable;


public class Product implements Serializable {


    private double id;
    //@SerializedName("name")
    private String name;
    private double raceId;
    private double sellerId;
    private String gender;
    private boolean sterile;
    private String description;
    private String publishDate;
    private double stateId;
    private double price;
    private double categoryId;
    private double latitude;
    private double longitude;
    private boolean active;



    public Product () {

    }

    public Product (JSONObject json) {

        this.gender = json.optString("gender");
        this.stateId = json.optDouble("stateId");
        this.raceId = json.optDouble("raceId");
        this.id = json.optDouble("id");
        this.price = json.optDouble("price");
        this.sellerId = json.optDouble("sellerId");
        this.sterile = json.optBoolean("sterile");
        this.publishDate = json.optString("publish_date");
        this.categoryId = json.optDouble("categoryId");
        this.description = json.optString("description");
        this.name = json.optString("name");

    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getStateId() {
        return this.stateId;
    }

    public void setStateId(double stateId) {
        this.stateId = stateId;
    }

    public double getRaceId() {
        return this.raceId;
    }

    public void setRaceId(double raceId) {
        this.raceId = raceId;
    }

    public double getId() {
        return this.id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(double sellerId) {
        this.sellerId = sellerId;
    }

    public boolean getSterile() {
        return this.sterile;
    }

    public void setSterile(boolean sterile) {
        this.sterile = sterile;
    }

    public String getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public double getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(double categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
