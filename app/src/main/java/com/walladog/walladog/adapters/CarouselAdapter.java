package com.walladog.walladog.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.walladog.walladog.controllers.fragments.PhotoDetailFragment;
import com.walladog.walladog.models.ProductImage;

import java.util.List;

/**
 * Created by hadock on 31/12/15.
 *
 */

public class CarouselAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private final static String TAG = CarouselAdapter.class.getName();
    private List<ProductImage> mPhotos = null;

    public CarouselAdapter(FragmentManager fm, List<ProductImage> photos) {
        super(fm);
        Log.v(TAG, "Constructor");
        Log.v(TAG,"Items en photos: "+String.valueOf(photos.size()));
        Log.v(TAG,"Primera foto: "+String.valueOf(photos.get(0).getPhoto_url()));
        mPhotos = photos;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoDetailFragment.newInstance(mPhotos.get(position));
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