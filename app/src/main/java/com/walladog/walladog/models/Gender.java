package com.walladog.walladog.models;

/**
 * Created by hadock on 3/02/16.
 *
 */
public class Gender {
    private String id;
    private String name;

    public Gender(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
