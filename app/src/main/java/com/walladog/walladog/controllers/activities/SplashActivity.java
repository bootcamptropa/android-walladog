package com.walladog.walladog.controllers.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.walladog.walladog.R;
import com.walladog.walladog.models.WDServices;

/**
 * Created by hadock on 14/12/15.
 *
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getName();
    private long splashDelay = 3000; // 6 segundos
    public ProgressBar progressBar;
    private TextView appTitle,appLoading = null;

    private WDServices mWDServices = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        appTitle = (TextView) findViewById(R.id.app_title);
        appLoading = (TextView) findViewById(R.id.txt_loading);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Lobster-Regular.ttf");

        appTitle.setTextSize(30);
        appTitle.setTypeface(face);
        appLoading.setTextSize(15);
        appLoading.setTypeface(face);

        StartAnimations();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //LaunchApp();
            }
        }, splashDelay);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        Animation animScaleDown = AnimationUtils.loadAnimation(this, R.anim.rotate);
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.startAnimation(animScaleDown);

    }

    private void LaunchApp(){





        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
