package com.walladog.walladog.models;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.walladog.walladog.models.interfaces.DAOPersistable;

import java.io.Serializable;

/**
 * Created by hadock on 14/12/15.
 *
 */

public class WDServices implements DAOPersistable<WDServices>,Serializable {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("name")
    private String name;

    @SerializedName("serviceImage")
    private String serviceImage;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

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

    @Override
    public long insert(@NonNull WDServices data) {
        return 0;
    }

    @Override
    public void update(long id, @NonNull WDServices data) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void delete(@NonNull WDServices data) {

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
    public WDServices query(long id) {
        return null;
    }



/*    public void getApiWDServices(final ProgressDialog dialog) {

        WDServicesService testService = ServiceGenerator.createService(WDServicesService.class);
        Call<List<WDWDServices>> call = testService.getMultiTask();

        call.enqueue(new Callback<List<WDServices>>() {
            @Override
            public void onResponse(Response<List<WDServices>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Log.v("API-JSON:", response.body().toString());
                    List<WDServices> test = response.body();
                    Log.v("ANDROID-OBJECT", test.toString());
                    Log.v("FIRST NAME:", String.valueOf(test.get(0).getName()));
                    dialog.dismiss();

                } else {
                    Log.v("RAMON", "Sin respuesta");
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // something went completely south (like no internet connection)
                dialog.dismiss();
                Log.d("Error", t.getMessage());
            }
        });

    }*/

}