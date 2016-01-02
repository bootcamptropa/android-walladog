package com.walladog.walladog.controllers.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.walladog.walladog.R;
import com.walladog.walladog.models.WDServices;

import org.w3c.dom.Text;

/**
 * Created by hadock on 28/12/15.
 *
 */

public class ServiceFragment extends Fragment {

    private static final String TAG = ServiceFragment.class.getName();

    public static final String ARG_WDSERVICE = null;
    private WDServices wdservice;

    private static TextView serviceTitle = null;
    private static TextView serviceDescription = null;
    private static ImageView serviceImage = null;

    public static ServiceFragment newInstance(WDServices service){
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WDSERVICE, service);
        fragment.setArguments(args);
        return fragment;
    }

    public ServiceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            wdservice = (WDServices) getArguments().getSerializable(ARG_WDSERVICE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_service, container, false);

        // Linking with view
        serviceTitle = (TextView) root.findViewById(R.id.txt_service_title);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Lobster-Regular.ttf");
        serviceTitle.setTypeface(face);
        serviceTitle.setTextSize(20);

        serviceDescription = (TextView) root.findViewById(R.id.txt_service_description);
        serviceImage = (ImageView) root.findViewById(R.id.img_service3);

        serviceDescription.setMovementMethod(new ScrollingMovementMethod());

        // Sync view & model
        serviceTitle.setText(wdservice.getName());
        serviceDescription.setText(wdservice.getDescription());
        Picasso.with(getActivity().getApplicationContext())
                .load(wdservice.getServiceImage())
                .placeholder(R.drawable.progress_animation)
                .into(serviceImage);

        return root;
    }


}
