package com.walladog.walladog.controllers.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.walladog.walladog.R;
import com.walladog.walladog.utils.WDUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class DrawerBaseActivity extends AppCompatActivity {

  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.drawer_layout_navigation_view)
  NavigationView navigationView;
  @Bind(R.id.drawer)
  DrawerLayout drawerLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.drawer_layout);

    ButterKnife.bind(this);

    // Initializing Toolbar and setting it as the actionbar
    setSupportActionBar(toolbar);
    WDUtils.applyFontForToolbarTitle(this);

    //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

      // This method will trigger on item Click of navigation menu
      @Override
      public boolean onNavigationItemSelected(MenuItem menuItem) {
        //Checking if the item is in checked state or not, if not make it in checked state
        menuItem.setChecked(!menuItem.isChecked());

        //Closing drawer_menu on item click
        drawerLayout.closeDrawers();

        DrawerBaseActivity.this.onNavigationItemSelected(menuItem);

        return true;
      }
    });

    ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open_drawer_accesibility_string, R.string.drawer_close_drawer_accesibility_string){
      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        DrawerBaseActivity.this.onDrawerClosed(drawerView);
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        DrawerBaseActivity.this.onDrawerOpened(drawerView);
      }
    };

    drawerLayout.setDrawerListener(actionBarDrawerToggle);

    //calling sync state is necessary or else your hamburger icon wont show up
    actionBarDrawerToggle.syncState();

  }
  public abstract void onDrawerClosed(View drawerView);
  public abstract void onDrawerOpened(View drawerView);

  public abstract void onNavigationItemSelected(MenuItem menuItem);

}
