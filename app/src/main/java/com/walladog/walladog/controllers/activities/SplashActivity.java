package com.walladog.walladog.controllers.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.walladog.walladog.R;
import com.walladog.walladog.models.ServiceGenerator;

import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.models.apiservices.WDServicesService;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by hadock on 14/12/15.
 *
 */
public class SplashActivity extends Activity {

    private static final String TAG = SplashActivity.class.getName();
    private long splashDelay = 3000; // 6 segundos
    public ProgressBar progressBar;
    private boolean firstUse;
    private Uri URLScheme = null;

    //GCM
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    protected String SENDER_ID = "your_sender_id";
    private GoogleCloudMessaging gcm =null;
    private String regid = null;
    private Context context= null;

    private List<WDServices> services = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = getIntent();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        context = getApplicationContext();
        if (checkPlayServices())
        {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty())
            {
                registerInBackground();
            }
            else
            {
                Log.d(TAG, "No valid Google Play Services APK found.");
            }
        }

        StartAnimations();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                getServices();
            }
        }, splashDelay);
    }


    //Helper Functions
    public void getServices() {
        WDServicesService testService = ServiceGenerator.createService(WDServicesService.class);
        Call<List<WDServices>> call = testService.getMultiTask();

        call.enqueue(new Callback<List<WDServices>>() {
            @Override
            public void onResponse(Response<List<WDServices>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<WDServices> test = response.body();
                    services = response.body();
                    Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    //Log.v(TAG, String.valueOf(test.get(0).getName()));
                } else {
                    Log.v(TAG, "No api response");

                }
            }

            @Override
            public void onFailure(Throwable t) {
                // something went completely south (like no internet connection)
                Log.d(TAG, t.getMessage());
            }
        });

    }



    //GCM
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.d(TAG, "This device is not supported - Google Play Services.");
                finish();
            }
            return false;
        }
        return true;
    }

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
        return getSharedPreferences(SplashActivity.class.getSimpleName(),
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
                    String regId = gcm.register("838012343378");
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
                Log.v(TAG,"Post execute");
            }
        };

        task.execute();

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.clearAnimation();
        iv.startAnimation(anim);

    }



}
