package com.walladog.walladog.models;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by hadock on 31/12/15.
 *
 */

public class ProductSeller implements Serializable{

    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String avatar_url;
    private String avatar_thumbnail_url;
    private int products_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getAvatar_thumbnail_url() {
        return avatar_thumbnail_url;
    }

    public void setAvatar_thumbnail_url(String avatar_thumbnail_url) {
        this.avatar_thumbnail_url = avatar_thumbnail_url;
    }

    public int getProducts_count() {
        return products_count;
    }

    public void setProducts_count(int products_count) {
        this.products_count = products_count;
    }
}
