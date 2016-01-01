package com.walladog.walladog;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by hadock on 1/01/16.
 *
 */
public class WalladogApp extends Application implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = WalladogApp.class.getName();

    public static Context context;

    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    protected String SENDER_ID = "838012343378";
    private GoogleCloudMessaging gcm =null;
    private String regid = null;

    //Location
    private GoogleApiClient mGoogleApiClient = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        context = getApplicationContext();

        if (checkPlayServices())
        {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()){
                registerInBackground();
            }else{
                Log.d(TAG, "No valid Google Play Services APK found.");
            }
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
                mGoogleApiClient.connect();
            }
        }

    }

    //GCM
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            Log.i(TAG, "No tiene instalada Google Play Services");
            return false;
        }
        return true;
    }

    //Get regID
    private String getRegistrationId(Context context)
    {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.d(TAG, "Registration ID not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.d(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context)
    {
        return getSharedPreferences(WalladogApp.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context)
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


    private void registerInBackground(){

        AsyncTask task = new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] params) {
                if(gcm==null){
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                try {
                    String regId = gcm.register(SENDER_ID);
                    Log.d(TAG,regId);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                //Log.v(TAG,"Post execute");
            }
        };

        task.execute();

    }

    private void saveLatLongCoords(String latitude,String longitude){
        Log.v(TAG,"Curdate: "+String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000));
        getSharedPreferences(WalladogApp.class.getSimpleName(), Context.MODE_PRIVATE)
                .edit()
                .putString("WDLat", latitude)
                .putString("WDLong",longitude)
                .putString("WDLatLongUpdate",String.valueOf(Calendar.getInstance().getTimeInMillis()/1000))
                .commit();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            String latitudeText = (String.valueOf(mLastLocation.getLatitude()));
            String longitudeText = (String.valueOf(mLastLocation.getLongitude()));
            Log.v(TAG,"Lat is : "+ latitudeText +" and Long: "+ longitudeText);
            saveLatLongCoords(latitudeText,longitudeText);
        }else{
            Log.v(TAG,"Lag/Lang is null");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.v(TAG,"Ojo con la memoria Pardal!");
    }
}
