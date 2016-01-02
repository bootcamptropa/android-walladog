package com.walladog.walladog.models;

import java.io.Serializable;

/**
 * Created by hadock on 14/12/15.
 *
 */

public class WDServices implements Serializable {

    private String name;
    private String serviceImage;
    private String description;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}