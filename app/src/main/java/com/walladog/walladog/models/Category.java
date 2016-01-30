package com.walladog.walladog.models;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable {

    private double id;
    private double id_category;
    private String name;
    private Date creationDate;
    private Date modificationDate;

    public Category(){

    }

    public Category(String name) {
        this.name = name;
        this.creationDate = new Date();
    }

    public Category(double id, double id_category, String name, Date creationDate, Date modificationDate) {
        this.id = id;
        this.id_category = id_category;
        this.name = name;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public Category(double id, String name, Date creationDate, Date modificationDate) {
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

    public double getId_category() {
        return id_category;
    }

    public void setId_category(double id_category) {
        this.id_category = id_category;
    }
}
