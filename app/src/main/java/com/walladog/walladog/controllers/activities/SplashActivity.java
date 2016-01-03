package com.walladog.walladog.controllers.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.walladog.walladog.models.apiservices.WDCategoryService;
import com.walladog.walladog.models.apiservices.WDProductsService;
import com.walladog.walladog.models.apiservices.WDRacesService;
import com.walladog.walladog.models.apiservices.WDServicesService;
import com.walladog.walladog.models.dao.CategoryDAO;
import com.walladog.walladog.models.dao.RaceDAO;
import com.walladog.walladog.models.db.DatabaseHelper;
import com.walladog.walladog.models.responses.CategoryResponse;
import com.walladog.walladog.models.responses.ProductsResponse;
import com.walladog.walladog.models.responses.RacesResponse;
import com.walladog.walladog.models.responses.ServicesResponse;

import java.io.Serializable;
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
                        DatabaseHelper.getInstance(getApplicationContext());
                        RaceDAO racedao = new RaceDAO(getApplicationContext());
                        for(Race race : mRacesList){
                            racedao.insert(race);
                        }
                        requestsFinished++;
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
                        appLoading.setText("Cargando Categorias");DatabaseHelper.getInstance(getApplicationContext());
                        CategoryDAO categorydao = new CategoryDAO(getApplicationContext());
                        for(Category cat : mCategoryList){
                            categorydao.insert(cat);
                        }
                        requestsFinished++;
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Log.v(TAG, "Failed request on " + WDCategoryService.class.getName());
                    }
                });


    }

    private void LaunchApp(){
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
}
