package com.walladog.walladog.controllers.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;
import com.walladog.walladog.R;
import com.walladog.walladog.WalladogApp;
import com.walladog.walladog.controllers.fragments.AddProductFragment;
import com.walladog.walladog.controllers.fragments.DogDetailFragment;
import com.walladog.walladog.controllers.fragments.DogListFragment;
import com.walladog.walladog.controllers.fragments.HomeFragment;
import com.walladog.walladog.controllers.fragments.LoginFragment;
import com.walladog.walladog.controllers.fragments.MapsLocator;
import com.walladog.walladog.controllers.fragments.NotificationsFragment;
import com.walladog.walladog.controllers.fragments.SigninFragment;
import com.walladog.walladog.controllers.fragments.UserProfileFragment;
import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.UserData;
import com.walladog.walladog.models.WDServices;
import com.walladog.walladog.models.apiservices.AccessToken;
import com.walladog.walladog.models.apiservices.ServiceGeneratorOAuth;
import com.walladog.walladog.models.apiservices.WDOAuth;
import com.walladog.walladog.models.apiservices.WDUserDataService;
import com.walladog.walladog.models.responses.ProductResponse;
import com.walladog.walladog.utils.WDEventNotification;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends DrawerBaseActivity
        implements LoginFragment.OnLoginClickListener,
        SigninFragment.OnSigninClickListener,
        DogListFragment.OnListItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_WDSERVICES = "EXTRA_WDSERVICES";
    public static final String EXTRA_WDPRODUCTS = "EXTRA_WDPRODUCTS";
    public static final String EXTRA_CATEGORIAS = "EXTRA_CATEGORIAS";

    private FrameLayout mFLayout=null;
    private List<WDServices> mServices = null;
    private ProductResponse mProducts = null;
    private List<Category> mCategoryList = null;

    private GoogleApiClient mGoogleApiClient = null;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mServices = (List<WDServices>) getIntent().getSerializableExtra(EXTRA_WDSERVICES);
        mProducts = (ProductResponse) getIntent().getSerializableExtra(EXTRA_WDPRODUCTS);
        mCategoryList = (List<Category>) getIntent().getSerializableExtra(EXTRA_CATEGORIAS);


        //Layout
        mFLayout = (FrameLayout) findViewById(R.id.drawer_layout_main_activity_frame);

        Fragment fragment = new HomeFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(HomeFragment.ARG_WDSERVICES, (Serializable) mServices);
        arguments.putSerializable(HomeFragment.ARG_CATEGORIAS, (Serializable) mCategoryList);
        fragment.setArguments(arguments);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.drawer_layout_main_activity_frame, fragment, HomeFragment.class.getName())
                    .commit();
        }
    }

    @Override public void onDrawerClosed(View drawerView) {}
    @Override public void onDrawerOpened(View drawerView) {}
    @Override public void onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, HomeFragment.newInstance(mServices,mProducts),HomeFragment.class.getName())
                        .addToBackStack(HomeFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_products:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, DogListFragment.newInstance(mProducts),DogListFragment.class.getName())
                        .addToBackStack(DogListFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Products", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_location:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, MapsLocator.newInstance("1","1"),MapsLocator.class.getName())
                        .addToBackStack(HomeFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_transactions:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, UserProfileFragment.newInstance(mProducts, "1"),UserProfileFragment.class.getName())
                        .addToBackStack(UserProfileFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Transactions", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_notifications:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, NotificationsFragment.newInstance("1", "1"),NotificationsFragment.class.getName())
                        .addToBackStack(NotificationsFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Notification", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_picture:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, AddProductFragment.newInstance("1","1"),AddProductFragment.class.getName())
                        .addToBackStack(AddProductFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Picture", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, LoginFragment.newInstance(),LoginFragment.class.getName())
                        .addToBackStack(LoginFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_signin:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, SigninFragment.newInstance(),SigninFragment.class.getName())
                        .addToBackStack(SigninFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Signin", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:

                Toast.makeText(getApplicationContext(), "Go to Signin", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        final android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() == 0) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(R.string.sure_exit)
                    .setPositiveButton(getString(R.string.app_yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.app_no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Gracias por seguir con nosotros!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public void onLoginSubmit(String username, String password, View currentView) throws UnsupportedEncodingException {
        ServiceGeneratorOAuth.createService(WDOAuth.class, null, AccessToken.clientID, AccessToken.clientSecret)
                .getAccessToken(AccessToken.grantType, username, password)
                .enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Response<AccessToken> response, Retrofit retrofit) {
                        if (response != null && response.code()==200) {
                            String aToken = response.body().getAccessToken();

                            Log.v(TAG, "Obtenemos Token y lo salvamos::" + aToken);
                            getSharedPreferences(WalladogApp.class.getSimpleName(), MODE_PRIVATE)
                                    .edit()
                                    .putString(AccessToken.OAUTH2_TOKEN, aToken)
                                    .commit();
                            Snackbar snackbar = Snackbar
                                    .make(mFLayout, "Login Success", Snackbar.LENGTH_LONG);

                            try {
                                ServiceGeneratorOAuth.createService(WDUserDataService.class).getUserData().enqueue(new Callback<UserData>() {
                                    @Override
                                    public void onResponse(Response<UserData> response, Retrofit retrofit) {
                                        if(response!=null & response.code()==200){
                                            UserData ud = response.body();
                                            getSharedPreferences(WalladogApp.class.getSimpleName(), MODE_PRIVATE)
                                                    .edit()
                                                    .putString(AccessToken.UDATA_USERNAME, ud.getUsername())
                                                    .putString(AccessToken.UDATA_AVATAR, ud.getAvatar_thumbnail_url())
                                                    .commit();

                                            setMenuForLogged(ud);
                                        }

                                    }
                                    @Override
                                    public void onFailure(Throwable t) {
                                        Log.v(TAG,"API request failed on Logins request");
                                        setMenuForNotLogged();
                                    }
                                });
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Snackbar.make(mFLayout, "Error al autenticar", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.v(TAG, "Failed request on " + AccessToken.class.getName());
                        Snackbar.make(mFLayout, "Error al autenticar", Snackbar.LENGTH_LONG).show();

                    }
                });
    }

    @Override
    public void onSigninSubmit(String username, String password, View currentView) {

    }

    @Override
    public void onListItemSelected(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_layout_main_activity_frame, DogDetailFragment.newInstance(mProducts.getResults().get(position)),DogDetailFragment.class.getName())
                .addToBackStack(DogDetailFragment.class.getName())
                .commit();
        Toast.makeText(getApplicationContext(), "Go to Home", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // This method will be called when a NotificationDataEvent is posted
    public void onEvent(WDEventNotification dataItem){
        Log.v(TAG, "Recived event to update Notifications");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_layout_main_activity_frame, DogListFragment.newInstance(mProducts),DogListFragment.class.getName())
                .addToBackStack(DogListFragment.class.getName())
                .commit();
    }

    private void setMenuForLogged(UserData ud){
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.txtUsername);
        TextView description = (TextView) headerView.findViewById(R.id.txtUsernameDesc);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.avatarImage);
        username.setText(ud.getFirst_name()+','+ud.getLast_name());
        description.setText("Conectado");
        Picasso.with(getApplicationContext())
                //.load(ud.getAvatar_thumbnail_url())
                .load("https://lh3.googleusercontent.com/CyrAYZKXSU4MfjB1tk94gK_daNfahS7pGHEuDBXMoL6S9MdBKDFuiL_jWXZ0IBtwrg=w300")
                .into(avatar);

        Menu drwMenu = navigationView.getMenu().getItem(6).getSubMenu();
        drwMenu.getItem(0).setVisible(false);
        drwMenu.getItem(1).setVisible(false);
    }

    private void setMenuForNotLogged(){
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.txtUsername);
        TextView description = (TextView) headerView.findViewById(R.id.txtUsernameDesc);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.avatarImage);
        username.setText("Usuario anónimo");
        description.setText("No conectado");
        Picasso.with(getApplicationContext())
                .load(R.drawable.walladogsmall)
                .into(avatar);

        Menu drwMenu = navigationView.getMenu().getItem(6).getSubMenu();
        drwMenu.getItem(2).setVisible(false);
        drwMenu.getItem(3).setVisible(false);
    }



}
