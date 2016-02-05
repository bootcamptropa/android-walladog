package com.walladog.walladog.models;

import java.io.Serializable;

/**
 * Created by hadock on 31/12/15.
 *
 */

public class UserData implements Serializable{

    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String email;
    private String avatar_url;
    private String avatar_thumbnail_url;
    private String products_count;
    private String password;
    private String password2;


    public UserData(int id, String first_name, String last_name, String username, String email, String avatar_url, String avatar_thumbnail_url, String products_count) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.email = email;
        this.avatar_url = avatar_url;
        this.avatar_thumbnail_url = avatar_thumbnail_url;
        this.products_count = products_count;
    }

    public UserData(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserData(){

    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProducts_count() {
        return products_count;
    }

    public void setProducts_count(String products_count) {
        this.products_count = products_count;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

}
