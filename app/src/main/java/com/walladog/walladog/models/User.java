package com.walladog.walladog.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;


public class User {

    @SerializedName("detail")
    private Detail detail;
    @SerializedName("password")
    private String password;
    @SerializedName("id")
    private double id;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("company")
    private String company;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("email")
    private String email;


    public User () {
    }

    public User (JSONObject json) {

        this.detail = new Detail(json.optJSONObject("detail"));
        this.password = json.optString("password");
        this.id = json.optDouble("id");
        this.lastname = json.optString("lastname");
        this.company = json.optString("company");
        this.firstname = json.optString("firstname");
        this.email = json.optString("email");

    }

    public Detail getDetail() {
        return this.detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getId() {
        return this.id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{id = "+id+", detail = "+detail+", email = "+email+", lastname = "+lastname+", firstname = "+firstname+", password = "+password+"}";
    }

}
