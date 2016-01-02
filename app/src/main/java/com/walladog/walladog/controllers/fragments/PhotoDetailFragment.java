package com.walladog.walladog.controllers.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.walladog.walladog.R;
import com.walladog.walladog.models.Photo;
import com.walladog.walladog.models.WDServices;

/**
 * Created by hadock on 31/12/15.
 *
 */
public class PhotoDetailFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "PICTURE";
    private Photo mPhoto = null;

    public final PhotoDetailFragment newInstance(Photo photo){
        PhotoDetailFragment f = new PhotoDetailFragment();
        Bundle bdl = new Bundle(1);
        bdl.putSerializable(EXTRA_MESSAGE, photo);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getArguments() != null) {
            mPhoto = (Photo) getArguments().getSerializable(EXTRA_MESSAGE);
        }else{
            mPhoto = new Photo("http://loremflickr.com/400/300/dog","Placeholder");
        }
        View v = inflater.inflate(R.layout.photo_layout, container, false);
        
        ImageView imgView = (ImageView)v.findViewById(R.id.imageView);
        TextView txtView = (TextView) v.findViewById(R.id.photoTitle);
        txtView.setText(mPhoto.getPhotoTitle());

/*
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.LTGRAY)
                .borderWidthDp(0)
                .cornerRadiusDp(10)
                .oval(false)
                .build();
*/

        Picasso.with(getActivity()).
                load(mPhoto.getPhotoUrl())
                /*.transform(transformation)*/
                .placeholder(R.drawable.walladogsmall)
                .into(imgView);

        return v;
    }

    public PhotoDetailFragment() {
    }
}
