package com.walladog.walladog.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.walladog.walladog.R;

/**
 * Created by hadock on 30/12/15.
 *
 */
public class WDUtils {

    public static void applyFontForToolbarTitle(Activity a){
        Toolbar toolbar = (Toolbar) a.findViewById(R.id.toolbar);
        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);
            if(view instanceof TextView){
                TextView tv = (TextView) view;
                if(tv.getText().equals(a.getTitle())){
                    tv.setGravity(Gravity.CENTER); //TODO no funciona
                    Typeface face=Typeface.createFromAsset(a.getAssets(),"fonts/Lobster-Regular.ttf");
                    tv.setTextSize(25);
                    tv.setTypeface(face);
                    break;
                }
            }
        }
    }

}
