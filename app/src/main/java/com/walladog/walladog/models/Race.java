package com.walladog.walladog.models;

import java.util.Date;

public class Race {

    private double id;
    private double id_race;
    private String name;
    private Date creationDate;
    private Date modificationDate;

    public Race(){

    }

    public Race(String name) {
        this.name = name;
        this.creationDate = new Date();
    }

    public Race(double id, String name) {
        this.id = id;
        this.name = name;
    }

    public Race(double id, String name, Date creationDate, Date modificationDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public double getId() {
        return this.id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public double getId_race() {
        return id_race;
    }

    public void setId_race(double id_race) {
        this.id_race = id_race;
    }
}
