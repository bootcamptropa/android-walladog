package com.walladog.walladog.adapters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;
import com.walladog.walladog.R;
import com.walladog.walladog.controllers.fragments.ProfileSSearchFragment;
import com.walladog.walladog.controllers.fragments.ProfileSellingFragment;
import com.walladog.walladog.controllers.fragments.ProfileSoldFragment;
import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.ProfileOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadock on 12/12/15.
 *
 */

public class ProfilePagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {


    private List<ProfileOption> mProfileOptions = null;

    private List<Product> mProductsSelling;
    private List<Product> mProductsSold;



    private static final String[] CONTENT = new String[] { "Calendar", "Camera", "Alarms", "Location" };
    private static final int[] ICONS = new int[] {
            R.drawable.ic_menu_account,
            R.drawable.ic_menu_gallery,
            R.drawable.ic_menu_camera,
            R.drawable.ic_menu_location,
    };


    public ProfilePagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        mProfileOptions = new ArrayList<ProfileOption>();
        mProfileOptions.add(new ProfileOption("Vendidos"));
        mProfileOptions.add(new ProfileOption("Vendiendo"));
        mProfileOptions.add(new ProfileOption("Búsquedas"));
    }

    public ProfilePagerAdapter(android.support.v4.app.FragmentManager fm,List<Product> pSelling,List<Product> pSold) {
        super(fm);
        mProfileOptions = new ArrayList<>();
        mProfileOptions.add(new ProfileOption("Vendidos"));
        mProfileOptions.add(new ProfileOption("Vendiendo"));
        mProfileOptions.add(new ProfileOption("Búsquedas"));

        mProductsSelling=pSelling;
        mProductsSold=pSold;
    }

    @Override
    public int getIconResId(int index) {
        return ICONS[index];
    }

    @Override
    public int getCount() {
        return mProfileOptions.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mProfileOptions.get(position).getOptionName();
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle arguments = new Bundle();

        switch (position){
            case 0:
                fragment = ProfileSellingFragment.newInstance(mProductsSelling);
                break;
            case 1:
                fragment = ProfileSoldFragment.newInstance(mProductsSold);
                break;
            case 2:
                fragment = ProfileSSearchFragment.newInstance("1","1");
                break;
        }
        return fragment;

    }

    public void setProductsSelling(List<Product> productsSelling) {
        mProductsSelling = productsSelling;
    }

    public void setProductsSold(List<Product> productsSold) {
        mProductsSold = productsSold;
    }
}