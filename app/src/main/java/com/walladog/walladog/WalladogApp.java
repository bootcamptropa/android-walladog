package com.walladog.walladog;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by hadock on 1/01/16.
 *
 */
public class WalladogApp extends Application {

    private static final String TAG = WalladogApp.class.getName();

    public static Context context;

    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    protected String SENDER_ID = "838012343378";
    private GoogleCloudMessaging gcm =null;
    private String regid = null;


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
}
