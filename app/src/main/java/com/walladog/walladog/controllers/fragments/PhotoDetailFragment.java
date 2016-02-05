package com.walladog.walladog.controllers.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.walladog.walladog.R;
import com.walladog.walladog.models.ProductImage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hadock on 31/12/15.
 *
 */
public class PhotoDetailFragment extends Fragment {

    private final static String TAG = PhotoDetailFragment.class.getName();

    public static final String EXTRA_MESSAGE = "PICTURE";
    private ImageView mImageProduct = null;
    private ProductImage mPhoto = null;

    public static final PhotoDetailFragment newInstance(ProductImage photo){
        PhotoDetailFragment f = new PhotoDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(EXTRA_MESSAGE, photo);
        f.setArguments(arguments);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhoto = (ProductImage) getArguments().getSerializable(EXTRA_MESSAGE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.photo_layout, container, false);
        mImageProduct = (ImageView)v.findViewById(R.id.productImage);


        Picasso.with(getContext()).invalidate(mPhoto.getPhoto_url());
        Picasso.with(getContext())
                .load(mPhoto.getPhoto_url())
                .placeholder(R.drawable.dogplace3)
                .into(mImageProduct);


        return v;
    }

    public PhotoDetailFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
