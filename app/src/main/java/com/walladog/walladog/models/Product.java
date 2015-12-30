package com.walladog.walladog.models;

import org.json.JSONObject;

import java.io.Serializable;


public class Product implements Serializable {

    private String gender;
    private double stateId;
    private double raceId;
    private double id;
    private double price;
    private double sellerId;
    private boolean sterile;
    private String publishDate;
    private double categoryId;
    private String description;
    private String name;


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

    @Override
    public String toString() {
        return "{id = "+id+", publishDate = "+publishDate+",  categoryId = "+categoryId+", price = "+price+", sterile = "+sterile+", description = "+description+", name = "+name+", stateId = "+stateId+", gender = "+gender+", sellerId = "+sellerId+", raceId = "+raceId+"}";
    }
}
