package com.walladog.walladog.models.responses;

import java.io.Serializable;

/**
 * Created by hadock on 1/01/16.
 *
 */
public class ErrorCode implements Serializable{

    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
