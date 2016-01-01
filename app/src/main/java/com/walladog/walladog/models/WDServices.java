package com.walladog.walladog.models;

import java.io.Serializable;

/**
 * Created by hadock on 14/12/15.
 *
 */

public class WDServices implements Serializable {

    //@SerializedName("success")
    //private Boolean success;

    //@SerializedName("name")
    private String name;

    //@SerializedName("serviceImage")
    private String serviceImage;


/*
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
*/

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

}