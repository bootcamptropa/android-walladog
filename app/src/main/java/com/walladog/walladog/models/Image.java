package com.walladog.walladog.models;

import org.json.JSONObject;

public class Image {

    private double id;
    private String photoUrl;
    private String name;
    private double productId;


    public Image () {

    }

    public Image (JSONObject json) {

        this.id = json.optDouble("id");
        this.photoUrl = json.optString("photoUrl");
        this.name = json.optString("name");
        this.productId = json.optDouble("productId");

    }

    public double getId() {
        return this.id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProductId() {
        return this.productId;
    }

    public void setProductId(double productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "{id = "+id+", name = "+name+", photoUrl = "+photoUrl+", productId = "+productId+"}";
    }
}

