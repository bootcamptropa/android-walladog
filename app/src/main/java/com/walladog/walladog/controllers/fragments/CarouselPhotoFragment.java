package com.walladog.walladog.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hadock on 31/12/15.
 *
 */

public class CarouselPhotoFragment extends Fragment {

    public static Fragment newInstance(Context context, int pos,float scale) {
        return new CarouselPhotoFragment();
    }

    public CarouselPhotoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}