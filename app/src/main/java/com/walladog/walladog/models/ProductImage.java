package com.walladog.walladog.models;

import java.io.Serializable;

/**
 * Created by hadock on 31/12/15.
 *
 */

public class ProductImage implements Serializable{

    private int id;
    private String name;
    private String photo_url;
    private String photo_thumbnail_url;

    public ProductImage(int id, String name, String photo_url, String photo_thumbnail_url) {
        this.id = id;
        this.name = name;
        this.photo_url = photo_url;
        this.photo_thumbnail_url = photo_thumbnail_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPhoto_thumbnail_url() {
        return photo_thumbnail_url;
    }

    public void setPhoto_thumbnail_url(String photo_thumbnail_url) {
        this.photo_thumbnail_url = photo_thumbnail_url;
    }
}
