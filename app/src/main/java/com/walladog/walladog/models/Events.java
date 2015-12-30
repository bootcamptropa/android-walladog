package com.walladog.walladog.models;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.walladog.walladog.models.interfaces.DAOPersistable;

public class Events implements DAOPersistable<Events> {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("id")
    private double id;

    @SerializedName("name")
    private String name;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Events(Double id, String name, Boolean success) {

        this.id = id;
        this.name = name;
        this.success = success;

    }

    @Override
    public String toString() {
        return "{id = "+id+", name = "+name+"}";
    }

    @Override
    public long insert(@NonNull Events data) {
        return 0;
    }

    @Override
    public void update(long id, @NonNull Events data) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void delete(@NonNull Events data) {

    }

    @Override
    public void deleteAll() {

    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        return null;
    }

    @Override
    public Events query(long id) {
        return null;
    }
}
