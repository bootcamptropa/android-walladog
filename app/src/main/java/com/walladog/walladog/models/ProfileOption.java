package com.walladog.walladog.models;

/**
 * Created by hadock on 3/01/16.
 *
 */
public class ProfileOption {

    private String optionName = null;
    private String optionPicture = null;

    public ProfileOption(String optionName, String optionPicture) {
        this.optionName = optionName;
        this.optionPicture = optionPicture;
    }

    public ProfileOption(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionPicture() {
        return optionPicture;
    }

    public void setOptionPicture(String optionPicture) {
        this.optionPicture = optionPicture;
    }
}
