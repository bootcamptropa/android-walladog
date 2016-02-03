package com.walladog.walladog.models;

import org.json.JSONObject;


public class State {

    private int id;
    private String name;


    public State () {

    }

    public State(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public State (JSONObject json) {

        this.id = json.optInt("id");
        this.name = json.optString("name");

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
}
