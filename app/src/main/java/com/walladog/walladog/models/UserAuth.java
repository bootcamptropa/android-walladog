package com.walladog.walladog.models;

import com.google.gson.annotations.SerializedName;

/**
 * Tiny authObject for autentication
 */

public class UserAuth {

    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserAuth() {
    }

    public UserAuth(String username, String password) {
        this.password = password;
        this.email = username;
    }

    @Override
    public String toString() {
        return "Object userAuth";
    }

}
