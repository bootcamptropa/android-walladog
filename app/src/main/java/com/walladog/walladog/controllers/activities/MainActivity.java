package com.walladog.walladog.controllers.activities;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.walladog.walladog.R;
import com.walladog.walladog.controllers.fragments.AddProductFragment;
import com.walladog.walladog.controllers.fragments.DogListFragment;
import com.walladog.walladog.controllers.fragments.HomeFragment;
import com.walladog.walladog.controllers.fragments.LoginFragment;
import com.walladog.walladog.controllers.fragments.MapsLocator;
import com.walladog.walladog.controllers.fragments.ServiceFragment;
import com.walladog.walladog.models.WDServices;

public class MainActivity extends DrawerBaseActivity
        implements LoginFragment.OnLoginClickListener,
        MapsLocator.OnFragmentInteractionListener,
        DogListFragment.OnFragmentInteractionListener,
        AddProductFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment fragment = new HomeFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("ARG_SERVICES", new WDServices());
        fragment.setArguments(arguments);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.drawer_layout_main_activity_frame, fragment, HomeFragment.class.getName())
                    .commit();
        }
    }

    @Override public void onDrawerClosed(View drawerView) {

    }

    @Override public void onDrawerOpened(View drawerView) {

    }

    @Override public void onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, LoginFragment.newInstance(),LoginFragment.class.getName())
                        .addToBackStack(LoginFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Login Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, HomeFragment.newInstance(),HomeFragment.class.getName())
                        .addToBackStack(HomeFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_products:
                /*getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, ProductListFragment.newInstance(),ProductListFragment.class.getName())
                        .addToBackStack(ProductListFragment.class.getName())
                        .commit();*/
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, DogListFragment.newInstance("1","1"),DogListFragment.class.getName())
                        .addToBackStack(DogListFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Products", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_location:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, MapsLocator.newInstance("1","1"),MapsLocator.class.getName())
                        .addToBackStack(HomeFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_picture:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawer_layout_main_activity_frame, AddProductFragment.newInstance("1","1"),AddProductFragment.class.getName())
                        .addToBackStack(AddProductFragment.class.getName())
                        .commit();
                Toast.makeText(getApplicationContext(), "Go to Picture", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoginSubmit(String username, String password, View currentView) {
        Log.v(TAG, "Click listener");
    }

    @Override
    public void onBackPressed() {
        final android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        ServiceFragment mFragment = (ServiceFragment) fm.findFragmentByTag("HomeFragment");

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
    public void onFragmentInteraction(Uri uri) {
        Log.v(TAG,"Fragment Listener");
    }
}
