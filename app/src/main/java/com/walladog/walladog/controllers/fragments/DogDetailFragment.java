package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.walladog.walladog.R;
import com.walladog.walladog.adapters.CarouselAdapter;
import com.walladog.walladog.models.Photo;
import com.walladog.walladog.utils.WorkaroundMapFragment;

import java.util.ArrayList;
import java.util.List;


public class DogDetailFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = DogDetailFragment.class.getName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    //Array of pictures
    private List<Photo> photoList;

    //Map
    private ScrollView mScrollView;
    private GoogleMap mMap;

    public DogDetailFragment() {
        photoList = new ArrayList<>();
        for(int i=0; i<5; i++){
            photoList.add(new Photo("Photo"+String.valueOf(i),"http://loremflickr.com/30"+String.valueOf(i)+"/300/dog"));
        }
    }

    public static DogDetailFragment newInstance(String param1, String param2) {
        DogDetailFragment fragment = new DogDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dog_detail, container, false);

        //Map - intercepting touch events in custom class
        if (mMap == null) {
            mMap = ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mScrollView = (ScrollView) v.findViewById(R.id.detailScroll); //parent scrollview in xml, give your scrollview id value

            ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment))
                    .setListener(new WorkaroundMapFragment.OnTouchListener() {
                        @Override
                        public void onTouch() {
                            mScrollView.requestDisallowInterceptTouchEvent(true);
                        }
                    });

        }

        CarouselAdapter adapter = new CarouselAdapter(getActivity().getSupportFragmentManager(),photoList);
        ViewPager pager = (ViewPager) v.findViewById(R.id.viewpager);

        pager.addOnPageChangeListener(this);

        LinePageIndicator ti = (LinePageIndicator) v.findViewById(R.id.titles);
        pager.setAdapter(adapter);
        ti.setViewPager(pager);
        ti.setOnPageChangeListener(this);

        return v;
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
}
