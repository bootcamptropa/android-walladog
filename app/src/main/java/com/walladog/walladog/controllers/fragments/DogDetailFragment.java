package com.walladog.walladog.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.walladog.walladog.R;
import com.walladog.walladog.adapters.CarouselAdapter;
import com.walladog.walladog.models.Photo;

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

    public DogDetailFragment() {
        photoList = new ArrayList<>();
        for(int i=0; i<10; i++){
            photoList.add(new Photo("Photo"+String.valueOf(i),"http://lorempixel.com/30"+String.valueOf(i)+"/300/animals"));
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
