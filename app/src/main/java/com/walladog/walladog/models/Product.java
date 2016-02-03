package com.walladog.walladog.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;


public class Product implements Serializable {


    private double id;
    private String name;
    private String race;
    private double raceid;
    private String gender;
    private boolean sterile;
    private String description;
    private String state;
    private int stateid;
    private Boolean active;
    private String latitude;
    private String longitude;
    private double price;
    private String category;
    private int categoryid;

    private ProductSeller seller;
    private List<ProductImage> images;

    public Product() {
    }

    public Product(double id, String name, String race, double raceid, String gender, boolean sterile, String description, String state, int stateid, Boolean active, String latitude, String longitude, double price, String category, int categoryid, ProductSeller seller, List<ProductImage> images) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.raceid = raceid;
        this.gender = gender;
        this.sterile = sterile;
        this.description = description;
        this.state = state;
        this.stateid = stateid;
        this.active = active;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.category = category;
        this.categoryid = categoryid;
        this.seller = seller;
        this.images = images;
    }

    public ProductSeller getSeller() {
        return seller;
    }

    public void setSeller(ProductSeller seller) {
        this.seller = seller;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public double getRaceid() {
        return raceid;
    }

    public void setRaceid(double raceid) {
        this.raceid = raceid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isSterile() {
        return sterile;
    }

    public void setSterile(boolean sterile) {
        this.sterile = sterile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStateid() {
        return stateid;
    }

    public void setStateid(int stateid) {
        this.stateid = stateid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
