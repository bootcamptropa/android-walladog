package com.walladog.walladog.models;

import java.io.Serializable;

/**
 * Created by hadock on 31/12/15.
 *
 */

public class Photo implements Serializable{

    private String photoUrl;
    private String photoTitle;

    public Photo(String photoTitle,String photoUrl) {
        this.photoUrl = photoUrl;
        this.photoTitle = photoTitle;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }
}
