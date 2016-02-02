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
import com.walladog.walladog.WalladogApp;
import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.ServiceGenerator;
import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.models.apiservices.AccessToken;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;
import com.walladog.walladog.models.apiservices.WDCategoryService;
import com.walladog.walladog.models.apiservices.WDOAuth;
import com.walladog.walladog.models.apiservices.WDProductService;
import com.walladog.walladog.models.apiservices.WDRaceService;
import com.walladog.walladog.models.apiservices.WDServicesService;
import com.walladog.walladog.models.responses.ProductResponse;
import com.walladog.walladog.models.responses.ServicesResponse;
import com.walladog.walladog.utils.DBAsyncTasks;
import com.walladog.walladog.utils.DBAsyncTasksGet;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
    private ProductResponse mProductList = null;
    private List<Category> mListaCategorias = null;
    private List<Race> mRaceList = null;


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
                try {
                    getOAuthTokenAndStartApp();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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

    /**
     *
     * @throws UnsupportedEncodingException
     * Identification with token , refresh token o go to Login
     *
     */
    private void getOAuthTokenAndStartApp() throws UnsupportedEncodingException {
        String savedToken = getSharedPreferences(WalladogApp.class.getSimpleName(),MODE_PRIVATE)
                .getString(AccessToken.OAUTH2_TOKEN, null);

        if(savedToken==null) {
            ServiceGeneratorOAuth.createService(WDOAuth.class, null, AccessToken.clientID, AccessToken.clientSecret).getAccessToken(AccessToken.grantType, AccessToken.cusername, AccessToken.cpassword)
                    .enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Response<AccessToken> response, Retrofit retrofit) {
                            if (response != null) {
                                String aToken = response.body().getAccessToken();
                                Log.v(TAG, "Obtenemos Token y lo salvamos::" + aToken);
                                getSharedPreferences(WalladogApp.class.getSimpleName(), MODE_PRIVATE)
                                        .edit()
                                        .putString(AccessToken.OAUTH2_TOKEN, aToken)
                                        .commit();
                                try {
                                    getInitRestData();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.v(TAG, "Response is null");
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.v(TAG, "Failed request on " + AccessToken.class.getName());
                        }
                    });
        }else{
            try {
                getInitRestData();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @throws UnsupportedEncodingException
     * Get initial data from all services and save to DB
     *
     */
    private void getInitRestData() throws UnsupportedEncodingException {
        appLoading.setText("Loading services...");

        ServiceGeneratorOAuth.createService(WDProductService.class).getProductsPaginated(0, 10)
                .enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Response<ProductResponse> response, Retrofit retrofit) {
                        mProductList = response.body();
                        requestsFinished++;
                        LaunchApp();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

        ServiceGeneratorOAuth.createService(WDRaceService.class).getRaces()
                .enqueue(new Callback<List<Race>>() {
                    @Override
                    public void onResponse(Response<List<Race>> response, Retrofit retrofit) {
                        mRaceList = response.body();
                        Log.v(TAG, mRaceList.get(0).getName());
                        appLoading.setText("Salvando razas");
                        DBAsyncTasks<Race> task = new DBAsyncTasks<Race>(DBAsyncTasks.TASK_SAVE_LIST, new Race(), getApplicationContext(), mRaceList, new DBAsyncTasks.OnItemsSavedToDBListener() {
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
                        Log.v(TAG,"Error retriving races");
                    }
                });

        ServiceGeneratorOAuth.createService(WDCategoryService.class).getMultiTask()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Response<List<Category>> response, Retrofit retrofit) {
                        final List<Category> mCategoryList = response.body();
                        appLoading.setText("Categorias recuperadas");
                        DBAsyncTasks<Category> task = new DBAsyncTasks<Category>(DBAsyncTasks.TASK_SAVE_LIST, new Category(), getApplicationContext(), mCategoryList, new DBAsyncTasks.OnItemsSavedToDBListener() {
                            @Override
                            public void onItemsSaved(Boolean saved) {
                                Log.v(TAG, "Categorias salvadas");
                                requestsFinished++;
                                LaunchApp();
                                DBAsyncTasksGet<Category> task2 = new DBAsyncTasksGet<Category>(DBAsyncTasksGet.TASK_GET_LIST,
                                        new Category(), getApplicationContext(),
                                        new DBAsyncTasksGet.OnItemsRecoveredFromDBListener<Category>() {
                                            @Override
                                            public void onItemsRecovered(List<Category> items) {
                                                mListaCategorias = items;
                                                requestsFinished++;
                                                LaunchApp();
                                            }
                                        });
                                task2.execute();
                            }
                        });
                        task.execute();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    private void LaunchApp(){
        Log.v(TAG, "Lanzado " + String.valueOf(requestsFinished));
        //TODO valor correcto 5
        if(mProductList!=null && requestsFinished==4){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(MainActivity.EXTRA_WDPRODUCTS, (Serializable) mProductList);
            i.putExtra(MainActivity.EXTRA_CATEGORIAS, (Serializable) mListaCategorias);
            Log.v(TAG,"Launching APP all tasks successfull resolved");
            startActivity(i);

        }else{
            Log.v(TAG,"Waiting for launch to end process...");
        }
    }

}
