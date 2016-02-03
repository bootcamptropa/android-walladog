package com.walladog.walladog.models;

/**
 * Created by hadock on 3/02/16.
 *
 */
public class Sterile {
    private int id;
    private String name;
    private Boolean value;

    public Sterile(int id, String name, Boolean value) {
        this.id = id;
        this.name = name;
        this.value = value;
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

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
