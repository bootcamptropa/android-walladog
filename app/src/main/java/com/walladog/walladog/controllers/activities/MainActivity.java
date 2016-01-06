package com.walladog.walladog.controllers.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.walladog.walladog.R;
import com.walladog.walladog.controllers.fragments.AddProductFragment;
import com.walladog.walladog.controllers.fragments.DogDetailFragment;
import com.walladog.walladog.controllers.fragments.DogListFragment;
import com.walladog.walladog.controllers.fragments.HomeFragment;
import com.walladog.walladog.controllers.fragments.LoginFragment;
import com.walladog.walladog.controllers.fragments.MapsLocator;
import com.walladog.walladog.controllers.fragments.NotificationsFragment;
import com.walladog.walladog.controllers.fragments.SigninFragment;
import com.walladog.walladog.controllers.fragments.UserProfileFragment;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.WDServices;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends DrawerBaseActivity
        implements LoginFragment.OnLoginClickListener,
        SigninFragment.OnSigninClickListener,
        DogListFragment.OnListItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_WDSERVICES = "EXTRA_WDSERVICES";
    public static final String EXTRA_WDPRODUCTS = "EXTRA_WDPRODUCTS";

    private static List<WDServices> mServices = null;
    private static List<Product> mProducts = null;

    private GoogleApiClient mGoogleApiClient = null;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mServices = (List<WDServices>) getIntent().getSerializableExtra(this.EXTRA_WDSERVICES);
        mProducts = (List<Product>) getIntent().getSerializableExtra(this.EXTRA_WDPRODUCTS);

        Fragment fragment = new HomeFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(HomeFragment.ARG_WDSERVICES, (Serializable) mServices);
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
                Toast.makeText(getApplicationContext(), "Go to ProductsResponse", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_location:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, MapsLocator.newInstance("1","1"),MapsLocator.class.getName())
                        .addToBackStack(HomeFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_transactions:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, UserProfileFragment.newInstance(mProducts, "1"),UserProfileFragment.class.getName())
                        .addToBackStack(UserProfileFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Picture", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_notifications:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, NotificationsFragment.newInstance("1", "1"),NotificationsFragment.class.getName())
                        .addToBackStack(NotificationsFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Picture", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Login Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_signin:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, SigninFragment.newInstance(),SigninFragment.class.getName())
                        .addToBackStack(SigninFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Signin Selected", Toast.LENGTH_SHORT).show();
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
    public void onLoginSubmit(String username, String password, View currentView) {
        Log.v(TAG, "Click listener");
    }

    @Override
    public void onSigninSubmit(String username, String password, View currentView) {

    }

    @Override
    public void onListItemSelected(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_layout_main_activity_frame, DogDetailFragment.newInstance(mProducts.get(position)),DogDetailFragment.class.getName())
                .addToBackStack(DogDetailFragment.class.getName())
                .commit();
        Toast.makeText(getApplicationContext(), "Go to Home", Toast.LENGTH_SHORT).show();
    }
}
