package com.walladog.walladog.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.walladog.walladog.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

    //Function NO ASYNC para pillar token (a pelo)
    private void OAuth2Login() throws IOException {

        final String USERNAME = "admin";
        final String PASSWORD = "Keepcoding123";
        final String CLIENT_ID = "uEAoxVEYOVmpg4Z8IAyCCEItlXO8Cf4G7RSu647d";
        final String CLIENT_SECRET = "Zc0JGu5pVUt7zNxrhQaCvit6ydr4WxMVI4nXluF9GBWgJV0rbUcR5y2uBZl3TgmLYryashJREp9AiIkbfRUVv5Cdd3n6ZX4Va3fI2cmvMwcgWRFYnrp7K8ZtwkopXrhV";
        final String URL_API = "http://api.walladog.com/o/token/";

        String data = URLEncoder.encode("grant_type", "UTF-8") + "=" + URLEncoder.encode( "password", "UTF-8" );
        data += "&" + URLEncoder.encode( "username", "UTF-8" ) + "=" + URLEncoder.encode( USERNAME, "UTF-8" );
        data += "&" + URLEncoder.encode( "password", "UTF-8" ) + "=" + URLEncoder.encode( PASSWORD, "UTF-8" );
        data += "&" + URLEncoder.encode( "client_id", "UTF-8" ) + "=" + URLEncoder.encode( CLIENT_ID, "UTF-8" );
        data += "&" + URLEncoder.encode( "client_secret", "UTF-8" ) + "=" + URLEncoder.encode( CLIENT_SECRET, "UTF-8" );

        URL server = new URL( URL_API );
        HttpURLConnection connection = ( HttpURLConnection ) server.openConnection();
        connection.setDoOutput( true );
        OutputStreamWriter osw = new OutputStreamWriter( connection.getOutputStream() );
        osw.write( data );
        osw.flush();

        int responseCode = connection.getResponseCode();

        if(responseCode==200)
        {

            BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
            StringBuffer response = new StringBuffer();
            String line = "";
            while( ( line = reader.readLine() ) != null )
            {
                response.append( line );
            }
            Log.v("Login", response.toString());
        }
        else
        {
            Log.v( "CatalogClient", "Response code:" + responseCode );
        }
    }

    public static boolean isExternalStorageAviable(){
        boolean mExternalStorageAvailable;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
        } else {
            mExternalStorageAvailable = false;
        }
        return mExternalStorageAvailable;
    }

    public static boolean isExternalStorageWritable(){
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageWriteable = false;
        }
        return mExternalStorageWriteable;
    }

}
