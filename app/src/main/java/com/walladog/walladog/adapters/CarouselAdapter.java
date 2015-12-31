package com.walladog.walladog.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.walladog.walladog.controllers.fragments.PhotoDetailFragment;
import com.walladog.walladog.controllers.fragments.ServiceFragment;
import com.walladog.walladog.models.Photo;

import java.util.List;

/**
 * Created by hadock on 31/12/15.
 *
 */

public class CarouselAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {


    private List<Photo> mPhotos;

    public CarouselAdapter(FragmentManager fm, List photos) {
        super(fm);
        mPhotos = photos;
    }

    @Override
    public Fragment getItem(int position) {
        Photo oPhoto = mPhotos.get(position);
        Fragment fragment = new PhotoDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(PhotoDetailFragment.EXTRA_MESSAGE,oPhoto);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }
}