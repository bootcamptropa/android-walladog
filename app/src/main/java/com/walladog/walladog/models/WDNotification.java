package com.walladog.walladog.models;

import java.util.Date;

public class WDNotification {

    private double id;
    private String title;
    private String message;
    private double author;
    private Boolean read;
    private Date creationDate;
    private Date modificationDate;

    public WDNotification(double id, String title, String message, double author, Boolean read, Date creationDate, Date modificationDate) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.author = author;
        this.read = read;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public WDNotification(String title, String message, String author) {
        this.title = title;
        this.message = message;
        this.author = Double.parseDouble(author);
        this.read=false;
        this.creationDate = new Date();
        this.modificationDate = new Date();
    }

    public WDNotification() {
        this.id = 0;
        this.title = "No title";
        this.message = "No message";
        this.author = 0;
        this.read = false;
        this.creationDate = new Date();
        this.modificationDate = new Date();
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAuthor() {
        return author;
    }

    public void setAuthor(double author) {
        this.author = author;
    }


    public Boolean isRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
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
}
