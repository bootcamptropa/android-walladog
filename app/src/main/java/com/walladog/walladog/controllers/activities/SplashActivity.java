package com.walladog.walladog.controllers.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.walladog.walladog.R;
import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.ServiceGenerator;
import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.models.apiservices.AccessToken;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;
import com.walladog.walladog.models.apiservices.WDCategoryService;
import com.walladog.walladog.models.apiservices.WDOAuth;
import com.walladog.walladog.models.apiservices.WDProductsService;
import com.walladog.walladog.models.apiservices.WDRacesService;
import com.walladog.walladog.models.apiservices.WDServicesService;
import com.walladog.walladog.models.responses.CategoryResponse;
import com.walladog.walladog.models.responses.ProductsResponse;
import com.walladog.walladog.models.responses.RacesResponse;
import com.walladog.walladog.models.responses.ServicesResponse;
import com.walladog.walladog.utils.DBAsyncTasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by hadock on 14/12/15.
 *
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getName();
    private long splashDelay = 3000; // 6 segundos
    public ProgressBar progressBar;
    private TextView appTitle,appLoading = null;

    private int requestsFinished=0;

    private List<WDServices> mWDServices = null;
    private List<Product> mProductList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT >= 10) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


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





        try {
            OAuth2Login();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StartAnimations();

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getInitRestData();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        Animation animScaleDown = AnimationUtils.loadAnimation(this, R.anim.rotate);
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.startAnimation(animScaleDown);

    }

    private void getInitRestData(){
        appLoading.setText("Loading services...");

        ServiceGenerator
                .createService(WDServicesService.class).getMultiTask()
                .enqueue(new Callback<ServicesResponse>() {
                    @Override
                    public void onResponse(Response<ServicesResponse> response, Retrofit retrofit) {
                        mWDServices = response.body().getData();
                        appLoading.setText("Servicios cargados");
                        requestsFinished++;
                        LaunchApp();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.v(TAG, "Failed request on " + WDServicesService.class.getName());
                    }
                });

        ServiceGenerator.createService(WDProductsService.class).getMultiTask()
                .enqueue(new Callback<ProductsResponse>() {

                    @Override
                    public void onResponse(Response<ProductsResponse> response, Retrofit retrofit) {
                        mProductList = response.body().getData();
                        appLoading.setText("Perros cargados");
                        requestsFinished++;
                        LaunchApp();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.v(TAG, "Failed request on " + WDProductsService.class.getName());
                    }
                });

        ServiceGenerator.createService(WDRacesService.class).getMultiTask()
                .enqueue(new Callback<RacesResponse>() {
                    @Override
                    public void onResponse(Response<RacesResponse> response, Retrofit retrofit) {
                        List<Race> mRacesList = response.body().getData();
                        appLoading.setText("Cargando razas");
                        DBAsyncTasks<Race> task = new DBAsyncTasks<Race>(DBAsyncTasks.TASK_SAVE_LIST, new Race(), getApplicationContext(), mRacesList, new DBAsyncTasks.OnItemsSavedToDBListener() {
                            @Override
                            public void onItemsSaved(Boolean saved) {
                                requestsFinished++;
                                LaunchApp();
                            }
                        });
                        task.execute();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.v(TAG, "Failed request on " + WDRacesService.class.getName());
                    }
                });

        ServiceGenerator.createService(WDCategoryService.class).getMultiTask()
                .enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(Response<CategoryResponse> response, Retrofit retrofit) {
                        List<Category> mCategoryList = response.body().getData();
                        appLoading.setText("Cargando Categorias");
                        DBAsyncTasks<Category> task = new DBAsyncTasks<Category>(DBAsyncTasks.TASK_SAVE_LIST, new Category(), getApplicationContext(), mCategoryList, new DBAsyncTasks.OnItemsSavedToDBListener() {
                            @Override
                            public void onItemsSaved(Boolean saved) {
                                requestsFinished++;
                                LaunchApp();
                            }
                        });
                        task.execute();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.v(TAG, "Failed request on " + WDCategoryService.class.getName());
                    }
                });

        //ServiceGeneratorOAuth.createService(WDOAuth.class, null, AccessToken.clientID, AccessToken.clientSecret).getAccessToken(AccessToken.grantType, AccessToken.cusername, AccessToken.cpassword, AccessToken.clientID, AccessToken.clientSecret)
        ServiceGenerator.createService(WDOAuth.class).getAccessToken(AccessToken.grantType, AccessToken.cusername, AccessToken.cpassword, AccessToken.clientID, AccessToken.clientSecret)
                .enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Response<AccessToken> response, Retrofit retrofit) {
                        if (response != null) {
                            String aToken = response.body().getAccessToken();
                            Log.v(TAG, "Pillamos el token::");
                        } else {
                            Log.v(TAG, "Response is null");
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.v(TAG, "Failed request on " + AccessToken.class.getName());
                    }
                });


    }

    private void LaunchApp(){
        Log.v(TAG, "Lanzado " + String.valueOf(requestsFinished));
        if(mProductList!=null && mWDServices!=null && requestsFinished==4){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(MainActivity.EXTRA_WDSERVICES, (Serializable) mWDServices);
            i.putExtra(MainActivity.EXTRA_WDPRODUCTS, (Serializable) mProductList);
            Log.v(TAG,"Launching APP all tasks successfull resolved");
            startActivity(i);

        }else{
            Log.v(TAG,"Error , some process working, app not launched...");
        }
    }

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
            Log.v( "Login", response.toString() );
        }
        else
        {
            Log.v( "CatalogClient", "Response code:" + responseCode );
        }
    }




}
